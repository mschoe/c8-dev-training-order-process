package org.camunda.training;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;
public class ZeebeClientFactory {
    
    private static final String ZEEBE_ADDRESS = "d5d00e99-8dd3-4db1-bf67-f9f7cd648c07.bru-2.zeebe.camunda.io:443";
    private static final String ZEEBE_CLIENT_ID = "xpodkujGpAbpBoK05~yQ6OjPDCmZ6ooJ";
    private static final String ZEEBE_CLIENT_SECRET = "5RxKUUq2CeWHb9TGMifVigc5cOftDNjpJrBRY_8fL6NuMhQHlqdZpGeeHEmhox7x";
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";
    public static ZeebeClient getZeebeClient() {

        final OAuthCredentialsProvider credentialsProvider = new OAuthCredentialsProviderBuilder()
                .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                .audience(ZEEBE_TOKEN_AUDIENCE)
                .clientId(ZEEBE_CLIENT_ID)
                .clientSecret(ZEEBE_CLIENT_SECRET)
                .build();

        return ZeebeClient.newClientBuilder()
                .gatewayAddress(ZEEBE_ADDRESS)
                .credentialsProvider(credentialsProvider)
                .build();
    }
}
