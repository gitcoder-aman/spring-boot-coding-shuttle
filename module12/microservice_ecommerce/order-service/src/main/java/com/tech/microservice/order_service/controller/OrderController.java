package com.tech.microservice.order_service.controller;

import com.tech.microservice.order_service.dto.OrderRequestDto;
import com.tech.microservice.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/core")
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/helloOrder")
    public String helloOrder(){
        return "Hello from Order Service";
    }
    @GetMapping
    public ResponseEntity<List<OrderRequestDto>>getAllOrders(){
        log.info("Fetching all order via controller");
        List<OrderRequestDto>orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id){
        log.info("Fetching Order with id:{} via controller",id);
        OrderRequestDto orderRequestDto = orderService.getOrderById(id);
        return ResponseEntity.ok(orderRequestDto);
    }
}
