package com;

import GRPC.TextBatchAnswer;
import GRPC.TextBatchData;
import GRPC.TypedJudgeRequest;
import GRPC.TypedJudgeResponse;
import GRPC.TypedJudgeServiceGrpc;
import GRPC.TypedSubmitRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test1 {

  public static void main(String[] args) {
    String msv = "B22DCCN544";
    String ip = "36.50.135.242";
    String qCode = "yGDblMWZ";
    ManagedChannel channel = ManagedChannelBuilder.forAddress(ip, 2240).usePlaintext().build();
    TypedJudgeServiceGrpc.TypedJudgeServiceBlockingStub stub = TypedJudgeServiceGrpc.newBlockingStub(channel);
    TypedJudgeRequest typedJudgeRequest = TypedJudgeRequest.newBuilder().setQuestionAlias(qCode)
        .setStudentCode(msv).build();
    TypedJudgeResponse typedJudgeResponse = stub.requestTyped(typedJudgeRequest);
    System.out.println(typedJudgeResponse);
    TextBatchData txt = typedJudgeResponse.getTextBatch();
    List<String> t = List.of("account", "payment", "refund", "shipping");
    Map<String, Integer> mp = new HashMap<>();
    Set<String> st = new HashSet<>();
    for (String tmp : txt.getEntriesList()){
      for (String tm:t){
        if (tmp.contains(tm)){
          mp.put(tm, mp.getOrDefault(tm, 0)+1);
          st.add(tm);
        }
      }
    }
    List<String> mA = new ArrayList<>();
    Map<String, Integer> map = new HashMap<>();
    for (String l:t){
      if (mp.containsKey(l)){
        mA.add(l);
        map.put(l, mp.get(l));
      }
    }
    TextBatchAnswer textBatchAnswer = TextBatchAnswer.newBuilder().putAllCounts(map).addAllValues(mA).build();
    TypedSubmitRequest typedSubmitRequest = TypedSubmitRequest.newBuilder().setRequestId(typedJudgeResponse.getRequestId())
        .setQuestionAlias(qCode)
        .setStudentCode(msv)
        .setTextBatchAnswer(textBatchAnswer).build();
    stub.submitTyped(typedSubmitRequest);
  }

}
