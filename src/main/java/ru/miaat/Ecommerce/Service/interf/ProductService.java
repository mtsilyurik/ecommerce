package ru.miaat.Ecommerce.Service.interf;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import ru.miaat.Ecommerce.Dto.ProductDto;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Entity.Product;

import java.math.BigDecimal;

public interface ProductService {
    Response createProduct(Long categoryId, MultipartFile photo, String name, String description, BigDecimal price);

    Response deleteProduct(Long id);

    Response updateProduct(Long categoryId, Long productId, MultipartFile photo, String name, String description, BigDecimal price);

    Response getAllProducts();

    Response getProductById(Long id);

    Response getAllProductsOfCategory(Long categoryId);

    Response searchProduct(String searchValue);

    Response getAllBySlice(int pageNumber);
}
