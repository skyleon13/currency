package com.leonel.currency.Repository;

import com.leonel.currency.Entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepo extends CrudRepository<History,Integer> {
}
