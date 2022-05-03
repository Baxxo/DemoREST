package ama.crai.test;

import ama.crai.test.controller.EmployeeController;
import ama.crai.test.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    @Test
    void contextLoads() {
        assertThat(employeeController).isNotNull();
    }

    @Test
    void testGetEmployee() {
        Collection<EntityModel<Employee>> entities = employeeController.all().getContent();

        entities.forEach(entity -> {
            Employee employee = entity.getContent();
            assertThat(employee).isNotNull();
        });

        assertThat(entities.isEmpty()).isFalse();
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = employeeController.one(1L).getContent();

        assertThat(employee).isNotNull();
    }

    @Test
    void testInsertNewEmployee() {
        Employee employee = new Employee("testFirstName", "testLastName", "testRole");

        ResponseEntity<EntityModel<Employee>> entityModelResponseEntity = employeeController.newEmployee(employee);

        assertThat(entityModelResponseEntity.getStatusCodeValue()).isEqualTo(201);
    }
}
