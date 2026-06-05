package com.kgn.store.repo;

import com.kgn.store.model.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderRecord, Long> { }
