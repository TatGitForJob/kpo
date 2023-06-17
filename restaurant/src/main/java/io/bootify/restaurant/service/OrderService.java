package io.bootify.restaurant.service;

import io.bootify.restaurant.domain.Dish;
import io.bootify.restaurant.domain.Order;
import io.bootify.restaurant.model.OrderDTO;
import io.bootify.restaurant.repos.DishRepository;
import io.bootify.restaurant.repos.OrderRepository;
import io.bootify.restaurant.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    public OrderService(final OrderRepository orderRepository,
            final DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setAmount(order.getAmount());
        orderDTO.setDish(order.getDish() == null ? null : order.getDish().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setAmount(orderDTO.getAmount());
        final Dish dish = orderDTO.getDish() == null ? null : dishRepository.findById(orderDTO.getDish())
                .orElseThrow(() -> new NotFoundException("dish not found"));
        order.setDish(dish);
        return order;
    }

}
