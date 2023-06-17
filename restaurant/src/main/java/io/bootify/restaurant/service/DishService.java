package io.bootify.restaurant.service;

import io.bootify.restaurant.domain.Dish;
import io.bootify.restaurant.domain.Restaurant;
import io.bootify.restaurant.model.DishDTO;
import io.bootify.restaurant.repos.DishRepository;
import io.bootify.restaurant.repos.RestaurantRepository;
import io.bootify.restaurant.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(final DishRepository dishRepository,
            final RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<DishDTO> findAll() {
        final List<Dish> dishs = dishRepository.findAll(Sort.by("id"));
        return dishs.stream()
                .map(dish -> mapToDTO(dish, new DishDTO()))
                .toList();
    }

    public DishDTO get(final Long id) {
        return dishRepository.findById(id)
                .map(dish -> mapToDTO(dish, new DishDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DishDTO dishDTO) {
        final Dish dish = new Dish();
        mapToEntity(dishDTO, dish);
        return dishRepository.save(dish).getId();
    }

    public void update(final Long id, final DishDTO dishDTO) {
        final Dish dish = dishRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dishDTO, dish);
        dishRepository.save(dish);
    }

    public void delete(final Long id) {
        dishRepository.deleteById(id);
    }

    private DishDTO mapToDTO(final Dish dish, final DishDTO dishDTO) {
        dishDTO.setId(dish.getId());
        dishDTO.setTitle(dish.getTitle());
        dishDTO.setRestaurant(dish.getRestaurant() == null ? null : dish.getRestaurant().getId());
        return dishDTO;
    }

    private Dish mapToEntity(final DishDTO dishDTO, final Dish dish) {
        dish.setTitle(dishDTO.getTitle());
        final Restaurant restaurant = dishDTO.getRestaurant() == null ? null : restaurantRepository.findById(dishDTO.getRestaurant())
                .orElseThrow(() -> new NotFoundException("restaurant not found"));
        dish.setRestaurant(restaurant);
        return dish;
    }

}
