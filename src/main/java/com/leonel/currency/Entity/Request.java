package com.leonel.currency.Entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Request {
    public String url;
    public int status;
    public String response;
    public long responseTimeMS;
    public LocalDateTime requestDateTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public long getResponseTimeMS() {
        return responseTimeMS;
    }

    public void setResponseTimeMS(long responseTimeMS) {
        this.responseTimeMS = responseTimeMS;
    }

    public LocalDateTime getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(LocalDateTime requestDateTime) {
        this.requestDateTime = requestDateTime;
    }
}
