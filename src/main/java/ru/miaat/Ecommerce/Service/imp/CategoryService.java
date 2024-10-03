package ru.miaat.Ecommerce.Service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.miaat.Ecommerce.Dto.CategoryDto;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Entity.Category;
import ru.miaat.Ecommerce.Exception.NotFoundException;
import ru.miaat.Ecommerce.Mapper.EntityDtoMapper;
import ru.miaat.Ecommerce.Repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService implements ru.miaat.Ecommerce.Service.interf.CategoryService {

    private final CategoryRepository categoryRepository;

    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response createCategory(CategoryDto category) {
        Category categoryEntity = new Category();
        categoryEntity.setName(category.getName());
        categoryRepository.save(categoryEntity);

        return Response.builder()
                .status(200)
                .message("Success")
                .build();
    }

    @Override
    public Response updateCategory(Long catId, CategoryDto category) {
        Category cat = categoryRepository
                        .findById(catId)
                        .orElseThrow(
                                () -> new NotFoundException("Category not found")
                        );
        cat.setName(category.getName());
        categoryRepository.save(cat);

        return Response.builder()
                .status(200)
                .message("Success")
                .build();
    }

    @Override
    public Response deleteCategory(Long catId) {
        Category cat = categoryRepository
                .findById(catId)
                .orElseThrow(
                        () -> new NotFoundException("Category not found"));
        categoryRepository.delete(cat);

        return Response.builder()
                .status(200)
                .message("'Success")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<CategoryDto> categories = categoryRepository.findAll().stream()
                .map(entityDtoMapper::mapCategoryToCategoryDto)
                .toList();
        return Response.builder()
                .status(200)
                .message("Success")
                .categoryList(categories)
                .build();
    }

    @Override
    public Response getCategoryById(Long catId) {
        Category cat = categoryRepository
                .findById(catId)
                .orElseThrow(
                        () -> new NotFoundException("Category not found"));
        CategoryDto categoryDto = entityDtoMapper.mapCategoryToCategoryDto(cat);

        return Response.builder()
                .status(200)
                .message("Success")
                .category(categoryDto)
                .build();
    }
}
