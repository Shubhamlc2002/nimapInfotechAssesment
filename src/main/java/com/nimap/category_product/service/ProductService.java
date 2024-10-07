package com.nimap.category_product.service;



import com.nimap.category_product.Repository.ProductRepository;
import com.nimap.category_product.entity.Category;
import com.nimap.category_product.entity.Product;
import com.nimap.category_product.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryService categoryService;

   
    public Page<Product> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

  
    public Product createProduct(Product product) {
        // Ensure the category exists
        Long categoryId = product.getCategory().getId();
        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        return productRepository.save(product);
    }

   
    public Product getProductById(Long id) {
    	
    	System.out.println(productRepository.findById(id));
    	
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());

        
        if (productDetails.getCategory() != null) {
            Long categoryId = productDetails.getCategory().getId();
            Category category = categoryService.getCategoryById(categoryId);
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

   
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}

