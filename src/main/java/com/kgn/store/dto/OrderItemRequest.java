package com.kgn.store.dto;

import jakarta.validation.constraints.*;

public class OrderItemRequest {

    @NotNull
    public Long productId;

    @Min(1) @Max(999)
    public int qty;
}
