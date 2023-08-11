package com.leonel.currency;

import com.leonel.currency.Controller.CurrencyController;
import com.leonel.currency.Entity.Currency;
import com.leonel.currency.Service.CurrencyServiceIMPL.CurrencyServiceIMPL;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CurrencyApplication {
	@Value("${api.Connect.timeout}")
	int timeoutApiConnect;
	@Value("${api.Read.timeout}")
	int timeoutApiRead;

	public static void main(String[] args) {
		SpringApplication.run(CurrencyApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate(getClientHttpRequestFactory());
	}

	@Bean
	public ClientHttpRequestFactory getClientHttpRequestFactory(){
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(timeoutApiConnect);
		factory.setReadTimeout(timeoutApiRead);
		return  factory;
	}

}
