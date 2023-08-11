package com.leonel.currency.Entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "date", columnDefinition = "DATE DEFAULT NOW()")
    private Date date;
    @Column(name = "time", columnDefinition = "TIME DEFAULT NOW()")
    private Time time;

    @Column(name = "url", columnDefinition = "CHARACTER(200)")
    private String url;

    @Column(name = "time_request_ms")
    private long time_request_ms;

    @Column(name = "information", columnDefinition = "CHARACTER(20000)")
    private String information;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime_request_ms() {
        return time_request_ms;
    }

    public void setTime_request_ms(long time_request_ms) {
        this.time_request_ms = time_request_ms;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
