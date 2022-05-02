package ama.crai.test.loader;

import ama.crai.test.entity.Employee;
import ama.crai.test.entity.Order;
import ama.crai.test.entity.Status;
import ama.crai.test.repository.EmployeeRepository;
import ama.crai.test.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            Employee bilbo = new Employee("Bilbo", "Baggins", "burglar");
            log.info("Preloading " + bilbo);
            employeeRepository.save(bilbo);

            Employee frodo = new Employee("Frodo", "Baggins", "thief");
            log.info("Preloading " + frodo);
            employeeRepository.save(frodo);

            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> log.info("Preloaded " + order));
        };
    }
}
