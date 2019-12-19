package com.java18.movienight.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog {

    @Id
    public ObjectId _id;
    public String url;
    public String ip;
    public String user = "unknown";
    public Date time;
    public String httpType;
    public String statusCode;
    public String requestBody;

    public RequestLog(String url, String httpType) {
        this.url = url;
        this.ip = "unknown";
        this.user = "unknown";
        this.time = new Date();
        this.httpType = httpType;
        this.requestBody = "";

    }
}
