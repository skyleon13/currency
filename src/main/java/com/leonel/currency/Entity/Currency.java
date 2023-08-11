package com.leonel.currency.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "code", columnDefinition = "CHARACTER(5)")
    private String code;
    @Column(name = "value", columnDefinition = "DOUBLE PRECISION")
    private Double value;
    @Column(name = "lastupdate", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime lastUpdate;
    @Column(name = "requestdate", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime requestDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
}
