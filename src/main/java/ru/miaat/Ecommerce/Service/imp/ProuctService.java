package ru.miaat.Ecommerce.Service.imp;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.miaat.Ecommerce.Dto.ProductDto;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Entity.Category;
import ru.miaat.Ecommerce.Entity.Product;
import ru.miaat.Ecommerce.Exception.NotFoundException;
import ru.miaat.Ecommerce.Mapper.EntityDtoMapper;
import ru.miaat.Ecommerce.Repository.CategoryRepository;
import ru.miaat.Ecommerce.Repository.ProductRepository;
import ru.miaat.Ecommerce.Repository.UserRepository;
import ru.miaat.Ecommerce.Service.AwsS3Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProuctService implements ru.miaat.Ecommerce.Service.interf.ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final EntityDtoMapper entityDtoMapper;

    private final AwsS3Service awsS3Service;
    private final UserRepository userRepository;

    @Override
    public Response createProduct(String categoryId, MultipartFile photo, String name, String description, BigDecimal price) {
        Category category = categoryRepository.findById(Long.parseLong(categoryId))
                .orElseThrow(
                        () -> new NotFoundException("Category " + categoryId + " not found")
                );
        String imageUrl = "";
        try {
            imageUrl = awsS3Service.saveImageToS3(photo);
        } catch (Exception e) {

        }


        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);


        productRepository.save(product);
        return Response.builder()
                .status(200)
                .message("Success")
                .build();
    }

    @Override
    public Response createProductDto(ProductDto productDto) {
        if (productDto == null) {
            return Response.builder()
                    .status(400)
                    .message("Product is null")
                    .build();
        }

        Product p = Product.builder()
                .name(productDto.getName())
                .imageUrl(productDto.getImageUrl())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .build();

        productRepository.save(p);

        ProductDto pdto = entityDtoMapper.mapProductToProductDto(p);

        return Response.builder()
                .status(200)
                .message("Success")
                .product(pdto)
                .build();
    }

    @Override
    public Response deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Product " + id + " not found")
                );

        productRepository.delete(product);

        return Response.builder()
                .status(200)
                .message("Success")
                .build();
    }

    @Override
    public Response updateProduct(Long categoryId, Long productId, MultipartFile photo, String name, String description, BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new NotFoundException("Product " + productId + " not found")
                );

        Category category = null;
        String imageUrl = null;

        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(
                            () -> new NotFoundException("Category " + categoryId + " not found")
                    );
        }

        if(category != null) {
            product.setCategory(category);
        }

        if(photo != null && !photo.isEmpty()) {
            imageUrl = awsS3Service.saveImageToS3(photo);
            product.setImageUrl(imageUrl);
        }

        if(name != null && !name.isBlank()) {
            product.setName(name);
        }

        if(description != null) {
            product.setDescription(description);
        }

        if(price != null) {
            product.setPrice(price);
        }

        productRepository.save(product);

        return Response.builder()
                .status(200)
                .message("Success")
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<ProductDto> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(entityDtoMapper::mapProductToProductDto)
                .toList();


        return Response.builder()
                .status(200)
                .message("Success")
                .products(products)
                .build();
    }

    @Override
    public Response getProductById(Long id) {
        ProductDto productDto = entityDtoMapper.mapProductToProductDto(
                productRepository.findById(id)
                        .orElseThrow(
                                () -> new NotFoundException("Product " + id + " not found")
                        )
        );

        return Response.builder()
                .status(200)
                .message("Success")
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProductsOfCategory(Long categoryId) {
        List<ProductDto> products = productRepository.findByCategoryId(categoryId).stream()
                .map(entityDtoMapper::mapProductToProductDto)
                .toList();

        if(products.isEmpty()) {
            throw new NotFoundException("No products found");
        }
        return Response.builder()
                .status(200)
                .message("Success")
                .products(products)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
        List<ProductDto> products = productRepository.findByNameContainingOrDescriptionContaining(searchValue, searchValue)
                .stream()
                .map(entityDtoMapper::mapProductToProductDto)
                .toList();

        if(products.isEmpty()) {
            throw new NotFoundException("No products found");
        }

        return Response.builder()
                .status(200)
                .message("Success")
                .products(products)
                .build();
    }

    @Override
    public Response getAllBySlice(int pageNumber) {
        final int elNumber = 5;
        Slice<Product> slice = productRepository
                .findAll(PageRequest.of(
                        pageNumber,
                        elNumber,
                        Sort.by(Sort.Direction.ASC, "id")
                        )
                );
        Slice<ProductDto> sliceDto = slice.map(entityDtoMapper::mapProductToProductDto);

        return Response.builder()
                .status(200)
                .message("Success")
                .pageNumber(pageNumber)
                .productsPage(sliceDto)
                .build();
    }
}
