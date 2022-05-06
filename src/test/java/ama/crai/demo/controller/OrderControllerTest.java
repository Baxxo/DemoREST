package ama.crai.demo.controller;

import ama.crai.demo.configuration.LoadDatabase;
import ama.crai.demo.entity.Employee;
import ama.crai.demo.entity.Order;
import ama.crai.demo.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    private Long id = 1L;

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @BeforeEach
    void setUp() {
        Employee employee = new Employee("testName", "testSurname", "testRole");

        Order order = new Order("testDescription", Status.IN_PROGRESS, employee);

        ResponseEntity<EntityModel<Order>> entityModelResponseEntity = orderController.newOrder(order);

        id = Objects.requireNonNull(Objects.requireNonNull(entityModelResponseEntity.getBody()).getContent()).getId();
    }

    @Test
    void all() {
        Collection<EntityModel<Order>> all = orderController.all().getContent();
        assertThat(all.isEmpty()).isFalse();

        all.forEach(order -> {
            Order o = order.getContent();
            assertThat(o).isNotNull();
        });
    }

    @Test
    void one() {
        EntityModel<Order> order = orderController.one(id);
        assertThat(order).isNotNull();
        assertThat(order.getContent()).isNotNull();
        assertThat(order.getContent().getDescription()).isEqualTo("testDescription");
    }

    @Test
    void newOrder() {
        Employee employee = new Employee("testName1", "testSurname1", "testRole1");
        Order order = new Order("testDescription2", Status.COMPLETED, employee);

        ResponseEntity<EntityModel<Order>> entityModelResponseEntity = orderController.newOrder(order);

        assertThat(entityModelResponseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(entityModelResponseEntity.getBody()).isNotNull();
        assertThat(entityModelResponseEntity.getBody().getContent()).isNotNull();
        assertThat(entityModelResponseEntity.getBody().getContent().getDescription()).isEqualTo("testDescription2");
        assertThat(entityModelResponseEntity.getBody().getContent().getStatus()).isEqualTo(Status.IN_PROGRESS);
    }

    @Test
    void cancel() {
        ResponseEntity<?> cancel = orderController.cancel(id);

        assertThat(cancel.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void cancelOrderNotInProgress() {
        orderController.cancel(id);
        ResponseEntity<?> cancel = orderController.cancel(id);
        assertThat(cancel.getStatusCodeValue()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
        assertThat(cancel.getBody()).isNotNull();
        assertThat(cancel.getBody().toString()).contains("Cannot cancel an order that is in CANCELLED status");
    }

    @Test
    void cancerCompletedOrder() {
        orderController.complete(id);
        ResponseEntity<?> cancel = orderController.cancel(id);
        assertThat(cancel.getStatusCodeValue()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
        assertThat(cancel.getBody()).isNotNull();
        assertThat(cancel.getBody().toString()).contains("Cannot cancel an order that is in COMPLETED status");
    }

    @Test
    void complete() {
        ResponseEntity<?> complete = orderController.complete(id);

        assertThat(complete.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void completeOrderNotInProgress() {
        orderController.complete(id);
        ResponseEntity<?> complete = orderController.complete(id);
        assertThat(complete.getStatusCodeValue()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
        assertThat(complete.getBody()).isNotNull();
        assertThat(complete.getBody().toString()).contains("Cannot complete an order that is in COMPLETED status");

    }

    @Test
    void completeCanceledOrder() {
        orderController.cancel(id);
        ResponseEntity<?> complete = orderController.complete(id);
        assertThat(complete.getStatusCodeValue()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
        assertThat(complete.getBody()).isNotNull();
        assertThat(complete.getBody().toString()).contains("Cannot complete an order that is in CANCELLED status");
    }
}