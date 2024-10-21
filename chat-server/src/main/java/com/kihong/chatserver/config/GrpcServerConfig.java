package com.kihong.chatserver.config;

import com.kihong.chatserver.auth.AuthInterceptor;
import com.kihong.chatserver.grpc.DashboardGrpcServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {

    private Server server;
    private final DashboardGrpcServiceImpl application;

    private final int port = 50051;

    @PostConstruct
    public void startServer() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(application)
                .addService(ProtoReflectionService.newInstance())
                .intercept(new AuthInterceptor())
                .build()
                .start();
        System.out.println("GrpcServerConfig.startServer : Server started, listening on " + port);
    }

    @PreDestroy
    public void stopServer() {
        if (server != null) {
            server.shutdown();
        }
    }



}
