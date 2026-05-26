package com;

import GRPC.TypedJudgeRequest;
import GRPC.TypedJudgeResponse;
import GRPC.TypedJudgeServiceGrpc;
import GRPC.TypedSubmitRequest;
import GRPC.TypedSubmitResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class TypedGRPC {
  public static void main(String[] args) {
    String msv = "B22DCCNxxx";
    String ip = "36.50.135.242";
    String qCode = "pdB7n6Cn";
    ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(ip, 2240).usePlaintext().build();
    TypedJudgeServiceGrpc.TypedJudgeServiceBlockingStub stub = TypedJudgeServiceGrpc.newBlockingStub(managedChannel);
    TypedJudgeRequest typedJudgeRequest = TypedJudgeRequest.newBuilder().setQuestionAlias(qCode).setStudentCode(msv).build();
    TypedJudgeResponse response = stub.requestTyped(typedJudgeRequest);

    TypedSubmitRequest submitRequest = TypedSubmitRequest.newBuilder().setRequestId(response.getRequestId()).setQuestionAlias(qCode).setStudentCode(msv).build();
    TypedSubmitResponse response1 = stub.submitTyped(submitRequest);
    System.out.println(response1);

  }
}
