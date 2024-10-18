package com.kihong.dashboardserver.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "download_log")
public class DownloadLog {
    @Id
    private String id;
    private String ip;
    private String Address;
    private String timeStamp;
}
