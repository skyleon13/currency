package com.leonel.currency.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leonel.currency.Entity.Currency;
import com.leonel.currency.Entity.Request;
import com.leonel.currency.Entity.Response;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

public interface CurrencyService {
    public void SaveRequest(Request request);
    public void SaveCurrencies(Request request) throws ParseException, JsonProcessingException;
    public List<Currency> GetAllCurrencies();
    public Currency SearchCurrencyByCode(String code);
    public List<Currency> SearchCurrencyByCodeAndDate(String code, String finit, String fend);
}
