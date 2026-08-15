package com.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billing.model.Billing;

public interface BillingRepository extends JpaRepository<Billing,Long>{
    
}
