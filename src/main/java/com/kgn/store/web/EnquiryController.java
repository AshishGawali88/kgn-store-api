package com.kgn.store.web;

import com.kgn.store.dto.EnquiryRequest;
import com.kgn.store.model.Enquiry;
import com.kgn.store.repo.EnquiryRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

    private final EnquiryRepository enquiries;

    public EnquiryController(EnquiryRepository enquiries) {
        this.enquiries = enquiries;
    }

    /** Public: a customer submits an enquiry from the Contact form. */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody EnquiryRequest req) {
        Enquiry e = new Enquiry();
        e.setName(req.name.trim());
        e.setMobile(req.mobile.trim());
        e.setEmail(req.email == null ? null : req.email.trim());
        e.setCategory(req.category == null ? null : req.category.trim());
        e.setMessage(req.message == null ? null : req.message.trim());
        Enquiry saved = enquiries.save(e);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("id", saved.getId(), "status", "received"));
    }
}
