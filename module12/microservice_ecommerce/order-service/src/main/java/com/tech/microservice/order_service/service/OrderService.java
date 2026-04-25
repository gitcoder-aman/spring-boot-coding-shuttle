package com.tech.microservice.order_service.service;

import com.tech.microservice.order_service.dto.OrderRequestDto;
import com.tech.microservice.order_service.entity.Orders;
import com.tech.microservice.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderRepository ordersRepository;
    private final ModelMapper modelMapper;

    public List<OrderRequestDto> getAllOrders(){
        log.info("fetching all orders");
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream()
                .map(order-> modelMapper.map(order, OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long id){
        log.info("Fetching order with id: {}",id);
        Orders orders = ordersRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
        return modelMapper.map(orders, OrderRequestDto.class);
    }
}
