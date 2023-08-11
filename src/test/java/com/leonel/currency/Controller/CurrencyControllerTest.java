package com.leonel.currency.Controller;



import com.leonel.currency.CurrencyApplication;
import com.leonel.currency.Entity.Currency;
import com.leonel.currency.Service.CurrencyServiceIMPL.CurrencyServiceIMPL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {CurrencyApplication.class})
public class CurrencyControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private CurrencyServiceIMPL currencyServiceMock;

    @Before
    public void setUp(){
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllCurrencies() throws Exception {
        List<Currency> lista = arrayAllCurrencies();
        when(currencyServiceMock.GetAllCurrencies()).thenReturn(lista);

        mockMvc.perform(get("/currencies/All").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].code").value(lista.get(0).getCode()))
                .andExpect(jsonPath("$.[1].code").value(lista.get(1).getCode()))
                .andExpect(jsonPath("$.[2].code").value(lista.get(2).getCode()))
                .andExpect(jsonPath("$.[3].code").value(lista.get(3).getCode()))
                .andExpect(jsonPath("$.[*].value").isNotEmpty())
                .andDo(print());
    }

    @Test
    public void testGetAllCurrenciesNotFound() throws Exception {
        String message = "Not found currencies in Database";

        List<Currency> lista = new ArrayList<>();
        when(currencyServiceMock.GetAllCurrencies()).thenReturn(lista);

        mockMvc.perform(get("/currencies/ALL").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(message))
                .andDo(print());
    }

    @Test
    public void testGetCurrencyByCode() throws Exception {
        List<Currency> lista = arrayCurrencies("MXN");
        when(currencyServiceMock.SearchCurrencyByCodeAndDate("MXN","","")).thenReturn(lista);

        mockMvc.perform(get("/currencies/MXN").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].code").value(lista.get(0).getCode()))
                .andExpect(jsonPath("$.[*].value").isNotEmpty())
                .andDo(print());
    }

    @Test
    public void testGetCurrencyByCodeNotFound() throws Exception {
        String code = "MXN";
        String message = "Currency "+ code +" not found";

        List<Currency> lista = new ArrayList<>();
        when(currencyServiceMock.SearchCurrencyByCodeAndDate("MXN","","")).thenReturn(lista);

        mockMvc.perform(get("/currencies/MXN").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(message))
                .andDo(print());
    }

    @Test
    public void testGetCurrencyByCodeAndDate() throws Exception {
        List<Currency> lista = arrayCurrencies("MXN");
        when(currencyServiceMock.SearchCurrencyByCodeAndDate(
                "MXN","2023-08-12T12:00:00","2023-08-15T23:00:00"))
                .thenReturn(lista);

        String url = "/currencies/MXN?finit=2023-08-12T12:00:00&fend=2023-08-15T23:00:00";
        mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].code").value(lista.get(0).getCode()))
                .andExpect(jsonPath("$.[*].value").isNotEmpty())
                .andDo(print());
    }

    @Test
    public void testGetCurrencyByCodeAndDateOptional() throws Exception {
        List<Currency> lista = arrayCurrencies("MXN");
        when(currencyServiceMock.SearchCurrencyByCodeAndDate(
                "MXN","2023-08-12T12:00:00",""))
                .thenReturn(lista);

        String url = "/currencies/MXN?finit=2023-08-12T12:00:00";
        mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].code").value(lista.get(0).getCode()))
                .andExpect(jsonPath("$.[*].value").isNotEmpty())
                .andDo(print());
    }

    @Test
    public void testBadRequest() throws Exception {
        String message = "Invalid Parameters";
        List<Currency> lista = arrayCurrencies("MXN");
        when(currencyServiceMock.SearchCurrencyByCodeAndDate(
                "MXN","Hello","")).thenReturn(lista);

        String url = "/currencies/MXN?finit=Hello";
        mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(message))
                .andDo(print());
    }

    public List<Currency> arrayAllCurrencies(){
        List<Currency> lista = new ArrayList<>();
        Currency currency;

        currency = new Currency();
        currency.setId(5);
        currency.setCode("ADA");
        currency.setValue(9.9999);
        currency.setLastUpdate(LocalDateTime.parse("2023-08-10T20:59:59"));
        currency.setRequestDate(LocalDateTime.parse("2023-08-12T16:30:00"));
        lista.add(currency);

        currency = new Currency();
        currency.setId(5);
        currency.setCode("MXN");
        currency.setValue(12.555);
        currency.setLastUpdate(LocalDateTime.parse("2023-08-10T20:59:59"));
        currency.setRequestDate(LocalDateTime.parse("2023-08-12T16:30:00"));
        lista.add(currency);

        currency = new Currency();
        currency.setId(5);
        currency.setCode("USD");
        currency.setValue(15.222);
        currency.setLastUpdate(LocalDateTime.parse("2023-08-10T20:59:59"));
        currency.setRequestDate(LocalDateTime.parse("2023-08-12T16:30:00"));
        lista.add(currency);

        currency = new Currency();
        currency.setId(5);
        currency.setCode("EUR");
        currency.setValue(19.222);
        currency.setLastUpdate(LocalDateTime.parse("2023-08-10T20:59:59"));
        currency.setRequestDate(LocalDateTime.parse("2023-08-12T16:30:00"));
        lista.add(currency);

        return lista;
    }

    public List<Currency> arrayCurrencies(String code){
        List<Currency> lista = new ArrayList<>();
        Currency currency;

        currency = new Currency();
        currency.setId(5);
        currency.setCode(code);
        currency.setValue(9.9999);
        currency.setLastUpdate(LocalDateTime.parse("2023-08-10T20:59:59"));
        currency.setRequestDate(LocalDateTime.parse("2023-08-12T16:30:00"));
        lista.add(currency);

        currency = new Currency();
        currency.setId(5);
        currency.setCode(code);
        currency.setValue(12.555);
        currency.setLastUpdate(LocalDateTime.parse("2023-08-11T20:59:59"));
        currency.setRequestDate(LocalDateTime.parse("2023-08-14T12:30:00"));
        lista.add(currency);

        currency = new Currency();
        currency.setId(5);
        currency.setCode(code);
        currency.setValue(15.222);
        currency.setLastUpdate(LocalDateTime.parse("2023-08-12T20:59:59"));
        currency.setRequestDate(LocalDateTime.parse("2023-08-15T12:30:00"));
        lista.add(currency);

        return lista;
    }
}
