package ama.crai.test.configuration;

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

    /**
     * Loads the database with some initial data.
     *
     * @return CommandLineRunner
     */
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("Preloading employees");

            Employee bilbo = new Employee("Bilbo", "Baggins", "burglar");
            bilbo.setId(1L);
            log.info("Preloading " + bilbo);
            employeeRepository.save(bilbo);

            Employee frodo = new Employee("Frodo", "Baggins", "thief");
            frodo.setId(2L);
            log.info("Preloading " + frodo);
            employeeRepository.save(frodo);

            employeeRepository.findAll().forEach(employee -> log.info("Preloaded " + employee));

            log.info("Preloading orders");

            Order macBook = new Order("MacBook Pro", Status.COMPLETED);
            macBook.setId(1L);
            log.info("Preloading " + macBook);
            orderRepository.save(macBook);

            Order iPhone = new Order("iPhone", Status.IN_PROGRESS);
            iPhone.setId(2L);
            log.info("Preloading " + iPhone);
            orderRepository.save(iPhone);

            orderRepository.findAll().forEach(order -> log.info("Preloaded " + order));
        };
    }
}
