package com.kgn.store.repo;

import com.kgn.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCatIgnoreCase(String cat);
}
