package org.camunda.training.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountBalanceService {

    private static final Logger LOG = LogManager.getLogger(AccountBalanceService.class);

    /**
     * The customer credit are the last digits of the customer id
     */
    private final Pattern pattern = Pattern.compile("(.*?)(\\d*)");

    /**
     * Get the current customer credit
     *
     * @param customerId
     * @return the current credit of the given customer
     */
    private Double getCustomerBalance(String customerId) {
        Double accountBalance = 0.0;
        Matcher matcher = pattern.matcher(customerId);

        if (matcher.matches() && matcher.group(2) != null && matcher.group(2).length() > 0) {
            accountBalance = Double.valueOf(matcher.group(2));
        }
        System.out.printf("customer %s has credit of %f %s", customerId, accountBalance, System.lineSeparator());

        return accountBalance;
    }

    /**
     * Deduct the account balance for the given customer and the given amount
     *
     * @param customerId
     * @param orderTotal
     * @return the open order amount
     */
    public Double deductAccountBalance(String customerId, Double orderTotal) {

        Double openAmount, customerBalance, deductedBalance;
        customerBalance = getCustomerBalance(customerId);

        if (orderTotal > customerBalance) {
            // Not enough money --> charge credit card
            openAmount = orderTotal - customerBalance;
            deductedBalance = customerBalance;
        } else {
            deductedBalance = orderTotal;
            openAmount = 0.0;
        }

        System.out.printf("charged %f from the customer account, open amount is %f %s", deductedBalance, openAmount, System.lineSeparator());
        return openAmount;
    }
}
