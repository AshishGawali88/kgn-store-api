package com.kgn.store.dto;

import jakarta.validation.constraints.*;

// All external input is treated as untrusted: every field is size-capped
// and validated. Stored as data only — never executed or rendered as HTML
// by the API.
public class EnquiryRequest {

    @NotBlank @Size(max = 100)
    public String name;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9 \\-]{7,15}$", message = "Enter a valid phone number")
    public String mobile;

    @Size(max = 120)
    @Email(message = "Enter a valid email")
    public String email;

    @Size(max = 120)
    public String category;

    @Size(max = 2000)
    public String message;
}
