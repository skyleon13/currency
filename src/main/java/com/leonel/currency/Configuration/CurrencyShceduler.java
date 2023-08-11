package com.leonel.currency.Configuration;

import com.leonel.currency.Controller.CurrencyController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class CurrencyShceduler {
    @Autowired
    private CurrencyController controller;

    @Scheduled(fixedRateString = "${api.interval}")
    public void main(){
        controller.mainScheduled();
    }
}
