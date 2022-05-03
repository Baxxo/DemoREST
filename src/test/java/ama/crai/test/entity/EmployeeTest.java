package ama.crai.test.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnit4.class)
class EmployeeTest {

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("John", "Doe", "Manager");
        employee.setId(1L);
    }

    @Test
    void getId() {
        assertEquals(1L, employee.getId());
    }

    @Test
    void setId() {
        employee.setId(2L);
        assertEquals(2L, employee.getId());
    }

    @Test
    void getFirstName() {
        assertEquals("John", employee.getFirstName());
    }

    @Test
    void setFirstName() {
        employee.setFirstName("Jane");
        assertEquals("Jane", employee.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", employee.getLastName());
    }

    @Test
    void setLastName() {
        employee.setLastName("Smith");
        assertEquals("Smith", employee.getLastName());
    }

    @Test
    void getRole() {
        assertEquals("Manager", employee.getRole());
    }

    @Test
    void setRole() {
        employee.setRole("Developer");
        assertEquals("Developer", employee.getRole());
    }

    @Test
    void testEquals() {
        Employee employee2 = new Employee("John", "Doe", "Manager");
        employee2.setId(1L);
        assertEquals(employee, employee2);
    }

    @Test
    void testHashCode() {
        Employee employee2 = new Employee("John", "Doe", "Manager");
        employee2.setId(1L);
        assertTrue(employee.equals(employee2) && employee2.equals(employee));
        assertEquals(employee.hashCode(), employee2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Employee{id=1, firstName='John', lastName='Doe', role='Manager'}", employee.toString());
    }
}