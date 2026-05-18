package com.example.sdetapi.integration;

import com.example.sdetapi.model.Employee;
import com.example.sdetapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeDatabaseValidationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void cleanDatabase() {
        employeeRepository.deleteAll();
    }

    @Test
    void shouldPersistEmployeeAndValidateDatabaseRecord() {
        Employee saved = employeeRepository.save(
                new Employee("Database Tester", "dbtester@example.com", "QA", 87000)
        );

        Optional<Employee> employeeFromDb = employeeRepository.findByEmail("dbtester@example.com");

        assertThat(employeeFromDb).isPresent();
        assertThat(employeeFromDb.get().getId()).isEqualTo(saved.getId());
        assertThat(employeeFromDb.get().getDepartment()).isEqualTo("QA");
        assertThat(employeeFromDb.get().getSalary()).isEqualTo(87000);
    }
}
