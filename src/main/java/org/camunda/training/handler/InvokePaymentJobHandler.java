package org.camunda.training.handler;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.response.PublishMessageResponse;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.training.ZeebeClientFactory;

import java.util.Map;

public class InvokePaymentJobHandler implements JobHandler {

    private static final Logger LOG = LogManager.getLogger(InvokePaymentJobHandler.class);
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        LOG.info(job.getType());
        try (ZeebeClient messageClient = ZeebeClientFactory.getZeebeClient()) {

            Map variables = job.getVariablesAsMap();
            String orderId = (String) variables.get ("orderId");
            variables.put("orderId", orderId);
            PublishMessageResponse messageResponse = messageClient.newPublishMessageCommand()
                    .messageName("payment-invocation-message")
                    .correlationKey(orderId)
                    .messageId("")
                    .variables(variables).send().join();

            client.newCompleteCommand(job.getKey()).variables(Map.of("messageKey", messageResponse.getMessageKey())).send().join();
        }
    }
}
