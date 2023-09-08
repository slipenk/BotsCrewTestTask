package com.slipenk.bc.controller;

import com.slipenk.bc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENTS_PATH;
import static com.slipenk.bc.dictionary.Dictionary.UNIVERSITY_PATH;

@RestController
@RequestMapping(UNIVERSITY_PATH)
public class DepartmentController {

    private final DepartmentService universityService;

    @Autowired
    public DepartmentController(DepartmentService universityService) {
        this.universityService = universityService;
    }

    @PostMapping(DEPARTMENTS_PATH)
    public String getAnswer(@RequestBody String question) {
        return universityService.getAnswer(question);
    }

}
