package com.kihong.resumeserver.controller;

import com.kihong.dashboard.DashboardServiceGrpc;
import com.kihong.resumeserver.service.AddResumeLogRequest;
import com.kihong.resumeserver.service.DashboardGrpcClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class ResumeController {

    private final DashboardGrpcClient dashboardGrpcClient;

    @GetMapping("/resume")
    public ResponseEntity<Resource> downloadResume(
            HttpServletRequest reqeust,
            HttpServletResponse response) {
        String ip = reqeust.getRemoteAddr();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss"));

        // gRPC 서버에 로그 남기기
        dashboardGrpcClient.addLog(
                new AddResumeLogRequest(ip, now)
        );

        Resource resource = new ClassPathResource("static/resume.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"resume.pdf\"")
                .body(resource);
    }

}
