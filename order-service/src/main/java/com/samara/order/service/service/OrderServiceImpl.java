package com.samara.order.service.service;

import com.samara.order.service.bo.order.OrderResponse;
import com.samara.order.service.mapper.Mapper;
import com.samara.order.service.model.OrderEntity;
import com.samara.order.service.model.OrderItem;
import com.samara.order.service.repository.OrderItemRepository;
import com.samara.order.service.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final Mapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, Mapper mapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public OrderResponse createOrder(Long userId, Long cartId) {
        clearOrderIfExisting(userId);
        OrderEntity order = createNewOrder(userId);
        List<OrderItem> orderItems = mapper.getOrderItemsFromCartItems(userId, order);

        order.setModifiedAt(LocalDateTime.now());
        order.setOrderItem(orderItems);
        order.setTotalPrice(orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum());

        log.debug("Saving order: {}", order);

        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        return mapper.orderEntityToOrderResponse(order);
    }

    private OrderEntity createNewOrder(Long userId) {
        OrderEntity newOrder = OrderEntity.builder()
                .userId(userId)
                .orderItem(new ArrayList<>())
                .totalPrice(0.0)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        log.debug("Creating new order for userId: {}", userId);
        return orderRepository.save(newOrder);
    }

    @Override
    public String clearOrderIfExisting(Long userId) {
        Optional<OrderEntity> existingOrder = orderRepository.findByUserId(userId);

        existingOrder.ifPresent(order -> {
            log.debug("Deleting existing order: {}", order);
            orderItemRepository.deleteAll(order.getOrderItem());
            orderRepository.delete(order);
        });

        return "User Order Deleted Successfully";
    }


    @Override
    public OrderResponse order(Long userId) {

        OrderEntity order = orderRepository.findByUserId(userId)
                .orElseGet(() -> createNewOrder(userId));

        List<OrderItem> orderItem = orderItemRepository.findAllByOrderId(order.getId());
        order.setOrderItem(orderItem);

        return mapper.orderEntityToOrderResponse(order);
    }

}