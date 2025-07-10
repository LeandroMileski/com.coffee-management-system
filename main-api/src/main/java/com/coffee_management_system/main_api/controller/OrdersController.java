package com.coffee_management_system.main_api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {

    /**
     * GET /api/orders - Get all orders for the authenticated user
     * This endpoint requires JWT authentication
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        // Get the authenticated user's information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Getting orders for user: {}", username);

        // Mock orders data (in a real application, this would come from a database)
        List<Map<String, Object>> orders = new ArrayList<>();

        Map<String, Object> order1 = new HashMap<>();
        order1.put("id", 1);
        order1.put("customerName", username);
        order1.put("items", List.of("Espresso", "Croissant"));
        order1.put("total", 8.50);
        order1.put("status", "completed");
        order1.put("orderDate", LocalDateTime.now().minusHours(2));

        Map<String, Object> order2 = new HashMap<>();
        order2.put("id", 2);
        order2.put("customerName", username);
        order2.put("items", List.of("Cappuccino", "Blueberry Muffin"));
        order2.put("total", 12.75);
        order2.put("status", "pending");
        order2.put("orderDate", LocalDateTime.now().minusMinutes(30));

        orders.add(order1);
        orders.add(order2);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Orders retrieved successfully");
        response.put("user", username);
        response.put("orders", orders);
        response.put("count", orders.size());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/orders/{id} - Get a specific order by ID
     * This endpoint requires JWT authentication
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getOrderById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Getting order {} for user: {}", id, username);

        // Mock single order data
        Map<String, Object> order = new HashMap<>();
        order.put("id", id);
        order.put("customerName", username);
        order.put("items", List.of("Latte", "Chocolate Chip Cookie"));
        order.put("total", 9.25);
        order.put("status", "completed");
        order.put("orderDate", LocalDateTime.now().minusHours(1));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order retrieved successfully");
        response.put("user", username);
        response.put("order", order);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/orders - Create a new order
     * This endpoint requires JWT authentication
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Creating order for user: {}", username);
        log.info("Order request: {}", orderRequest);

        // Mock order creation
        Map<String, Object> newOrder = new HashMap<>();
        newOrder.put("id", 3);
        newOrder.put("customerName", username);
        newOrder.put("items", orderRequest.get("items"));
        newOrder.put("total", orderRequest.get("total"));
        newOrder.put("status", "pending");
        newOrder.put("orderDate", LocalDateTime.now());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Order created successfully");
        response.put("user", username);
        response.put("order", newOrder);
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/orders/user-info - Get authenticated user information
     * This is a simple endpoint to test JWT authentication
     */
    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        log.info("Getting user info for: {}", username);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User info retrieved successfully");
        response.put("username", username);
        response.put("authorities", authentication.getAuthorities());
        response.put("authenticated", authentication.isAuthenticated());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}