package ama.crai.demo.configuration;

import ama.crai.demo.entity.Employee;
import ama.crai.demo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadEmployee {

    private static final Logger log = LoggerFactory.getLogger(LoadEmployee.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    List<Employee> loadEmployeeInDB() {
        Employee bilbo = new Employee("Bilbo", "Baggins", "burglar");
        log.info("Preloading " + bilbo);

        Employee frodo = new Employee("Frodo", "Baggins", "thief");
        log.info("Preloading " + frodo);

        bilbo = employeeRepository.save(bilbo);
        frodo = employeeRepository.save(frodo);
        log.info("Saved " + bilbo);
        log.info("Saved " + frodo);

        log.info("Preloading completed");

        return employeeRepository.findAll();
    }

}
