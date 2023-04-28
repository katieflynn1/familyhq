package com.fam.repository;

import com.fam.model.Statistics;
import com.fam.model.Task;
import com.fam.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

}