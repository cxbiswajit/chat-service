package com.openprice.chatservice.config;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.cx.v3beta1.SessionsClient;
import com.google.cloud.dialogflow.cx.v3beta1.SessionsSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class DialogflowConfig {

    @Value("${dialogflow.REGION_ID}")
    private String locationId;

    @Value("${dialogflow.credentials.path}")
    private String credentialsPath;

    @Bean
    public GoogleCredentials googleCredentials() throws IOException {
         Resource resource = new ClassPathResource("openprice-392217-3ffcc4d15946.json");
        try (InputStream serviceAccountStream = resource.getInputStream()) {
            return GoogleCredentials.fromStream(serviceAccountStream).createScoped("https://www.googleapis.com/auth/cloud-platform");
        }
    }

    @Bean
    public CredentialsProvider credentialsProvider(GoogleCredentials googleCredentials) {
        return FixedCredentialsProvider.create(googleCredentials);
    }

    @Bean
    public SessionsClient sessionsClient(CredentialsProvider credentialsProvider) throws IOException {
        SessionsSettings.Builder sessionsSettingsBuilder = SessionsSettings.newBuilder();
        if (locationId.equals("global")) {
            sessionsSettingsBuilder.setEndpoint("dialogflow.googleapis.com:443");
        } else {
            sessionsSettingsBuilder.setEndpoint(locationId + "-dialogflow.googleapis.com:443");
        }
        sessionsSettingsBuilder.setCredentialsProvider(credentialsProvider);
        SessionsSettings sessionsSettings = sessionsSettingsBuilder.build();

        return SessionsClient.create(sessionsSettings);
    }
}
