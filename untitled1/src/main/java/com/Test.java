//package com;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//class ORes{
//  public String requestId;
//  public String data;
//}
//
//class Sub{
//  public String studentCode;
//  public String qCode;
//  public String requestId;
//  public String answer;
//
//  public Sub(String studentCode, String qCode, String requestId, String answer) {
//    this.studentCode = studentCode;
//    this.qCode = qCode;
//    this.requestId = requestId;
//    this.answer = answer;
//  }
//}
//public class Test {
//
//  public static void main(String[] args) throws Exception{
//    String msv = "B22DCCN544";
//    String ip = "36.50.135.242";
//    String qCode = "vULNWgX7";
//    String getUrl = "http://" + ip + ":2230/api/rest/character?studentCode=" + msv + "&qCode=" + qCode;
//    HttpClient client = HttpClient.newHttpClient();
//    HttpRequest request = HttpRequest.newBuilder()
//        .uri(URI.create(getUrl))
//        .GET().build();
//    HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
//    System.out.println(res.body());
//    ObjectMapper objectMapper = new ObjectMapper();
//    ORes oRes = objectMapper.readValue(res.body(), ORes.class);
//    String dt = oRes.data;
//    String[] a = dt.split("\\|\\|");
//    StringBuilder ans = new StringBuilder();
//    for(String x:a){
//      String[] tmp = x.split("\\s+");
//      StringBuilder sb = new StringBuilder();
//      for(String t:tmp){
//        if (t.startsWith("user=")){
//          sb.append("user=[EMAIL] ");
//        } else if (t.startsWith("phone=")){
//          sb.append("phone=[PHONE] ");
//        }else if (t.startsWith("token=")){
//          sb.append("token=[TOKEN] ");
//        }else {
//          sb.append(t).append(" ");
//        }
//      }
//      ans.append(sb.toString().trim()).append("||");
//    }
//    ans.delete(ans.length()-2, ans.length());
//    System.out.println(ans);
//    Sub sub = new Sub(msv, qCode, oRes.requestId, ans.toString());
//    String json = objectMapper.writeValueAsString(sub);
//    HttpRequest httpRequest = HttpRequest.newBuilder()
//        .uri(URI.create("http://" + ip + ":2230/api/rest/character/submit"))
//        .POST(HttpRequest.BodyPublishers.ofString(json))
//        .build();
//    HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//    System.out.println(response.body());
//
//  }
//
//}
