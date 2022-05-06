package ama.crai.demo.controller;

import ama.crai.demo.assembler.OrderModelAssembler;
import ama.crai.demo.entity.Order;
import ama.crai.demo.entity.Product;
import ama.crai.demo.entity.Status;
import ama.crai.demo.entity.ordPrd.OrderProduct;
import ama.crai.demo.exception.OrderNotFoundException;
import ama.crai.demo.repository.OrderRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderModelAssembler orderModelAssembler;

    public OrderController(OrderRepository orderRepository, OrderModelAssembler assembler) {
        this.orderRepository = orderRepository;
        this.orderModelAssembler = assembler;
    }

    @GetMapping()
    public CollectionModel<EntityModel<Order>> all() {

        List<EntityModel<Order>> orders = orderRepository.findAll().stream() //
                .map(orderModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(orders, //
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @RequestMapping(
            params = "id",
            method = RequestMethod.GET
    )
    public @ResponseBody EntityModel<Order> one(@RequestParam("id") Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        return orderModelAssembler.toModel(order);
    }

    @PostMapping()
    ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

        order.setStatus(Status.IN_PROGRESS);
        Order newOrder = orderRepository.save(order);

        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
                .body(orderModelAssembler.toModel(newOrder));
    }

    @RequestMapping(
            value = "/cancel",
            params = "id",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<?> cancel(@RequestParam("id") Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(orderModelAssembler.toModel(orderRepository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("Cannot cancel an order that is in " + order.getStatus() + " status")
                );
    }

    @RequestMapping(
            value = "/complete",
            params = "id",
            method = RequestMethod.PUT
    )
    public ResponseEntity<?> complete(@RequestParam("id") Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(orderModelAssembler.toModel(orderRepository.save(order)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("Cannot complete an order that is in " + order.getStatus() + " status")
                );
    }

    @RequestMapping(
            value = "/list_products",
            params = "id",
            method = RequestMethod.GET
    )
    public @ResponseBody CollectionModel<Product> listProducts(@RequestParam("id") Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        List<OrderProduct> productsList = order.getProductsList();

        List<Product> products = new ArrayList<>();
        for (OrderProduct orderProduct : productsList) {
            products.add(orderProduct.getProduct());
        }

        return CollectionModel.of(products, //
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }
}
