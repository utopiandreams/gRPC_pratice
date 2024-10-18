package com.kihong.dashboardserver.service;

import com.kihong.dashboard.DashboardServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import static com.kihong.dashboard.DashboardProto.ResumeLogRequest;
import static com.kihong.dashboard.DashboardProto.ResumeLogResponse;

@Service
public class DashboardGrpcServiceImpl extends DashboardServiceGrpc.DashboardServiceImplBase {

    @Override
    public void addLog(ResumeLogRequest request, StreamObserver<ResumeLogResponse> responseObserver) {
        // 몽고디비 로그 저장
        // 웹소켓으로 로그 전송

        System.out.println("Received log from IP: " + request.getIp());
        ResumeLogResponse response = ResumeLogResponse.newBuilder()
                .setMessage("Log added successfully")
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
