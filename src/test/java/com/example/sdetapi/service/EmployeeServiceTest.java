package com.example.sdetapi.service;

import com.example.sdetapi.exception.DuplicateEmployeeException;
import com.example.sdetapi.exception.EmployeeNotFoundException;
import com.example.sdetapi.model.Employee;
import com.example.sdetapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldCreateEmployeeWhenEmailIsUnique() {
        Employee employee = new Employee("API Tester", "api@example.com", "QA", 90000);
        when(employeeRepository.existsByEmail("api@example.com")).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee created = employeeService.createEmployee(employee);

        assertThat(created.getEmail()).isEqualTo("api@example.com");
        verify(employeeRepository).save(employee);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        Employee employee = new Employee("Duplicate", "duplicate@example.com", "QA", 90000);
        when(employeeRepository.existsByEmail("duplicate@example.com")).thenReturn(true);

        assertThatThrownBy(() -> employeeService.createEmployee(employee))
                .isInstanceOf(DuplicateEmployeeException.class)
                .hasMessageContaining("duplicate@example.com");

        verify(employeeRepository, never()).save(employee);
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        when(employeeRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(100L))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("100");
    }
}
