package com.kgn.store.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public class OrderRequest {

    @NotBlank @Size(max = 100)
    public String customerName;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9 \\-]{7,15}$", message = "Enter a valid phone number")
    public String mobile;

    @Size(max = 600)
    public String address;

    @Pattern(regexp = "^(COD|ONLINE|CALL)?$", message = "Invalid payment method")
    public String paymentMethod;

    @NotEmpty(message = "Order must contain at least one item")
    @Size(max = 100)
    @Valid
    public List<OrderItemRequest> items;
}
