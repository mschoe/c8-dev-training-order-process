package org.camunda.training.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.training.services.CreditCardLookupService;

import java.util.Date;
import java.util.Map;

public class LookupCreditCardJobHandler implements JobHandler {
    private static final Logger LOG = LogManager.getLogger(LookupCreditCardJobHandler.class);
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        LOG.info("Look Up Credit Card Job Handler started to work " + job.getVariables());

        CreditCardLookupService creditCardLookupService = new CreditCardLookupService();

        Map creditCardVariabels = job.getVariablesAsMap();
        creditCardVariabels.put("cardNumber", creditCardLookupService.getCreditCardNumber());
        creditCardVariabels.put("csv", creditCardLookupService.getCsv());
        creditCardVariabels.put("expiryDate", creditCardLookupService.getExpiryDate());
        creditCardVariabels.put("requestCreditCardDetails", false);
        client.newCompleteCommand(job.getKey()).variables(creditCardVariabels).send().join();
    }
}
