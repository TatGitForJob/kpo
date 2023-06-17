package io.bootify.restaurant.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RestaurantDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    private Double rating;

}
