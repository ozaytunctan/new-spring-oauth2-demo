package com.otunctan.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.otunctan.dtos.OrderResponseDto;
import com.otunctan.enums.OrderStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/orders")
public class OrdersController {
	
	@GetMapping()
	public List<OrderResponseDto> getOrders() {

		OrderResponseDto order1 = new OrderResponseDto(UUID.randomUUID().toString(),
				"product-id-1", "user-id-1", 1, OrderStatus.NEW);

		OrderResponseDto order2 = new OrderResponseDto(UUID.randomUUID().toString(),
				"product-id-2", "user-id-1", 1, OrderStatus.NEW);
		
		return Arrays.asList(order1, order2);
	}

}
