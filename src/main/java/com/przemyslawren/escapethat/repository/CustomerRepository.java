package com.przemyslawren.escapethat.repository;

import com.przemyslawren.escapethat.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
