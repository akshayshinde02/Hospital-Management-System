package com.billing.service;

import com.billing.exception.BillingException;
import com.billing.model.Billing;

public interface BillingService {
    
    public Billing createBill(String token, Billing billing) throws BillingException;

    public Billing viewBillDetails(String token,Long billingId) throws BillingException;
}
