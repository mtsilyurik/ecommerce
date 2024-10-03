package ru.miaat.Ecommerce.Service.interf;

import ru.miaat.Ecommerce.Dto.CategoryDto;
import ru.miaat.Ecommerce.Dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDto category);
    Response updateCategory(Long catId, CategoryDto category);
    Response deleteCategory(Long catId);
    Response getAllCategories();
    Response getCategoryById(Long catId);
}
