package com.slipenk.bc.service;

import com.slipenk.bc.dto.StatisticsDTO;
import com.slipenk.bc.exceptions.DepartmentOrEmployeeNotFoundException;
import com.slipenk.bc.repository.DepartmentRepository;
import com.slipenk.bc.repository.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.slipenk.bc.dictionary.Dictionary.ASSISTANTS;
import static com.slipenk.bc.dictionary.Dictionary.ASSOCIATE_PROFESSORS;
import static com.slipenk.bc.dictionary.Dictionary.CHECK_QUESTION;
import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENT;
import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENT_IS;
import static com.slipenk.bc.dictionary.Dictionary.DEPARTMENT_OR_EMPLOYEE_NOT_FOUND;
import static com.slipenk.bc.dictionary.Dictionary.FULL_STOP;
import static com.slipenk.bc.dictionary.Dictionary.FULL_STOP_WITH_NEW_ROW;
import static com.slipenk.bc.dictionary.Dictionary.HYPHEN_WITH_SPACES;
import static com.slipenk.bc.dictionary.Dictionary.PROFESSORS;
import static com.slipenk.bc.dictionary.Dictionary.QUESTION_MARK;
import static com.slipenk.bc.dictionary.Dictionary.SHOW;
import static com.slipenk.bc.dictionary.Dictionary.SHOW_COUNT_OF_EMPLOYEES_FOR_THE;
import static com.slipenk.bc.dictionary.Dictionary.SHOW_THE_AVERAGE_SALARY_FOR_THE;
import static com.slipenk.bc.dictionary.Dictionary.SPACE;
import static com.slipenk.bc.dictionary.Dictionary.STATISTICS;
import static com.slipenk.bc.dictionary.Dictionary.THE_AVERAGE_SALARY_OF_THE;
import static com.slipenk.bc.dictionary.Dictionary.THE_HEAD_OF_THE;
import static com.slipenk.bc.dictionary.Dictionary.WHO_IS_THE_HEAD_OF_THE;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public String getAnswer(String question) {
        if (question.startsWith(WHO_IS_THE_HEAD_OF_THE)) {
            return getHeadOfDepartment(question);
        } else if (question.startsWith(SHOW) && question.endsWith(STATISTICS + FULL_STOP)) {
            return getStatistics(question);
        } else if (question.startsWith(SHOW_THE_AVERAGE_SALARY_FOR_THE)) {
            return getAverageSalary(question);
        } else if (question.startsWith(SHOW_COUNT_OF_EMPLOYEES_FOR_THE)) {
            return getCountOfEmployees(question);
        }
        return CHECK_QUESTION;
    }

    private String getCountOfEmployees(String question) {
        String firstString = StringUtils.substringAfter(question, SHOW_COUNT_OF_EMPLOYEES_FOR_THE).strip();
        String departmentName = StringUtils.substringBefore(firstString, DEPARTMENT + FULL_STOP).strip();
        Long count = departmentRepository.findEmployeeCountByDepartmentName(departmentName);
        if (count == null) {
            throw new DepartmentOrEmployeeNotFoundException(DEPARTMENT_OR_EMPLOYEE_NOT_FOUND);
        }
        return String.valueOf(count);
    }

    private String getAverageSalary(String question) {
        String firstString = StringUtils.substringAfter(question, SHOW_THE_AVERAGE_SALARY_FOR_THE).strip();
        String departmentName = StringUtils.substringBefore(firstString, DEPARTMENT + FULL_STOP).strip();
        Double averageSalary = departmentRepository.findAverageSalaryByDepartment(departmentName);
        if (averageSalary == null) {
            throw new DepartmentOrEmployeeNotFoundException(DEPARTMENT_OR_EMPLOYEE_NOT_FOUND);
        }
        return THE_AVERAGE_SALARY_OF_THE + departmentName + DEPARTMENT_IS + averageSalary + FULL_STOP;
    }

    private String getStatistics(String question) {
        String firstString = StringUtils.substringAfter(question, SHOW).strip();
        String departmentName = StringUtils.substringBefore(firstString, DEPARTMENT + SPACE + STATISTICS + FULL_STOP).strip();
        StatisticsDTO statisticsDTO = departmentRepository.getDepartmentStatisticsByDepartmentName(departmentName);
        if (statisticsDTO == null) {
            throw new DepartmentOrEmployeeNotFoundException(DEPARTMENT_OR_EMPLOYEE_NOT_FOUND);
        }
        return getCountOfEmployeesByPosition(statisticsDTO);
    }

    private String getCountOfEmployeesByPosition(StatisticsDTO statisticsDTO) {
        Long assistantsCount = statisticsDTO.getAssistantsCount();
        Long associateProfessorsCount = statisticsDTO.getAssociateProfessorsCount();
        Long professorsCount = statisticsDTO.getProfessorsCount();
        if (assistantsCount == null || associateProfessorsCount == null || professorsCount == null) {
            throw new DepartmentOrEmployeeNotFoundException(DEPARTMENT_OR_EMPLOYEE_NOT_FOUND);
        }
        return ASSISTANTS + HYPHEN_WITH_SPACES + assistantsCount + FULL_STOP_WITH_NEW_ROW +
                ASSOCIATE_PROFESSORS + HYPHEN_WITH_SPACES + associateProfessorsCount + FULL_STOP_WITH_NEW_ROW +
                PROFESSORS + HYPHEN_WITH_SPACES + professorsCount + FULL_STOP;
    }

    private String getHeadOfDepartment(String question) {
        String firstString = StringUtils.substringAfter(question, WHO_IS_THE_HEAD_OF_THE).strip();
        String departmentName = StringUtils.substringBefore(firstString, DEPARTMENT + QUESTION_MARK).strip();
        Integer employeeId = departmentRepository.findEmployeeIdByDepartmentName(departmentName);
        if (employeeId == null) {
            throw new DepartmentOrEmployeeNotFoundException(DEPARTMENT_OR_EMPLOYEE_NOT_FOUND);
        }
        return getNameAndSurnameOfEmployee(employeeId, departmentName);
    }

    private String getNameAndSurnameOfEmployee(Integer employeeId, String departmentName) {
        String employeeNameAndSurname = employeeRepository.findEmployeeByIdWithConcatenation(employeeId);
        if (employeeNameAndSurname == null) {
            throw new DepartmentOrEmployeeNotFoundException(DEPARTMENT_OR_EMPLOYEE_NOT_FOUND);
        }
        return THE_HEAD_OF_THE + departmentName + DEPARTMENT_IS + employeeNameAndSurname + FULL_STOP;
    }

}
