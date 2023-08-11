package com.leonel.currency.Entity;

import java.util.Map;

public class Response {
    Map<String, Object> meta;
    Map<String, Object> data;

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
