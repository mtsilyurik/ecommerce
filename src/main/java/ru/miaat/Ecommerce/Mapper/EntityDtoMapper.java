package ru.miaat.Ecommerce.Mapper;

import org.springframework.stereotype.Component;
import ru.miaat.Ecommerce.Dto.*;
import ru.miaat.Ecommerce.Entity.*;

@Component
public class EntityDtoMapper {

    public UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole().name());
        userDto.setCreatedAt(user.getCreatedAt());

        return userDto;
    }

    public AddressDto mapAddressToAddressDto(Address a) {
        AddressDto addressDto = new AddressDto();

        addressDto.setId(a.getId());
        addressDto.setStreet(a.getStreet());
        addressDto.setCity(a.getCity());
        addressDto.setState(a.getState());
        addressDto.setCountry(a.getCountry());
        addressDto.setZip(a.getZip());

        addressDto.setUser(mapUserToUserDto(a.getUser()));
        addressDto.setCreatedAt(a.getCreatedAt());
        return addressDto;
    }

    public CategoryDto mapCategoryToCategoryDto(Category c) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(c.getId());
        categoryDto.setName(c.getName());
        return categoryDto;
    }

    public OrderItemDto mapOrderItemToOrderItemDto(OrderItem o) {
        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setId(o.getId());
        orderItemDto.setQuantity(o.getQuantity());
        orderItemDto.setPrice(o.getPrice());
        orderItemDto.setStatus(o.getStatus());
        orderItemDto.setCreatedAt(o.getCreatedAt());
        return orderItemDto;
    }

    public ProductDto mapProductToProductDto(Product p) {
        ProductDto productDto = new ProductDto();

        productDto.setId(p.getId());
        productDto.setName(p.getName());
        productDto.setDescription(p.getDescription());
        productDto.setPrice(p.getPrice());
        productDto.setImageUrl(p.getImageUrl());

        return productDto;
    }

    public OrderDto mapOrderToOrderDto(Order o) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(o.getId());
        orderDto.setTotalPrice(o.getTotalPrice());
        orderDto.setCreatedAt(o.getCreatedAt());
        orderDto.setOrderItemList(o.getOrderItemList().stream()
                .map(this::mapOrderItemToOrderItemDto)
                .toList()
        );

        return orderDto;
    }

    public UserDto mapUserToUserDtoWithAddress(User u){
        UserDto userDto = mapUserToUserDto(u);
        if(u.getAddress() != null){
            userDto.setAddress(mapAddressToAddressDto(u.getAddress()));
        }
        return userDto;
    }

    public OrderItemDto mapOrderItemToOrderItemDtoWithProduct(OrderItem o){
        OrderItemDto orderItemDto = mapOrderItemToOrderItemDto(o);
        if(o.getProduct() != null){
            orderItemDto.setProduct(mapProductToProductDto(o.getProduct()));
        }
        return orderItemDto;
    }

    public OrderItemDto mapOrderItemToOrderItemDtoWithProductAndUser(OrderItem o){
        OrderItemDto orderItemDto = mapOrderItemToOrderItemDtoWithProduct(o);
        if(o.getUser() != null){
            orderItemDto.setUser(mapUserToUserDtoWithAddress(o.getUser()));
        }
        return orderItemDto;
    }

    public UserDto mapUserToUserDtoWithAddressAndOrders(User u){
        UserDto userDto = mapUserToUserDtoWithAddress(u);

        if(u.getOrderItemList() != null && !u.getOrderItemList().isEmpty()){
            userDto.setOrderItemDtoList(u.getOrderItemList().stream()
                    .map(this::mapOrderItemToOrderItemDtoWithProduct)
                    .toList()
            );
        }
        return userDto;
    }
}
