package com.billing.service.impl;

import org.springframework.stereotype.Service;

import com.billing.client.PatientClient;
import com.billing.dto.PatientDto;
import com.billing.exception.BillingException;
import com.billing.model.Billing;
import com.billing.repository.BillingRepository;
import com.billing.service.BillingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService{

    private final BillingRepository repository;
    private final PatientClient patientClient;

    @Override
    public Billing createBill(String token, Billing billing) throws BillingException {
    
        if(token==null){
           throw new BillingException("token cannot null");
        }
        if(billing==null){
            throw new BillingException("Billing cannot null");
        }

        PatientDto patient = patientClient.getPatient(token);

        Billing savedBilling = new Billing();
        savedBilling.setPatientId(patient.getPatientId());
        savedBilling.setAmount(billing.getAmount());

        return repository.save(savedBilling);
    }

    @Override
    public Billing viewBillDetails(String token,Long billingId) throws BillingException {

        if(token==null){
            throw new BillingException("token not null");
        }else{

            return repository.findById(billingId).orElseThrow(()->new BillingException("Bill not found for {}"+billingId));
        }
    }

    
}
