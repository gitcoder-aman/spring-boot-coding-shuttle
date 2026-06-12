package com.tech.microservice.inventory_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "order-service",path = "/orders")  //make sure same name with order service
public interface OrdersFeignClient {

    @GetMapping("/core/helloOrder")
    String helloOrders();
}
