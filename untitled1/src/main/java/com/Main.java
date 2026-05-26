package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import org.apache.commons.codec.digest.HmacUtils;

class ObjectResponse{
  public String requestId;
  public Data data;
}
class Data{
  public String nonce;
  public String signingKey;
  public List<String> events;
}

class Submit{
  public String studentCode;
  public String qCode;
  public String requestId;

  public Submit(String studentCode, String qCode, String requestId) {
    this.studentCode = studentCode;
    this.qCode = qCode;
    this.requestId = requestId;
  }
}
public class Main {

  public static void main(String[] args) throws Exception{
    String msv = "B22DCCNxxx";
    String ip = "36.50.135.242";
    String qCode = "T79a5Eox";
    HttpClient client = HttpClient.newHttpClient();
    String getUrl = "http://" + ip + ":2230/api/rest/header?studentCode=" + msv + "&qCode=" + qCode;
    HttpRequest request = HttpRequest.newBuilder(URI.create(getUrl)).GET().build();
    HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(res.body());
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectResponse objectResponse = objectMapper.readValue(res.body(), ObjectResponse.class);
    System.out.println(objectResponse.requestId);
    String payload = objectResponse.data.nonce + ":" + String.join("|", objectResponse.data.events)
        + ":" + msv;
    String key = HmacUtils.hmacSha256Hex(objectResponse.data.signingKey, payload);
    String url = "http://" + ip + ":2230/api/rest/header/submit?studentCode=" + msv + "&qCode=" + qCode;
    Submit submit = new Submit(msv, qCode, objectResponse.requestId);
    String json = objectMapper.writeValueAsString(submit);
    HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).POST(HttpRequest.BodyPublishers.ofString(json))
        .setHeader("X-Signature", key)
        .build();
    HttpResponse<String> res1 = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    System.out.println(res1.body());



  }
}