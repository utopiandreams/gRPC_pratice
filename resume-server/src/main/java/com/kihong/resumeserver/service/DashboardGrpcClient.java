package com.kihong.resumeserver.service;

import com.kihong.dashboard.DashboardProto;
import com.kihong.dashboard.DashboardServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardGrpcClient {

    public DashboardGrpcClient(@GrpcClient("dashboard-service")
                               DashboardServiceGrpc.DashboardServiceBlockingStub stub) {
        this.stub = stub;
    }

    DashboardServiceGrpc.DashboardServiceBlockingStub stub;


    public String addLog(AddResumeLogRequest request) {
        DashboardProto.ResumeLogRequest req = DashboardProto.ResumeLogRequest.newBuilder()
                .setIp(request.ip())
                .setTimestamp(request.timestamp())
                .build();

        DashboardProto.ResumeLogResponse res = stub.addLog(req);
        return res.getMessage();
    }


}
