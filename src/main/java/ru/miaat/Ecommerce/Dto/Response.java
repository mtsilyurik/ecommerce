package ru.miaat.Ecommerce.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int status;
    private String message;
    private final LocalDateTime timestamp = LocalDateTime.now();

    private String token;
    private String role;
    private String expiration;

    private int totalPage;
    private long totalElements;

    private AddressDto address;
    private List<AddressDto> addressList;

    private  ProductDto product;
    private List<ProductDto> products;

    private UserDto user;
    private List<UserDto> userList;

    private CategoryDto category;
    private List<CategoryDto> categoryList;

    private OrderDto order;
    private List<OrderDto> orderList;

    private OrderItemDto orderItem;
    private List<OrderItemDto> orderItemList;

}
