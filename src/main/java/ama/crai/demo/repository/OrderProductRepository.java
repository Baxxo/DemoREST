package ama.crai.demo.repository;

import ama.crai.demo.entity.Order;
import ama.crai.demo.entity.ordPrd.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
