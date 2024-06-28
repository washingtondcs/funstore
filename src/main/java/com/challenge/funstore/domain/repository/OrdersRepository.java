package com.challenge.funstore.domain.repository;

import com.challenge.funstore.domain.dto.CustomerOrderResponseDTO;
import com.challenge.funstore.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT o FROM Orders o JOIN o.customer c WHERE c.name LIKE %:name% OR c.cpf LIKE %:cpf%")
    List<Orders> findByCustomerNameOrCpf(@Param("name") String name, @Param("cpf") String cpf);
}
