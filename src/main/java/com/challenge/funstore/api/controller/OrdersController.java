package com.challenge.funstore.api.controller;

import com.challenge.funstore.api.service.OrdersService;
import com.challenge.funstore.domain.dto.CustomerOrderResponseDTO;
import com.challenge.funstore.domain.dto.OrderResponseDTO;
import com.challenge.funstore.domain.dto.OrdersDTO;
import com.challenge.funstore.domain.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrdersDTO ordersDTO) {
        OrderResponseDTO responseDTO = ordersService.createOrder(ordersDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Integer id) {
        Orders order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerOrderResponseDTO>> findOrdersByCustomerNameOrCpf(@RequestParam(required = false) String name, @RequestParam(required = false) String cpf) {
        List<CustomerOrderResponseDTO> orders = ordersService.findOrdersByCustomerNameOrCpf(name == null ? "" : name, cpf == null ? "" : cpf);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Integer id, @RequestBody Orders orderDetails) {
        Orders updatedOrder = ordersService.updateOrder(id, orderDetails);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        Optional<Orders> orders = ordersService.deleteOrder(id);
        if (orders.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Order successfully deleted");
            response.put("product", orders.get());
            return ResponseEntity.ok().body(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Order not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}