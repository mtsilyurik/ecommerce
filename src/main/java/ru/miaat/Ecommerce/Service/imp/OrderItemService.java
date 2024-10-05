package ru.miaat.Ecommerce.Service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.miaat.Ecommerce.Dto.OrderItemDto;
import ru.miaat.Ecommerce.Dto.OrderRequest;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Entity.Order;
import ru.miaat.Ecommerce.Entity.OrderItem;
import ru.miaat.Ecommerce.Entity.Product;
import ru.miaat.Ecommerce.Entity.User;
import ru.miaat.Ecommerce.Enum.OrderStatus;
import ru.miaat.Ecommerce.Exception.NotFoundException;
import ru.miaat.Ecommerce.Mapper.EntityDtoMapper;
import ru.miaat.Ecommerce.Repository.OrderItemRepository;
import ru.miaat.Ecommerce.Repository.OrderRepository;
import ru.miaat.Ecommerce.Repository.ProductRepository;
import ru.miaat.Ecommerce.Specification.OrderItemSpecification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService implements ru.miaat.Ecommerce.Service.interf.OrderItemService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    private final UserService userService;

    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response placeOrder(OrderRequest orderRequest) {

        User user = userService.getLogInUser();

        // map order request items to order entity
        List<OrderItem> orderItems = orderRequest.getItems().stream().map(
                orderItemRequest -> {
                    Product product = productRepository.findById(orderItemRequest.getProductId())
                            .orElseThrow(() -> new NotFoundException("Product not found"));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(orderItemRequest.getQuantity());
                    // setting price
                    orderItem.setPrice(
                            product.getPrice().multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()))
                    );

                    orderItem.setStatus(OrderStatus.PENDINDG);
                    orderItem.setUser(user);

                    return orderItem;
                }).toList();
        // calculate the total price
        BigDecimal totalPrice = orderRequest.getTotalPrice() != null && orderRequest.getTotalPrice().compareTo(BigDecimal.ZERO) > 0
                ? orderRequest.getTotalPrice()
                : orderItems.stream().map(OrderItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        // create order entity
        Order order = new Order();
        order.setOrderItemList(orderItems);
        order.setTotalPrice(totalPrice);

        // set the order ref to order items
        orderItems.forEach(orderItem -> { orderItem.setOrder(order); });

        orderRepository.save(order);

        return Response.builder()
                .status(200)
                .message("Success. Order id "+order.getId())
                .build();
    }

    @Override
    public Response updateOrderItemStatus(Long orderItemId, String status) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(
                    () -> new NotFoundException("Order item not found")
                );

        orderItem.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        orderItemRepository.save(orderItem);

        return Response.builder()
                .status(200)
                .message("Success")
                .build();
    }

    @Override
    public Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable) {
        Specification<OrderItem> spec = Specification.where(OrderItemSpecification.hasStatus(status))
                .and(OrderItemSpecification.createdBetween(startDate, endDate))
                .and(OrderItemSpecification.hasItemId(itemId));

        Page<OrderItem> orderItemPage = orderItemRepository.findAll(spec, pageable);

        if(orderItemPage.isEmpty()) {
            throw new NotFoundException("Order item not found");
        }

        List<OrderItemDto> orderItemsDto = orderItemPage.getContent()
                .stream()
                .map(entityDtoMapper::mapOrderItemToOrderItemDtoWithProductAndUser)
                .toList();

        return Response.builder()
                .status(200)
                .message("Success")
                .orderItemList(orderItemsDto)
                .totalPage(orderItemPage.getTotalPages())
                .totalElements(orderItemPage.getTotalElements())
                .build();
    }
}
