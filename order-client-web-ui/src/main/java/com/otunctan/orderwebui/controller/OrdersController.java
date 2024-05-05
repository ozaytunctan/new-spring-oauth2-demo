package com.otunctan.orderwebui.controller;


import com.otunctan.orderwebui.dtos.OrderResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class OrdersController {


    private final RestTemplate restTemplate;

    @Value("${order.api.all-order.service.url}")
    private String ordersServiceUrl;

    public OrdersController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/orders")
    public String getOrders(Model model,
                            @RegisteredOAuth2AuthorizedClient("order-client-oidc") OAuth2AuthorizedClient authorizedClient) {


        String jwtAccessToken = authorizedClient.getAccessToken().getTokenValue();
        System.out.println("jwtAccessToken =  " + jwtAccessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtAccessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<OrderResponseDto>> responseEntity =
                restTemplate.exchange(ordersServiceUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<List<OrderResponseDto>>() {
                });

        List<OrderResponseDto> orders = responseEntity.getBody();

        model.addAttribute("orders", orders);

        return "orders-page";

    }
}
