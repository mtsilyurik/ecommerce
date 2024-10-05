package ru.miaat.Ecommerce.Service.interf;

import org.springframework.data.domain.Pageable;
import ru.miaat.Ecommerce.Dto.OrderRequest;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Enum.OrderStatus;

import java.time.LocalDateTime;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);

    Response updateOrderItemStatus(Long orderItemStatus, String status);

    Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);
}
