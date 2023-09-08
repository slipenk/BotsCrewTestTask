package com.slipenk.bc.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENT;
import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENT_EMPLOYEE;
import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENT_ID;
import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENT_NAME;
import static com.slipenk.bc.dictionary.Dictionary.EMPLOYEE_ID;
import static com.slipenk.bc.dictionary.Dictionary.HEAD_OF_DEPARTMENT;
import static com.slipenk.bc.dictionary.Dictionary.ID;

@Entity
@Table(name = DEPARTMENT)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private int id;

    @Column(name = DEPARTMENT_NAME, unique = true)
    private String departmentName;

    @Column(name = HEAD_OF_DEPARTMENT, unique = true)
    private int employeeId;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = DEPARTMENT_EMPLOYEE,
            joinColumns = @JoinColumn(name = DEPARTMENT_ID),
            inverseJoinColumns = @JoinColumn(name = EMPLOYEE_ID)
    )
    private List<Employee> employees;

}
