package com.openprice.chatservice.service;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.dialogflow.cx.v3beta1.DetectIntentRequest;
import com.google.cloud.dialogflow.cx.v3beta1.DetectIntentResponse;
import com.google.cloud.dialogflow.cx.v3beta1.QueryInput;
import com.google.cloud.dialogflow.cx.v3beta1.QueryResult;
import com.google.cloud.dialogflow.cx.v3beta1.SessionName;
import com.google.cloud.dialogflow.cx.v3beta1.SessionsClient;
import com.google.cloud.dialogflow.cx.v3beta1.TextInput;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetectIntentService {

    private final SessionsClient sessionsClient;

    @Autowired
    public DetectIntentService(SessionsClient sessionsClient) {
        this.sessionsClient = sessionsClient;
    }

    public QueryResult detectIntent(String sessionId, String text) throws IOException, ApiException {

        SessionName session = SessionName.ofProjectLocationAgentSessionName("openprice-392217", "us-central1", "692d1093-96f2-4b25-a8f4-482d2e6667bd", sessionId);

        TextInput.Builder textInput = TextInput.newBuilder().setText(text);

        // Build the query with the TextInput and language code (en-US).
        QueryInput queryInput = QueryInput.newBuilder().setText(textInput).setLanguageCode("en-US").build();

        // Build the DetectIntentRequest with the SessionName and QueryInput.
        DetectIntentRequest request = DetectIntentRequest.newBuilder()
                .setSession(session.toString())
                .setQueryInput(queryInput)
                .build();

        // Performs the detect intent request.
        DetectIntentResponse response = sessionsClient.detectIntent(request);

        // Display the query result.
        QueryResult queryResult = response.getQueryResult();

        return queryResult;
    }

}

