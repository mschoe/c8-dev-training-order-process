package org.camunda.training.handler;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.Map;
import java.util.UUID;

public class OrderIdJobHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) throws Exception {

        UUID orderId = UUID.randomUUID();
        client.newCompleteCommand(job.getKey()).variables(Map.of("orderId",orderId.toString())).send().join();
    }
}
