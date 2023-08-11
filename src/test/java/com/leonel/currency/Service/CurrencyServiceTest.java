package com.leonel.currency.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leonel.currency.Entity.Currency;
import com.leonel.currency.Entity.History;
import com.leonel.currency.Entity.Request;
import com.leonel.currency.Entity.Response;
import com.leonel.currency.Repository.CurrencyRepo;
import com.leonel.currency.Repository.HistoryRepo;
import com.leonel.currency.Service.CurrencyServiceIMPL.CurrencyServiceIMPL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CurrencyServiceTest {

    @Mock
    private CurrencyRepo currencyRepo;

    @Mock
    private HistoryRepo historyRepo;

    private CurrencyServiceIMPL currencyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        currencyService = new CurrencyServiceIMPL(currencyRepo, historyRepo);
    }

    @Test
    public void testSaveRequest() {
        Request request = new Request();
        request.setUrl("https://catfact.ninja/fact?max_length=5000");
        request.setResponseTimeMS(100);
        request.setResponse("{\"fact\":\"Ancient Egyptian family members shaved their eyebrows in mourning when the family cat died.\",\"length\":91}");

        currencyService.SaveRequest(request);

        verify(historyRepo).save(any(History.class));
    }

    @Test
    public void testSaveCurrencies() throws ParseException, JsonProcessingException {
        String response = "{\n" +
                "  \"meta\": {\n" +
                "    \"last_updated_at\": \"2023-08-08T23:59:59Z\"\n" +
                "  },\n" +
                "  \"data\": {\n" +
                "    \"ADA\": {\n" +
                "      \"code\": \"ADA\",\n" +
                "      \"value\": 3.3541215215\n" +
                "    },\n" +
                "    \"AED\": {\n" +
                "      \"code\": \"AED\",\n" +
                "      \"value\": 3.6718105602\n" +
                "    },\n" +
                "    \"AFN\": {\n" +
                "      \"code\": \"AFN\",\n" +
                "      \"value\": 84.66218286\n" +
                "    }}}";

        Request request = new Request();
        request.setResponse(response);

        currencyService.SaveCurrencies(request);

        verify(currencyRepo, times(3)).save(any(Currency.class));
    }

    @Test
    public void testSearchCurrencyByCode() {
        Currency currency = new Currency();
        currency.setId(1);
        currency.setCode("USD");
        currency.setValue(1.0);
        currency.setLastUpdate(LocalDateTime.now());
        currency.setRequestDate(LocalDateTime.now());

        when(currencyRepo.findByCode("USD")).thenReturn(currency);

        Currency resultCurrency = currencyService.SearchCurrencyByCode("USD");

        assertNotNull(resultCurrency);
        assertEquals("USD", resultCurrency.getCode());
    }

    @Test
    public void testSearchCurrencyByCodeAndDate() {
        Currency currency1 = new Currency();
        currency1.setId(1);
        currency1.setCode("USD");
        currency1.setValue(1.0);
        currency1.setLastUpdate(LocalDateTime.now());
        currency1.setRequestDate(LocalDateTime.now());

        Currency currency2 = new Currency();
        currency2.setId(2);
        currency2.setCode("USD");
        currency2.setValue(3.0);
        currency2.setLastUpdate(LocalDateTime.now());
        currency2.setRequestDate(LocalDateTime.now());

        Currency currency3 = new Currency();
        currency3.setId(3);
        currency3.setCode("USD");
        currency3.setValue(5.8);
        currency3.setLastUpdate(LocalDateTime.now());
        currency3.setRequestDate(LocalDateTime.now());

        List<Currency> mockCurrencies = new ArrayList<>();
        mockCurrencies.add(currency1);
        mockCurrencies.add(currency2);
        mockCurrencies.add(currency3);

        LocalDateTime startDate = LocalDateTime.parse("2023-08-01T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2023-08-10T00:00:00");

        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Currency> criteriaQuery = mock(CriteriaQuery.class);
        Root<Currency> root = mock(Root.class);
        TypedQuery<Currency> query = mock(TypedQuery.class);

        Predicate codePredicate = criteriaBuilder.equal(root.get("code"), "USD");
        Predicate datePredicate = criteriaBuilder.between(root.get("requestDate"), startDate, endDate);

        EntityManager entityManager = mock(EntityManager.class);

        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Currency.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Currency.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaBuilder.equal(root.get("code"), "USD")).thenReturn(codePredicate);
        when(criteriaBuilder.between(root.get("requestDate"), startDate, endDate)).thenReturn(datePredicate);
        when(criteriaQuery.where(any(Predicate.class))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(mockCurrencies);

        // Set the EntityManager mock to your service
        currencyService.setEntityManager(entityManager);

        List<Currency> resultCurrencies = currencyService.SearchCurrencyByCodeAndDate("USD", "2023-08-01T00:00:00", "2023-08-10T00:00:00");

        assertEquals(mockCurrencies.size(), resultCurrencies.size());
        assertEquals("USD", resultCurrencies.get(0).getCode());
        assertEquals(1.0, resultCurrencies.get(0).getValue());
        assertEquals("USD", resultCurrencies.get(1).getCode());
        assertEquals(3.0, resultCurrencies.get(1).getValue());
        assertEquals("USD", resultCurrencies.get(2).getCode());
        assertEquals(5.8, resultCurrencies.get(2).getValue());
    }
}
