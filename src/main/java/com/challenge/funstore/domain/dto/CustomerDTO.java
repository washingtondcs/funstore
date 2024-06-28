package com.challenge.funstore.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public CustomerDTO() {
    }

    public CustomerDTO(Integer id, String name, String email, String cpf, LocalDateTime dateCreated, LocalDateTime dateUpdated) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }
}
