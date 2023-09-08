package com.slipenk.bc.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENT_EMPLOYEE;
import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENT_ID;
import static com.slipenk.bc.dictionary.Dictionary.EMPLOYEE;
import static com.slipenk.bc.dictionary.Dictionary.EMPLOYEE_ID;
import static com.slipenk.bc.dictionary.Dictionary.ID;
import static com.slipenk.bc.dictionary.Dictionary.NAME;
import static com.slipenk.bc.dictionary.Dictionary.POSITION;
import static com.slipenk.bc.dictionary.Dictionary.SALARY;
import static com.slipenk.bc.dictionary.Dictionary.SURNAME;

@Entity
@Table(name = EMPLOYEE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private int id;

    @Column(name = NAME)
    private String name;

    @Column(name = SURNAME)
    private String surname;

    @Column(name = SALARY)
    private double salary;

    @Enumerated(EnumType.STRING)
    @Column(name = POSITION)
    private UniversityWorkerPosition position;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = DEPARTMENT_EMPLOYEE,
            joinColumns = @JoinColumn(name = EMPLOYEE_ID),
            inverseJoinColumns = @JoinColumn(name = DEPARTMENT_ID)
    )
    private List<Department> departments;

}
