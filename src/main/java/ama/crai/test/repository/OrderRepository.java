package ama.crai.test.repository;

import ama.crai.test.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query("SELECT o FROM Order o WHERE o.user_id = :id")
//    List<Order> findByUserId(@Param("id") Long id);
}
