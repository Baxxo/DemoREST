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
            log.info("Preloading " + bilbo);

            Employee frodo = new Employee("Frodo", "Baggins", "thief");
            log.info("Preloading " + frodo);

            // --------------------------------------------------------------------------

            log.info("Preloading orders");

            Order macBook = new Order("MacBook Pro", Status.COMPLETED, frodo);
            log.info("Preloading " + macBook);

            Order iPhone = new Order("iPhone", Status.IN_PROGRESS, bilbo);
            log.info("Preloading " + iPhone);

            // --------------------------------------------------------------------------

            Employee save = employeeRepository.save(bilbo);
            Employee save1 = employeeRepository.save(frodo);
            log.info("Saved " + save);
            log.info("Saved " + save1);

        };
    }
}
