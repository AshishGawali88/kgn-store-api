package com.kgn.store.repo;

import com.kgn.store.model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> { }
