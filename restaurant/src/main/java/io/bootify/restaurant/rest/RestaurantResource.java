package io.bootify.restaurant.rest;

import io.bootify.restaurant.model.RestaurantDTO;
import io.bootify.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantResource {

    private final RestaurantService restaurantService;

    public RestaurantResource(final RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(restaurantService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createRestaurant(
            @RequestBody @Valid final RestaurantDTO restaurantDTO) {
        final Long createdId = restaurantService.create(restaurantDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRestaurant(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final RestaurantDTO restaurantDTO) {
        restaurantService.update(id, restaurantDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable(name = "id") final Long id) {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
