package com.slipenk.bc.repository;

import com.slipenk.bc.dto.StatisticsDTO;
import com.slipenk.bc.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Query("SELECT d.employeeId FROM Department d WHERE d.departmentName = :name")
    Integer findEmployeeIdByDepartmentName(@Param("name") String name);

    @Query("SELECT " +
            "NEW com.slipenk.bc.dto.StatisticsDTO(" +
            "SUM(CASE WHEN e.position = 'ASSISTANT' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN e.position = 'ASSOCIATE_PROFESSOR' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN e.position = 'PROFESSOR' THEN 1 ELSE 0 END)) " +
            "FROM Department d " +
            "JOIN d.employees e " +
            "WHERE d.departmentName = :departmentName")
    StatisticsDTO getDepartmentStatisticsByDepartmentName(@Param("departmentName") String departmentName);

    @Query("SELECT AVG(e.salary) FROM Department d JOIN d.employees e WHERE d.departmentName = :departmentName")
    Double findAverageSalaryByDepartment(@Param("departmentName") String departmentName);

    @Query("SELECT COUNT(e) FROM Department d JOIN d.employees e WHERE d.departmentName = :departmentName")
    Long findEmployeeCountByDepartmentName(@Param("departmentName") String departmentName);
}
