package ama.crai.test.exception;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JUnit4.class)
class EmployeeNotFoundExceptionTest {

    @Test
    void testConstructor() {
        EmployeeNotFoundException exception = new EmployeeNotFoundException(1L);
        assertEquals("Could not find employee 1", exception.getMessage());
    }

    @Test
    void testConstructorWithNullValue() {
        EmployeeNotFoundException exception = new EmployeeNotFoundException(null);
        assertEquals("Could not find employee null", exception.getMessage());
    }

    @Test
    void testThrowException() {
        assertThrows(EmployeeNotFoundException.class, () -> {
            throw new EmployeeNotFoundException(1L);
        });
    }

}