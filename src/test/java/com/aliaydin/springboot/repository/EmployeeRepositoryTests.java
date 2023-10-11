package com.aliaydin.springboot.repository;

import com.aliaydin.springboot.entity.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Ali")
                .lastName("Aydin")
                .email("a@a.com")
                .build();
    }

    //JUnit test for save employee operation
    @Test
    @DisplayName("Save Employee Operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        // Given - precondition or setup
        /*

        Employee employee = Employee.builder()
                .firstName("Ali")
                .lastName("Aydin")
                .email("a@a.com")
                .build();

         */


        /*
        Employee employee1 = new Employee();
        employee1.setFirstName("ALi");
        employee1.setLastName("Aydin");
        employee1.setEmail("a@a.com");
        */


        // when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    // JUnit test for Get All Employees
    @Test
    @DisplayName("Get All Employees")
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        // Given - precondition
        Employee employee1 = Employee.builder()
                .firstName("Ali")
                .lastName("Aydin")
                .email("a@a.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Veli")
                .lastName("Deli")
                .email("v@d.com")
                .build();

        Employee employee3 = Employee.builder()
                .firstName("Mahmut")
                .lastName("Orhan")
                .email("m@o.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);


        // When - behaviour
        List<Employee> employees = employeeRepository.findAll();


        // Then - Verify the output
        assertThat(employees).isNotNull();
        assertThat(employees.size()).isEqualTo(3);

    }

    // JUnit test for Get Employee By id
    @Test
    @DisplayName("Get Employee By Id")
    public void givenEmployee_whenFindById_thenReturnEmployee(){
        // Given - precondition
        Employee employee = Employee.builder()
                .firstName("Ali")
                .lastName("Aydin")
                .email("a@a.com")
                .build();

        employeeRepository.save(employee);

        // When - behaviour
        Employee getEmployee = employeeRepository.findById(employee.getId()).get();

        // Then - Verify the output
        assertThat(getEmployee).isNotNull();
        assertThat(getEmployee.getId()).isGreaterThan(0);

    }

    // JUnit test for Get Employee by Email
    @Test
    @DisplayName("Get Employee By Email")
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        // Given - precondition
        Employee employee = Employee.builder()
                .firstName("Ali")
                .lastName("Aydin")
                .email("a@a.com")
                .build();

        employeeRepository.save(employee);

        // When - behaviour
        Employee findEmployee = employeeRepository.findByEmail(employee.getEmail()).get();

        // Then - Verify the output
        assertThat(findEmployee).isNotNull();

    }


    // JUnit test for update employee operation
    @Test
    @DisplayName("Update employee by id")
    public void givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // Given - precondition
        Employee employee = Employee.builder()
                .firstName("Ali")
                .lastName("Aydin")
                .email("a@a.com")
                .build();

        employeeRepository.save(employee);

        // When - behaviour
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("qwe@qwe.com");
        savedEmployee.setFirstName("Joe");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // Then - Verify the output
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getEmail()).isEqualTo("qwe@qwe.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Joe");
    }

    // JUnit test for Delete employee
    @Test
    @DisplayName("Delete Employee by Id")
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        // Given - precondition
        Employee employee = Employee.builder()
                .firstName("Ali")
                .lastName("Aydin")
                .email("a@a.com")
                .build();

        employeeRepository.save(employee);

        // When - behaviour
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // Then - Verify the output
        assertThat(employeeOptional).isEmpty();

    }

    // JUnit test for custom JPQL query
    @Test
    @DisplayName("Find employee by first and last name")
    public void givenFirstAndLastName_whenFindByJPQL_thenReturnEmployee(){
        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ali")
                .lastName("Aydin")
                .email("a@a,com")
                .build();
        employeeRepository.save(employee);
        String firstName = "Ali";
        String lastName = "Aydin";

        // when -  action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

}
