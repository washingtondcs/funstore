package com.challenge.funstore.api.service;

import com.challenge.funstore.domain.dto.CustomerOrderResponseDTO;
import com.challenge.funstore.domain.dto.OrderResponseDTO;
import com.challenge.funstore.domain.dto.OrdersDTO;
import com.challenge.funstore.domain.dto.ProductDTO;
import com.challenge.funstore.domain.entity.Customer;
import com.challenge.funstore.domain.entity.OrderItems;
import com.challenge.funstore.domain.entity.Orders;
import com.challenge.funstore.domain.entity.Product;
import com.challenge.funstore.domain.enums.OrderTypeEnums;
import com.challenge.funstore.domain.repository.CustomerRepository;
import com.challenge.funstore.domain.repository.OrdersRepository;
import com.challenge.funstore.domain.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public OrdersService(OrdersRepository ordersRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrdersDTO ordersDTO) {
        Orders order = new Orders();
        order.setCustomer(customerRepository.findById(ordersDTO.getCustomer()).orElseThrow());
        order.setType(ordersDTO.getType());
        order.setRentalDays(ordersDTO.getRentalDays());
        order.setOrderItems(new ArrayList<>());
        BigDecimal totalValue = BigDecimal.ZERO;

        for (Integer productId : ordersDTO.getOrderItems()) {
            Product product = productRepository.findById(productId).orElseThrow();

            BigDecimal productValue = BigDecimal.ZERO;
            if (order.getType() == 1) {
                productValue = product.getSaleValue();
            } else if (order.getType() == 2) {
                productValue = product.getRentalValue().multiply(BigDecimal.valueOf(order.getRentalDays()));
            }

            totalValue = totalValue.add(productValue);

            OrderItems orderItem = new OrderItems();
            orderItem.setOrders(order);
            orderItem.setProduct(product);
            order.getOrderItems().add(orderItem);
        }

        Optional<Customer> customer = customerRepository.findById(order.getCustomer().getId());
        order.setTotalValue(totalValue);
        setOrderTypeName(order);

        order.setDateCreated(LocalDateTime.now());
        order.setDateUpdated(LocalDateTime.now());
        order = ordersRepository.save(order);

        return new OrderResponseDTO(order.getId(), order.getTotalValue(), order.getDateCreated(),
                order.getDateUpdated(), customer.get().getName(), customer.get().getCpf(), order.getOrderItems());
    }

    public Orders getOrderById(Integer id) {
        return ordersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));
    }

    public Orders updateOrder(Integer id, Orders orderDetails) {
        Orders order = ordersRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));

        order.setCustomer(orderDetails.getCustomer());
        order.setType(orderDetails.getType());
        order.setRentalDays(orderDetails.getRentalDays());
        return ordersRepository.save(order);
    }

    public Optional<Orders> deleteOrder(Integer id) {
        Optional<Orders> orders = ordersRepository.findById(id);
        if (orders.isPresent()) {
            Orders o = orders.get();
            o.setDeleted(true);
            ordersRepository.save(o);
            return Optional.of(o);
        } else {
            return Optional.empty();
        }
    }

    public List<CustomerOrderResponseDTO> findOrdersByCustomerNameOrCpf(String name, String cpf) {
        List<Orders> orders = ordersRepository.findByCustomerNameOrCpf(name, cpf);

        return orders.stream().map(order -> {
            List<ProductDTO> productDTOS = order.getOrderItems().stream()
                    .map(item -> new ProductDTO(
                                    item.getProduct().getDescription(),
                                    item.getProduct().getProductType(),
                                    item.getProduct().getProductTypeName(),
                                    item.getProduct().getRentalValue(),
                                    item.getProduct().getSaleValue()
                            )
                    )
                    .collect(Collectors.toList());

            return new CustomerOrderResponseDTO(
                    order.getId(),
                    order.getTypeName(),
                    order.getCustomer().getName(),
                    order.getCustomer().getCpf(),
                    order.getCustomer().getEmail(),
                    order.getTotalValue(),
                    order.getDateCreated(),
                    productDTOS
            );
        }).collect(Collectors.toList());
    }

    private void setOrderTypeName(Orders orders) {
        if (orders.getType() != null) {
            OrderTypeEnums.OrderType orderType = OrderTypeEnums.OrderType.findByCode(orders.getType());
            orders.setTypeName(orderType.getDescription());
        }
    }
}
