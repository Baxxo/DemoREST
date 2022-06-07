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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Autowired
    private LoadEmployee loadEmployee;

    @Autowired
    private LoadOrder loadOrder;

    @Autowired
    private LoadProduct loadProduct;

    @Autowired
    private LoadPrdOrd loadPrdOrd;

    /**
     * Loads the database with some initial data.
     *
     * @return CommandLineRunner
     */
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("Preloading Database");

            List<Employee> employees = loadEmployee.loadEmployeeInDB();

            log.info("Preloaded " + employees.size() + " employees");

            List<Product> products = loadProduct.loadProductInDB();

            log.info("Preloaded " + products.size() + " products");

            List<Order> orders = loadOrder.loadOrderInDB(employees);

            log.info("Preloaded " + orders.size() + " orders");

            List<OrderProduct> orderProducts = loadPrdOrd.loadPrdOrdInDB(products, orders);

            log.info("Preloaded " + orderProducts.size() + " order products");

            log.info("Preloading completed");
        };
    }
}
