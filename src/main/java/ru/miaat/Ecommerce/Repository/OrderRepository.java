package ru.miaat.Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.miaat.Ecommerce.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
