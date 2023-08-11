package com.leonel.currency.Service.CurrencyServiceIMPL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonel.currency.Entity.Currency;
import com.leonel.currency.Entity.History;
import com.leonel.currency.Entity.Request;
import com.leonel.currency.Entity.Response;
import com.leonel.currency.Repository.CurrencyRepo;
import com.leonel.currency.Repository.HistoryRepo;
import com.leonel.currency.Service.CurrencyService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class CurrencyServiceIMPL implements CurrencyService {
    @Autowired
    private HistoryRepo repoHistory;
    @Autowired
    private CurrencyRepo repoCurrency;
    @Autowired
    private EntityManager entityManager;

    public CurrencyServiceIMPL(CurrencyRepo currencyRepo, HistoryRepo historyRepo) {
        this.repoCurrency = currencyRepo;
        this.repoHistory = historyRepo;
    }

    @Override
    public void SaveRequest(Request request) {
        History his = new History();

        his.setDate(new java.sql.Date(System.currentTimeMillis()));
        his.setTime(new java.sql.Time(System.currentTimeMillis()));
        his.setUrl(request.getUrl());
        his.setTime_request_ms(request.getResponseTimeMS());
        his.setInformation(request.response);

        this.repoHistory.save(his);
    }

    @Override
    public void SaveCurrencies(Request request) throws ParseException, JsonProcessingException {
        com.leonel.currency.Entity.Currency currency, searchResult;

        ObjectMapper mapJson = new ObjectMapper();
        Response response = mapJson.readValue(request.response, Response.class);

        String lastUpdate =  response.getMeta().values().stream().toList().get(0).toString();
        List<Object> listCurrencies = response.getData().values().stream().toList();

        for (Object curr: listCurrencies) {
            currency = new Currency();

            currency.setCode(((LinkedHashMap) curr).get("code").toString());
            currency.setValue(Double.valueOf(((LinkedHashMap) curr).get("value").toString()));
            LocalDateTime zdt = ZonedDateTime.parse(lastUpdate).toLocalDateTime();
            currency.setLastUpdate(zdt);
            currency.setRequestDate(request.getRequestDateTime());

            this.repoCurrency.save(currency);
        }
    }

    public Currency SearchCurrencyByCode(String code) {
        return repoCurrency.findByCode(code);
    }

    public List<Currency> GetAllCurrencies(){
        return repoCurrency.findAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    public List<Currency> SearchCurrencyByCodeAndDate(String code, String finit, String fend) {
        CriteriaBuilder Builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Currency> Query = Builder.createQuery(Currency.class);
        Root<Currency> from = Query.from(Currency.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(Builder.equal(Builder.upper(from.get("code")), code.toUpperCase()));

        if(!finit.isEmpty()){
            predicates.add(Builder.greaterThanOrEqualTo(from.get("requestDate"), LocalDateTime.parse(finit)));
        }

        if(!fend.isEmpty())
        {
            predicates.add(Builder.lessThanOrEqualTo(from.get("requestDate"), LocalDateTime.parse(fend)));
        }

        Query.select(from).where(predicates.toArray(new Predicate[0]));

        return  entityManager.createQuery(Query).getResultList();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
