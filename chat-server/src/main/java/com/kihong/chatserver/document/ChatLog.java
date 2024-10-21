package com.kihong.chatserver.document;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "download_log")
@Builder
public class ChatLog {
    @Id
    private String id;
    private String userId;
    private String username;
    private String message;
    private Long timeStamp;
}
