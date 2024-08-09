package com.jmt.restaurant.services;

import com.jmt.restaurant.repositories.FoodMenuImageRepository;
import com.jmt.restaurant.repositories.FoodMenuRepository;
import com.jmt.restaurant.repositories.RestaurantImageRepository;
import com.jmt.restaurant.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Lazy
@Service
@RequiredArgsConstructor
public class DataTransferService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final FoodMenuRepository foodMenuRepository;
    private final FoodMenuImageRepository foodMenuImageRepository;

    private final RestTemplate restTemplate;
    private String serviceKey = "BOYbPA7XwfKbTICZBI6Myilx082zrTanL2VbWfQxwHMa8WA1YwyN2PkhdIeJZkmL";

    /**
     * 식당 기본 정보
     *
     */
    public void update1() {
        String url = String.format("https://seoul.openapi.redtable.global/api/rstr?serviceKey=%s", serviceKey);

        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(url), String.class);
        System.out.println(response);
    }
}
