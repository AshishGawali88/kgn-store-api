package com.kgn.store.web;

import com.kgn.store.model.Product;
import com.kgn.store.repo.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository products;

    public ProductController(ProductRepository products) {
        this.products = products;
    }

    /** Public catalog. Optional ?category=cleaners to filter. */
    @GetMapping
    public List<Product> list(@RequestParam(value = "category", required = false) String category) {
        if (category == null || category.isBlank() || category.equalsIgnoreCase("all")) {
            return products.findAll();
        }
        return products.findByCatIgnoreCase(category.trim());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> one(@PathVariable Long id) {
        return products.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
