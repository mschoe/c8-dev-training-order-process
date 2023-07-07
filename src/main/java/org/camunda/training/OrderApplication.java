package org.camunda.training;

import io.camunda.zeebe.client.ZeebeClient;
import org.camunda.training.handler.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class OrderApplication {

    private static final Logger LOG = LogManager.getLogger(OrderApplication.class);

    public static void main(String[] args) {
        try (ZeebeClient client = ZeebeClientFactory.getZeebeClient()) {
            LOG.info("Client connected to: " + client.newTopologyRequest().send().join());

            // Order Worker
            // Create an order Id
            client.newWorker().jobType("create-order-id")
                    .handler(new OrderIdJobHandler())
                    .open();

            // Invoke Payment
            client.newWorker().jobType("payment-invocation")
                    .handler(new InvokePaymentJobHandler())
                    .open();

            // Payment Worker
            // Account Balance Worker
            client.newWorker().jobType("charge-account-balance")
                    .handler(new AccountBalanceJobHandler())
                    .open();

            // Credit Card Worker
            client.newWorker().jobType("charge-credit-card")
                    .handler(new CreditCardJobHandler())
                    .open();

            // Confirm Payment
            client.newWorker().jobType("payment-confirmation")
                    .handler(new PaymentConfirmationJobHandler())
                    .open();

            // run until System.in receives exit command
            waitUntilSystemInput("exit");
        }
    }

    private static void waitUntilSystemInput(final String exitCode) {
        try (final Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                final String nextLine = scanner.nextLine();
                if (nextLine.contains(exitCode)) {
                    return;
                }
            }
        }
    }
}
