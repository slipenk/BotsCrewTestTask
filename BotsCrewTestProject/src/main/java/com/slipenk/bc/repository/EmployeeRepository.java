package com.slipenk.bc.repository;

import com.slipenk.bc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT CONCAT(e.name, ' ', e.surname) FROM Employee e WHERE e.name LIKE %?1% OR e.surname LIKE %?1%")
    List<String> findEmployeesWithTemplate(String template);

    @Query("SELECT CONCAT(e.name, ' ', e.surname) FROM Employee e WHERE e.id = ?1")
    String findEmployeeByIdWithConcatenation(int id);
}
