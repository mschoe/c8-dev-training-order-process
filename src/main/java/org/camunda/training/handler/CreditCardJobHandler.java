package org.camunda.training.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.training.OrderApplication;
import org.camunda.training.services.CreditCardService;

import java.util.Map;

public class CreditCardJobHandler implements JobHandler {

    private static final Logger LOG = LogManager.getLogger(CreditCardJobHandler.class);
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        LOG.info("Credit Card Job Handler started to work " + job.getVariables());

        Map variables = job.getVariablesAsMap();
        CreditCardService creditcardService = new CreditCardService();
        creditcardService.chargeCreditCard((Double) variables.get("openAmount"));
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
