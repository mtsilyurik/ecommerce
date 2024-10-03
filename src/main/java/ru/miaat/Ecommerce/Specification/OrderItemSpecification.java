package ru.miaat.Ecommerce.Specification;

import org.springframework.data.jpa.domain.Specification;
import ru.miaat.Ecommerce.Entity.OrderItem;
import ru.miaat.Ecommerce.Enum.OrderStatus;

import java.time.LocalDateTime;

public class OrderItemSpecification {

    public static Specification<OrderItem> hasStatus(OrderStatus status) {
        return ((root, criteriaQuery, criteriaBuilder) ->
                status != null ? criteriaBuilder.equal(root.get("status"), status)
                        : null);
    }


    public static Specification<OrderItem> createdBetween (LocalDateTime start, LocalDateTime end) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            if(start != null && end != null) {
                return criteriaBuilder.between(root.get("createdAt"), start, end);
            } else if (start != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), start);
            } else if (end != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), end);
            } else {
                return null;
            }
        });
    }


    public static Specification<OrderItem> hasItemId (Long itemId) {
        return ((root, query, criteriaBuilder) ->
                itemId != null ? criteriaBuilder.equal(root.get("itemId"), itemId)
                    : null);
    }
}
