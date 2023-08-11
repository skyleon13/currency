package com.leonel.currency.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonel.currency.Entity.Currency;
import com.leonel.currency.Entity.Request;
import com.leonel.currency.Service.CurrencyServiceIMPL.CurrencyServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/currencies")
public class CurrencyController {

    @Value("${api.currency.uriLatest}")
    String uri;
    @Value("${api.currency.apikey}")
    String apikey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyServiceIMPL currency;

    public CurrencyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void mainScheduled(){
        Request request = new Request();
        try {
            request = ExecuteApi();
            currency.SaveRequest(request);
            currency.SaveCurrencies(request);
        }
        catch (ParseException | JsonProcessingException ie){
            Thread.currentThread().interrupt();
        }
    }

    @GetMapping
    @RequestMapping(value = "{cur}", method = RequestMethod.GET)
    public ResponseEntity<?> CurrencyNormal(@PathVariable String cur,
                                      @RequestParam(name="finit",required=false,defaultValue = "") String finit,
                                      @RequestParam(name="fend",required=false,defaultValue = "") String fend){
        List<Currency> list;

        try {
            if (!finit.isEmpty())LocalDateTime.parse(finit);
            if (!fend.isEmpty())LocalDateTime.parse(fend);
        }catch (DateTimeParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Parameters");
        }

        if(Objects.equals(cur.toUpperCase(), "ALL"))
        {
            list = currency.GetAllCurrencies();
        }
        else {
            list = currency.SearchCurrencyByCodeAndDate(cur,finit,fend);
        }

        if(!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        else {
            if(Objects.equals(cur.toUpperCase(), "ALL")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found currencies in Database");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Currency "+ cur +" not found");
            }
        }
    }

    private Request ExecuteApi(){
        Request req= new Request();
        long startTime=0;
        try {
            String urlCurrencyApi = uri + apikey;
            req.setUrl(urlCurrencyApi);
            ObjectMapper mapper = new ObjectMapper();

            startTime = System.currentTimeMillis();
            req.setRequestDateTime(LocalDateTime.now());
            Object objResponse = restTemplate.getForObject(urlCurrencyApi, Object.class);
            req.responseTimeMS = System.currentTimeMillis() - startTime;
            req.response = mapper.writeValueAsString(objResponse);
            req.status = 200;
        }
        catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc){
            req.responseTimeMS = System.currentTimeMillis() - startTime;
            req.response = httpClientOrServerExc.getMessage();
            req.status = httpClientOrServerExc.getStatusCode().value();
        }
        catch (JsonProcessingException ex) {
            req.responseTimeMS = System.currentTimeMillis() - startTime;
            req.response = ex.getMessage();
            req.status = 503;
        }
        catch (ResourceAccessException accessException){
            req.responseTimeMS = System.currentTimeMillis() - startTime;
            req.response = accessException.getMessage();
            req.status = 408;
        }

        return req;
    }
}
