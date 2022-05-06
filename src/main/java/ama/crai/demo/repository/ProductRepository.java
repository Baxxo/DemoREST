package ama.crai.demo.repository;

import ama.crai.demo.entity.Order;
import ama.crai.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
