package com.kihong.resumeserver.service;

import lombok.Builder;

@Builder
public record AddResumeLogRequest(
        String ip,
        String timestamp
) {
}
