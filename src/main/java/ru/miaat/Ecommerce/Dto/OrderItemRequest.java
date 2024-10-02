package ru.miaat.Ecommerce.Dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private int productId;
    private int quantity;

}
