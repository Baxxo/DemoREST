package ama.crai.demo.configuration;

import ama.crai.demo.entity.Employee;
import ama.crai.demo.entity.Order;
import ama.crai.demo.entity.Status;
import ama.crai.demo.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadOrder {

    private static final Logger log = LoggerFactory.getLogger(LoadOrder.class);
    @Autowired
    private OrderRepository orderRepository;

    List<Order> loadOrderInDB(List<Employee> employees) {

        log.info("Preloading orders");

        Order macBookOrder = new Order("MacBook Pro", Status.COMPLETED, employees.get(0));
        log.info("Preloading " + macBookOrder);

        Order iPhoneOrder = new Order("iPhone", Status.IN_PROGRESS, employees.get(1));
        log.info("Preloading " + iPhoneOrder);

        macBookOrder = orderRepository.save(macBookOrder);
        iPhoneOrder = orderRepository.save(iPhoneOrder);
        log.info("Saved " + macBookOrder);
        log.info("Saved " + iPhoneOrder);

        return orderRepository.findAll();
    }
}
