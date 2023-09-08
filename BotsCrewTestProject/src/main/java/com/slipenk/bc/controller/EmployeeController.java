package com.slipenk.bc.controller;

import com.slipenk.bc.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.slipenk.bc.dictionary.Dictionary.EMPLOYEES_PATH;
import static com.slipenk.bc.dictionary.Dictionary.UNIVERSITY_PATH;

@RestController
@RequestMapping(UNIVERSITY_PATH)
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(EMPLOYEES_PATH)
    public String getAnswer(@RequestBody String question) {
        return employeeService.getAnswer(question);
    }

}
