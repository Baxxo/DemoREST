package ama.crai.test;

import ama.crai.test.controller.EmployeeController;
import ama.crai.test.entity.Employee;
import ama.crai.test.loader.LoadDatabase;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeControllerTest {

    @Autowired
    private EmployeeController employeeController;

    private boolean initialized = false;

    private Long id = 1L;
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    /**
     * this method is used to initialize {@link EmployeeControllerTest#id}
     */
    @PostConstruct
    public void setUp() {
        if (!initialized) {
            Employee employee = new Employee("testFirstName", "testLastName", "testRole");

            ResponseEntity<EntityModel<Employee>> entityModelResponseEntity = employeeController.newEmployee(employee);

            id = Objects.requireNonNull(Objects.requireNonNull(entityModelResponseEntity.getBody()).getContent()).getId();

            log.info("Preloading " + id);
        }
        initialized = true;
    }

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
        Employee employee = employeeController.one(id).getContent();

        assertThat(employee).isNotNull();
    }

    @Test
    void testInsertNewEmployee() {
        Employee employee = new Employee("testFirstName", "testLastName", "testRole");

        ResponseEntity<EntityModel<Employee>> entityModelResponseEntity = employeeController.newEmployee(employee);

        assertThat(entityModelResponseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void testReplaceEmployee() {
        Employee employee = new Employee("testFirstName", "testLastName", "testRole");

        ResponseEntity<?> responseEntity = employeeController.replaceEmployee(employee, id);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

    @Test
    void testDeleteEmployee() {
        ResponseEntity<?> responseEntity = employeeController.deleteEmployee(id);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
    }
}
