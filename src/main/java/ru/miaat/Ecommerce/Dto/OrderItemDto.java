package ru.miaat.Ecommerce.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import ru.miaat.Ecommerce.Enum.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private int quantity;
    private BigDecimal price;
    private OrderStatus status;
    private UserDto user;
    private ProductDto product;
    private OrderDto order;
    private LocalDateTime createdAt;
}
