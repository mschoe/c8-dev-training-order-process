package org.camunda.training.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.training.services.AccountBalanceService;

import java.util.Map;

public class AccountBalanceJobHandler implements JobHandler {

    private static final Logger LOG = LogManager.getLogger(AccountBalanceJobHandler.class);
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {
        LOG.info("Account Balance Job Handler started to work " + job.getVariables());

        Map variables = job.getVariablesAsMap();
        AccountBalanceService accountBalanceService = new AccountBalanceService();
        Double openAmount = accountBalanceService.deductAccountBalance((String) variables.get("customerID"), (Double) variables.get("orderTotal"));

        client.newCompleteCommand(job.getKey()).variables(Map.of("openAmount",openAmount)).send().join();
    }
}
