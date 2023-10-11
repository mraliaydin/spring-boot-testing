package com.aliaydin.springboot.service;

import com.aliaydin.springboot.entity.Employee;
import com.aliaydin.springboot.exception.ResourceNotFoundException;
import com.aliaydin.springboot.repository.EmployeeRepository;
import com.aliaydin.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    private  Employee employee;
    @BeforeEach
    public void setup(){
        employee = Employee.builder()
            .id(1L)
            .firstName("Ali")
            .lastName("Aydin")
            .email("a@a.com")
            .build();
    }


    // JUnit test for saveEmployee method
    @Test
    @DisplayName("saveEmployee method")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        // Given - precondition
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // When - behaviour
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // Then - Verify the output
        Assertions.assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for saveEmployee method
    @Test
    @DisplayName("saveEmployee method which throws exception")
    public void givenExistEmail_whenSaveEmployee_thenThrowsException(){
        // Given - precondition
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // When - behaviour
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
                ()->{
            employeeService.saveEmployee(employee);
                });

        // Then - Verify the output
        BDDMockito.verify(employeeRepository, Mockito.never()).save(employee);
    }

    // JUnit test for getAllEmployees method
    @Test
    @DisplayName("Get All Employees")
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployees(){
        // Given - precondition
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("John")
                .lastName("Doe")
                .email("j@d.com")
                .build();

        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // When - behaviour
        List<Employee> employees = employeeService.getAllEmployees();

        // Then - Verify the output
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Get Empty Employees list (negative scenario)")
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployees(){
        // Given - precondition
        BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // When - behaviour
        List<Employee> employees = employeeService.getAllEmployees();

        // Then - Verify the output
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees).isEmpty();
        Assertions.assertThat(employees.size()).isEqualTo(0);
    }

    // JUnit test for
    @Test
    @DisplayName("Get Employee By Id")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        // Given - precondition
        BDDMockito.given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        // When - behaviour
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employee.getId());

        // Then - Verify the output
        Assertions.assertThat(employeeOptional).isNotNull();
    }


    @Test
    @DisplayName("Get Employee By Id (negative scenario)")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmptyEmployee(){
        // Given - precondition
        BDDMockito.given(employeeRepository.findById(employee.getId())).willReturn(Optional.empty());

        // When - behaviour
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employee.getId());

        // Then - Verify the output
        Assertions.assertThat(employeeOptional).isEmpty();

    }

    // JUnit test for
    @Test
    @DisplayName("Update Employee")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // Given - precondition
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("b@b.com");
        employee.setLastName("ASD");

        // When - behaviour
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // Then - Verify the output
        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("b@b.com");
        Assertions.assertThat(updatedEmployee.getLastName()).isEqualTo("ASD");

    }

    // JUnit test for
    @Test
    @DisplayName("Delete Employee By ID")
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        // Given - precondition
        BDDMockito.willDoNothing().given(employeeRepository).deleteById(employee.getId());

        // When - behaviour
        employeeService.deleteEmployee(employee.getId());

        // Then - Verify the output
        BDDMockito.verify(employeeRepository, Mockito.times(1)).deleteById(employee.getId());
    }
}
