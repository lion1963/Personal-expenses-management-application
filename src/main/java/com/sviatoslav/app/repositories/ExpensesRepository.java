package com.sviatoslav.app.repositories;

import com.sviatoslav.app.entities.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {

    List<Expenses> findAllByOrderByDateAsc();

    void deleteByDate(Date date);
}
