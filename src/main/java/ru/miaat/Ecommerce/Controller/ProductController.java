package ru.miaat.Ecommerce.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Entity.Product;
import ru.miaat.Ecommerce.Exception.InvalidCredentialsException;
import ru.miaat.Ecommerce.Service.interf.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(
            @RequestParam Long categoryId,
            @RequestParam MultipartFile image,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price)
    {
        if(categoryId == null || image.isEmpty() || name.isBlank() || description.isBlank() || price == null){
            throw new InvalidCredentialsException("All Fields are required");
        }

        return ResponseEntity.ok(productService.createProduct(categoryId, image, name, description, price));
    }

    @DeleteMapping("delete/{prodId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long prodId){
        return ResponseEntity.ok(productService.deleteProduct(prodId));
    }

    @PutMapping("/update/{prodId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(
            @PathVariable Long prodId,
            @RequestParam(required = false) MultipartFile photo,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal price)
    {
        return ResponseEntity.ok(productService.updateProduct(categoryId, prodId, photo, name, description, price));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAll(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get-by-id/{prodId}")
    public ResponseEntity<Response> getProductById(@PathVariable Long prodId){
        return ResponseEntity.ok(productService.getProductById(prodId));
    }

    @GetMapping("/get-all-of-category/{catId}")
    public ResponseEntity<Response> getProductOfCategory(@PathVariable Long catId){
        return ResponseEntity.ok(productService.getAllProductsOfCategory(catId));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchForProducts(@RequestParam String searchValue){
        return ResponseEntity.ok(productService.searchProduct(searchValue));
    }

    @GetMapping("/get-all-by-pages")
    public ResponseEntity<Response> getAllProductsByPages(
            @RequestParam int pageNumber)
    {
        return ResponseEntity.ok(productService.getAllBySlice(pageNumber));
    }

}
