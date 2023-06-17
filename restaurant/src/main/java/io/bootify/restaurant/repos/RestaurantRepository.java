package io.bootify.restaurant.repos;

import io.bootify.restaurant.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
