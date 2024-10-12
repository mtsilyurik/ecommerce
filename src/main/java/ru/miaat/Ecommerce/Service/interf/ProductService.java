package ru.miaat.Ecommerce.Service.interf;

import org.springframework.web.multipart.MultipartFile;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Entity.Product;

import java.math.BigDecimal;

public interface ProductService {
    Response createProduct(String categoryId, MultipartFile photo, String name, String description, BigDecimal price);

    Response deleteProduct(Long id);

    Response updateProduct(Long categoryId, Long productId, MultipartFile photo, String name, String description, BigDecimal price);

    Response getAllProducts();

    Response getProductById(Long id);

    Response getAllProductsOfCategory(Long categoryId);

    Response searchProduct(String searchValue);
}
