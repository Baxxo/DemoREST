package ama.crai.demo.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public static final String MESSAGE = "Could not find employee ";

    public EmployeeNotFoundException(Long id) {
        super(MESSAGE + id);
    }
}
