# c8-dev-training-order-process

Example application for the [Camunda 8 - Platform for Developers Training](https://academy.camunda.com/c8-platform-developers-ilt-en).

## Prepare Setup

### Cluster API Credentials 
Create new []Client Credentials](https://docs.camunda.io/docs/guides/setup-client-connection-credentials/) for the cluster and provide them as environment variables for the [Zeebe CLI Client](https://docs.camunda.io/docs/apis-tools/cli-client/).

## Deploy Processes

Deploy the order and the payment process
```
zbctl deploy resource resources/payment-process.bpmn
```

## Start a process instance
Start new instances with the zeebe client
```
zbctl create instance order_process --variables "{\"orderTotal\": 256.04, \"customerID\": \"bnm57\"}"
```

