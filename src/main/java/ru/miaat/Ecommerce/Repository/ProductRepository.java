package ru.miaat.Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.miaat.Ecommerce.Entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);


}
