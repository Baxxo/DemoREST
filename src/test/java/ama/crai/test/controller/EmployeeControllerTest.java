package ama.crai.test.controller;

import ama.crai.test.entity.Employee;
import ama.crai.test.exception.EmployeeNotFoundException;
import ama.crai.test.loader.LoadDatabase;
import org.junit.jupiter.api.AfterEach;
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
    void greeting() {
        String greeting = employeeController.greeting();

        assertEquals("Hello, World", greeting);
    }

    @Test
    void all() {
        Collection<EntityModel<Employee>> entities = employeeController.all().getContent();

        entities.forEach(entity -> {
            Employee employee = entity.getContent();
            assertThat(employee).isNotNull();
        });

        assertThat(entities.isEmpty()).isFalse();
    }

    @Test
    void one() {
        Employee employee = employeeController.one(id).getContent();

        assertThat(employee).isNotNull();
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