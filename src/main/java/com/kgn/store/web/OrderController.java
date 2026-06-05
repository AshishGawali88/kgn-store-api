package com.kgn.store.web;

import com.kgn.store.dto.OrderItemRequest;
import com.kgn.store.dto.OrderRequest;
import com.kgn.store.model.OrderItem;
import com.kgn.store.model.OrderRecord;
import com.kgn.store.model.Product;
import com.kgn.store.repo.OrderRepository;
import com.kgn.store.repo.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orders;
    private final ProductRepository products;
    private final SecureRandom random = new SecureRandom();

    public OrderController(OrderRepository orders, ProductRepository products) {
        this.orders = orders;
        this.products = products;
    }

    /**
     * Public: place an order. Prices and product names are taken from the
     * SERVER's catalog, never from the client, so a tampered request cannot
     * change the price. The total is computed server-side.
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OrderRequest req) {
        List<OrderItem> items = new ArrayList<>();
        int total = 0;

        for (OrderItemRequest line : req.items) {
            Product p = products.findById(line.productId).orElse(null);
            if (p == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "invalid_product",
                                     "message", "Unknown product id: " + line.productId));
            }
            int lineTotal = p.getPrice() * line.qty;
            total += lineTotal;
            items.add(new OrderItem(p.getId(), p.getName(), line.qty, p.getPrice()));
        }

        OrderRecord order = new OrderRecord();
        order.setOrderCode("KGN-" + (1000 + random.nextInt(9000)));
        order.setCustomerName(req.customerName.trim());
        order.setMobile(req.mobile.trim());
        order.setAddress(req.address);
        order.setPaymentMethod(req.paymentMethod == null || req.paymentMethod.isBlank()
                ? "CALL" : req.paymentMethod);
        order.setItems(items);
        order.setTotal(total);

        OrderRecord saved = orders.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "orderCode", saved.getOrderCode(),
                "total", saved.getTotal(),
                "status", saved.getStatus()
        ));
    }
}
