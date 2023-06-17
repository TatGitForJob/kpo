package io.bootify.restaurant.service;

import io.bootify.restaurant.domain.Restaurant;
import io.bootify.restaurant.model.RestaurantDTO;
import io.bootify.restaurant.repos.RestaurantRepository;
import io.bootify.restaurant.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(final RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantDTO> findAll() {
        final List<Restaurant> restaurants = restaurantRepository.findAll(Sort.by("id"));
        return restaurants.stream()
                .map(restaurant -> mapToDTO(restaurant, new RestaurantDTO()))
                .toList();
    }

    public RestaurantDTO get(final Long id) {
        return restaurantRepository.findById(id)
                .map(restaurant -> mapToDTO(restaurant, new RestaurantDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RestaurantDTO restaurantDTO) {
        final Restaurant restaurant = new Restaurant();
        mapToEntity(restaurantDTO, restaurant);
        return restaurantRepository.save(restaurant).getId();
    }

    public void update(final Long id, final RestaurantDTO restaurantDTO) {
        final Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(restaurantDTO, restaurant);
        restaurantRepository.save(restaurant);
    }

    public void delete(final Long id) {
        restaurantRepository.deleteById(id);
    }

    private RestaurantDTO mapToDTO(final Restaurant restaurant, final RestaurantDTO restaurantDTO) {
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setAddress(restaurant.getAddress());
        restaurantDTO.setRating(restaurant.getRating());
        return restaurantDTO;
    }

    private Restaurant mapToEntity(final RestaurantDTO restaurantDTO, final Restaurant restaurant) {
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setRating(restaurantDTO.getRating());
        return restaurant;
    }

}
