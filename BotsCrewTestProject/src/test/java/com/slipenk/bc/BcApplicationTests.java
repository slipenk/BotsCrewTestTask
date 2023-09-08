package com.slipenk.bc;

import com.slipenk.bc.controller.DepartmentController;
import com.slipenk.bc.controller.EmployeeController;
import com.slipenk.bc.entity.Department;
import com.slipenk.bc.entity.Employee;
import com.slipenk.bc.entity.UniversityWorkerPosition;
import com.slipenk.bc.exceptions.DepartmentOrEmployeeNotFoundException;
import com.slipenk.bc.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BcApplicationTests {

    private final DepartmentRepository departmentRepository;
    private final DepartmentController departmentController;
    private final EmployeeController employeeController;

    @Autowired
    BcApplicationTests(DepartmentRepository departmentRepository, DepartmentController departmentController,
                       EmployeeController employeeController) {
        this.departmentRepository = departmentRepository;
        this.departmentController = departmentController;
        this.employeeController = employeeController;
    }

    @BeforeEach
    @Transactional
    void fillDatabase() {
        Employee employee1 = new Employee(1, "Virgil", "Van Dijk", 15000, UniversityWorkerPosition.PROFESSOR, null);
        Employee employee2 = new Employee(2, "Mohammed", "Salah", 20000, UniversityWorkerPosition.ASSISTANT, null);
        Employee employee3 = new Employee(3, "Joe", "Gomez", 48000.9, UniversityWorkerPosition.ASSOCIATE_PROFESSOR, null);
        Employee employee4 = new Employee(4, "Luis", "Diaz", 41800.9, UniversityWorkerPosition.PROFESSOR, null);
        Employee employee5 = new Employee(5, "Ibrahima", "Konate", 89000.9, UniversityWorkerPosition.PROFESSOR, null);
        Employee employee6 = new Employee(6, "Cody", "Gakpo", 1200.9, UniversityWorkerPosition.ASSISTANT, null);

        Department department1 = new Department(-1, "Applied Mathematics", 1, List.of(employee1, employee2, employee3, employee4, employee5, employee6));
        Department department2 = new Department(-1, "Applied Physics and Nanomaterials Science", 2, List.of(employee1, employee2, employee3, employee4, employee5, employee6));
        Department department3 = new Department(-1, "Mathematics", 3, List.of(employee1, employee2, employee3, employee4, employee5, employee6));
        Department department4 = new Department(-1, "Highways and Bridges", 4, List.of(employee1, employee2, employee3, employee4, employee5, employee6));
        Department department5 = new Department(-1, "Computer-Aided Design", 5, List.of(employee1, employee2, employee3, employee4, employee5, employee6));
        Department department6 = new Department(-1, "Philosophy", -1, null);
        Department department7 = new Department(-1, "Sociology and Social Work", -2, List.of(employee5));

        departmentRepository.saveAll(List.of(department1, department2, department3, department4, department5, department6, department7));
    }

    void testAnswersDepartments(String question, String answer) {
        Assertions.assertEquals(answer, departmentController.getAnswer(question));
    }

    void testAnswersEmployees(String question, String answer) {
        Assertions.assertEquals(answer, employeeController.getAnswer(question));
    }

    @Test
    void testHeadOfDepartmentJoeGomez() {
        testAnswersDepartments("Who is the head of the Mathematics department?",
                "The head of the Mathematics department is Joe Gomez.");
    }

    @Test
    void testHeadOfDepartmentIbrahimaKonate() {
        testAnswersDepartments("Who is the head of the Computer-Aided Design department?",
                "The head of the Computer-Aided Design department is Ibrahima Konate.");
    }

    @Test
    void testHeadOfWrongDepartment() {
        assertThrows(DepartmentOrEmployeeNotFoundException.class, () ->
                testAnswersDepartments("Who is the head of the Salah department?",
                        ""));
    }

    @Test
    void testHeadOfDepartmentWrongQuestion() {
        testAnswersDepartments("Who the head of the Computer-Aided Design department?",
                "Please, check your question. It must follow the appropriate format.");
    }

    @Test
    void testNoHeadOfDepartment() {
        assertThrows(DepartmentOrEmployeeNotFoundException.class, () ->
                testAnswersDepartments("Who is the head of the Philosophy department?",
                        ""));
    }

    @Test
    void testShowDepartmentStatistics() {
        testAnswersDepartments("Show Highways and Bridges department statistics.",
                """
                        Assistants - 2.
                        Associate professors - 1.
                        Professors - 3.""");
    }

    @Test
    void testShowDepartmentStatisticsOnlyOne() {
        testAnswersDepartments("Show Sociology and Social Work department statistics.",
                """
                        Assistants - 0.
                        Associate professors - 0.
                        Professors - 1.""");
    }

    @Test
    void testShowDepartmentStatisticsForNoDepartment() {
        assertThrows(DepartmentOrEmployeeNotFoundException.class, () ->
                testAnswersDepartments("Show Salah department statistics.",
                        ""));
    }

    @Test
    void testShowDepartmentStatisticsForNoEmployees() {
        assertThrows(DepartmentOrEmployeeNotFoundException.class, () ->
                testAnswersDepartments("Show Philosophy department statistics.",
                        ""));
    }

    @Test
    void testShowDepartmentStatisticsWrongQuestion() {
        testAnswersDepartments("Show Sociology and Social Work department statistics int.",
                "Please, check your question. It must follow the appropriate format.");
    }

    @Test
    void testShowAverageSalary() {
        testAnswersDepartments("Show the average salary for the Applied Physics and Nanomaterials Science department.",
                "The average salary of the Applied Physics and Nanomaterials Science department is 35833.93333333333.");
    }

    @Test
    void testShowAverageSalaryNoDepartment() {
        assertThrows(DepartmentOrEmployeeNotFoundException.class, () ->
                testAnswersDepartments("Show the average salary for the Salah Science department.",
                        ""));
    }

    @Test
    void testShowAverageSalaryNoEmployee() {
        assertThrows(DepartmentOrEmployeeNotFoundException.class, () ->
                testAnswersDepartments("Show the average salary for the Philosophy department.",
                        ""));
    }

    @Test
    void testShowAverageSalaryWrongQuestion() {
        testAnswersDepartments("Show salary for the Applied Physics and Nanomaterials Science department.",
                "Please, check your question. It must follow the appropriate format.");
    }

    @Test
    void test4ShowCountOfEmployeesCAD() {
        testAnswersDepartments("Show count of employees for the Computer-Aided Design department.",
                "6");
    }

    @Test
    void test4ShowCountOfEmployeesAPNS() {
        testAnswersDepartments("Show count of employees for the Applied Physics and Nanomaterials Science department.",
                "6");
    }

    @Test
    void testShowCountNoDepartment() {
        testAnswersDepartments("Show count of employees for the Salah department.",
                "0");
    }

    @Test
    void testShowCountNoEmployees() {
        testAnswersDepartments("Show count of employees for the Philosophy department.",
                "0");
    }

    @Test
    void testShowCountWrongQuestion() {
        testAnswersDepartments("Show employees for the Computer-Aided Design department.",
                "Please, check your question. It must follow the appropriate format.");
    }

    @Test
    void testGlobalSearch() {
        testAnswersEmployees("Global search by i",
                "Virgil Van Dijk, Luis Diaz, Ibrahima Konate.");
    }

    @Test
    void testGlobalSearchSalah() {
        testAnswersEmployees("Global search by Salah",
                "Mohammed Salah.");
    }

    @Test
    void testGlobalSearchNoTemplate() {
        testAnswersEmployees("Global search by Salah-ah",
                "No acceptable result.");
    }

    @Test
    void testGlobalSearchWrongQuestion() {
        testAnswersEmployees("Global by i",
                "Please, check your question. It must follow the appropriate format.");
    }

}
