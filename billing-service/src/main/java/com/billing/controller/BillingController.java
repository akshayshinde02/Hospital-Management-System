package com.billing.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.billing.exception.BillingException;
import com.billing.model.Billing;
import com.billing.service.BillingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/billing")
@Slf4j
public class BillingController {
    
    private final BillingService billingService;

    @PostMapping("/create-bill")
    public ResponseEntity<Billing> createBill(@RequestHeader("Authorization") String token,@RequestBody Billing billing) throws BillingException{

        log.info("inside controller"+"createBill");

        return new ResponseEntity<>(billingService.createBill(token, billing), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/view-bill")
    public ResponseEntity<Billing> getBill(@RequestHeader("Authorization") String token, @PathVariable Long id) throws BillingException{

        log.info("inside controller"+"getBill");

        return new ResponseEntity<>(billingService.viewBillDetails(token, id),HttpStatus.FOUND);
    }
}
