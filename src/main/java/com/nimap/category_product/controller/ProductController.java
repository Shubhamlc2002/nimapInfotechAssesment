package com.nimap.category_product.controller;



import com.nimap.category_product.entity.Product;
import com.nimap.category_product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET all products with pagination
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> products = productService.getAllProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // POST create new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET product by ID with category details
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        // To avoid LazyInitializationException, you can fetch category details here
        product.getCategory().getName(); // Initialize category
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // PUT update product by ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id, 
            @RequestBody Product productDetails) {
        Product updated = productService.updateProduct(id, productDetails);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // DELETE product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

