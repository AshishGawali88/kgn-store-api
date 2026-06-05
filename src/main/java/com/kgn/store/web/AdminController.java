package com.kgn.store.web;

import com.kgn.store.model.Enquiry;
import com.kgn.store.model.OrderRecord;
import com.kgn.store.repo.EnquiryRepository;
import com.kgn.store.repo.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Protected by AdminKeyFilter (requires header X-Admin-Key).
 * Lets the shop owner review incoming enquiries and orders.
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final EnquiryRepository enquiries;
    private final OrderRepository orders;

    public AdminController(EnquiryRepository enquiries, OrderRepository orders) {
        this.enquiries = enquiries;
        this.orders = orders;
    }

    @GetMapping("/enquiries")
    public List<Enquiry> enquiries() {
        return enquiries.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    @GetMapping("/orders")
    public List<OrderRecord> orders() {
        return orders.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
