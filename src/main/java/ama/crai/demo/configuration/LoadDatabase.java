package ama.crai.demo.configuration;

import ama.crai.demo.entity.Employee;
import ama.crai.demo.entity.Order;
import ama.crai.demo.entity.Product;
import ama.crai.demo.entity.Status;
import ama.crai.demo.entity.ordPrd.OrderProduct;
import ama.crai.demo.repository.EmployeeRepository;
import ama.crai.demo.repository.OrderProductRepository;
import ama.crai.demo.repository.OrderRepository;
import ama.crai.demo.repository.ProductRepository;
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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

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

            bilbo = employeeRepository.save(bilbo);
            frodo = employeeRepository.save(frodo);
            log.info("Saved " + bilbo);
            log.info("Saved " + frodo);

            // --------------------------------------------------------------------------

            log.info("Preloading orders");

            Order macBookOrder = new Order("MacBook Pro", Status.COMPLETED, frodo);
            log.info("Preloading " + macBookOrder);

            Order iPhoneOrder = new Order("iPhone", Status.IN_PROGRESS, bilbo);
            log.info("Preloading " + iPhoneOrder);

            macBookOrder = orderRepository.save(macBookOrder);
            iPhoneOrder = orderRepository.save(iPhoneOrder);
            log.info("Saved " + macBookOrder);
            log.info("Saved " + iPhoneOrder);

            // --------------------------------------------------------------------------

//            Product macBookPro = new Product("MacBook Pro", ""
            Product macBookPro = new Product("MacBook Pro", "Apple's latest laptop");
            log.info("Preloading " + macBookPro);

            Product iPhoneX = new Product("iPhone X", "Apple's latest smartphone");
            log.info("Preloading " + iPhoneX);

            macBookPro = productRepository.save(macBookPro);
            iPhoneX = productRepository.save(iPhoneX);
            log.info("Saved " + macBookPro);
            log.info("Saved " + iPhoneX);

            // --------------------------------------------------------------------------

            OrderProduct macBookProOrder = new OrderProduct(macBookOrder, macBookPro, 1);
            log.info("Preloading " + macBookProOrder);

            OrderProduct iPhoneXOrder = new OrderProduct(iPhoneOrder, iPhoneX, 1);
            log.info("Preloading " + iPhoneXOrder);

            macBookProOrder = orderProductRepository.save(macBookProOrder);
            iPhoneXOrder = orderProductRepository.save(iPhoneXOrder);
            log.info("Saved " + macBookProOrder);
            log.info("Saved " + iPhoneXOrder);

            // --------------------------------------------------------------------------

            log.info("Preloading completed");

        };
    }
}
