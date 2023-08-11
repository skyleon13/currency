package com.leonel.currency.Repository;

import com.leonel.currency.Entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface CurrencyRepo extends JpaRepository<Currency,Integer> {
    public Currency findByCode(String code);
}
