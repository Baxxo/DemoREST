package ama.crai.demo.controller;

import ama.crai.demo.configuration.LoadDatabase;
import ama.crai.demo.entity.Employee;
import ama.crai.demo.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    private Long id = 1L;

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @BeforeEach
    void setUp() {
        Employee employee = new Employee("testFirstName", "testLastName", "testRole");

        ResponseEntity<EntityModel<Employee>> entityModelResponseEntity = employeeController.newEmployee(employee);

        id = Objects.requireNonNull(Objects.requireNonNull(entityModelResponseEntity.getBody()).getContent()).getId();

        log.info("Preloading " + id);
    }

    @Test
    void all() {
        Collection<EntityModel<Employee>> entities = employeeController.all().getContent();
        assertThat(entities.isEmpty()).isFalse();

        entities.forEach(entity -> {
            Employee employee = entity.getContent();
            assertThat(employee).isNotNull();
        });

    }

    @Test
    void one() {
        Employee employee = employeeController.one(id).getContent();

        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(id);
    }

    @Test
    void oneNotFound() {
        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> employeeController.one(100L).getContent());

        String expectedMessage = EmployeeNotFoundException.MESSAGE;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void newEmployee() {
        Employee employee = new Employee("testFirstName", "testLastName", "testRole");

        ResponseEntity<EntityModel<Employee>> entityModelResponseEntity = employeeController.newEmployee(employee);

        assertThat(entityModelResponseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(entityModelResponseEntity.getBody()).isNotNull();
        assertThat(entityModelResponseEntity.getBody().getContent()).isNotNull();
        assertThat(entityModelResponseEntity.getBody().getContent().getFirstName()).isEqualTo("testFirstName");
        assertThat(entityModelResponseEntity.getBody().getContent().getLastName()).isEqualTo("testLastName");
        assertThat(entityModelResponseEntity.getBody().getContent().getRole()).isEqualTo("testRole");
    }

    @Test
    void replaceEmployee() {
        Employee employee = new Employee("testFirstName", "testLastName", "testRole");

        ResponseEntity<?> responseEntity = employeeController.replaceEmployee(employee, id);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void replaceEmployeeNotExist() {
        Employee employee = new Employee("testFirstName", "testLastName", "testRole");

        ResponseEntity<?> responseEntity = employeeController.replaceEmployee(employee, id + 1);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void deleteEmployee() {
        ResponseEntity<?> responseEntity = employeeController.deleteEmployee(id);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    void deleteEmployeeNotExist() {
        assertThrows(EmptyResultDataAccessException.class, () -> employeeController.deleteEmployee(100L));
    }
}