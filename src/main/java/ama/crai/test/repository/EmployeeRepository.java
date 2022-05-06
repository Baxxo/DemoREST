package ama.crai.test.repository;

import ama.crai.test.entity.Employee;
import ama.crai.test.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select o from Order o join Employee e on o.employee.id = e.id where o.employee.id = :id")
    List<Order> findOrdersByUserId(@Param("id") Long userId);
}
