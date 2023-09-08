package com.slipenk.bc.service;

import com.slipenk.bc.repository.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.slipenk.bc.dictionary.Dictionary.CHECK_QUESTION;
import static com.slipenk.bc.dictionary.Dictionary.COMMA_WITH_SPACE;
import static com.slipenk.bc.dictionary.Dictionary.FULL_STOP;
import static com.slipenk.bc.dictionary.Dictionary.GLOBAL_SEARCH_BY;
import static com.slipenk.bc.dictionary.Dictionary.NO_RESULT;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String getAnswer(String question) {
        if (question.startsWith(GLOBAL_SEARCH_BY)) {
            return getGlobalSearch(question);
        }
        return CHECK_QUESTION;
    }

    private String getGlobalSearch(String question) {
        String template = StringUtils.substringAfter(question, GLOBAL_SEARCH_BY).strip();
        List<String> resultList = employeeRepository.findEmployeesWithTemplate(template);
        if (resultList.isEmpty()) {
            return NO_RESULT;
        }
        return StringUtils.join(resultList, COMMA_WITH_SPACE) + FULL_STOP;
    }
}
