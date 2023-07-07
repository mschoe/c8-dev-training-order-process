package org.camunda.training.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.training.handler.CreditCardJobHandler;

public class CreditCardService {

    private static final Logger LOG = LogManager.getLogger(CreditCardService.class);
    public void chargeCreditCard(Double openAmount) {

        LOG.info( openAmount + " will be charged from customer credit card");
    }
}
