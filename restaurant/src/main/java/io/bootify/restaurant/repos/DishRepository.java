package io.bootify.restaurant.repos;

import io.bootify.restaurant.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DishRepository extends JpaRepository<Dish, Long> {
}
