package io.bootify.restaurant.repos;

import io.bootify.restaurant.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
