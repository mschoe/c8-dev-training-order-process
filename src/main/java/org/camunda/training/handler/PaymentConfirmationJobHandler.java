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

public class PaymentConfirmationJobHandler implements JobHandler {

    private static final Logger LOG = LogManager.getLogger(PaymentConfirmationJobHandler.class);
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        try (ZeebeClient messageClient = ZeebeClientFactory.getZeebeClient()) {

            Map variables = job.getVariablesAsMap();
            String orderId = (String) variables.get ("orderId");
            LOG.info(job.getType() + " for order id: " + orderId);
            PublishMessageResponse messageResponse = messageClient.newPublishMessageCommand()
                    .messageName("payment-confirmation-message")
                    .correlationKey(orderId)
                    .messageId("")
                    .send().join();

            client.newCompleteCommand(job.getKey()).variables(Map.of("messageKey", messageResponse.getMessageKey())).send().join();
        }
    }
}
