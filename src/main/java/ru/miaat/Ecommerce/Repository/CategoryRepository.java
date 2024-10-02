package ru.miaat.Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.miaat.Ecommerce.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
