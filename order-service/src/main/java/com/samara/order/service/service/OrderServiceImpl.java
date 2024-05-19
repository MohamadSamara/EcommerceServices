package com.samara.order.service.service;

import com.samara.order.service.bo.order.OrderResponse;
import com.samara.order.service.mapper.Mapper;
import com.samara.order.service.model.OrderEntity;
import com.samara.order.service.model.OrderItem;
import com.samara.order.service.repository.OrderItemRepository;
import com.samara.order.service.repository.OrderRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


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


    @Override
    public OrderResponse order(Long orderId) {

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order Not Found With ID = " + orderId));

        List<OrderItem> orderItem = orderItemRepository.findAllByOrderId(orderId);
        order.setOrderItem(orderItem);

        return mapper.orderEntityToOrderResponse(order);
    }

    @Override
    public OrderResponse orderForUser(Long userId) {
        OrderEntity orderForUser = orderRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Order Not Found With UserId = " + userId));

        List<OrderItem> orderItem = orderItemRepository.findAllByOrderId(orderForUser.getId());

        orderForUser.setOrderItem(orderItem);

        return mapper.orderEntityToOrderResponse(orderForUser);
    }

    @Override
    public String deleteOrderForUser(Long userId) {

        OrderEntity userOrder = orderRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Order Not Found With UserId = " + userId));

        orderRepository.delete(userOrder);

        return "User Order Deleted Successfully";
    }


}