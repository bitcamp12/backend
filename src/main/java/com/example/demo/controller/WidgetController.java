package com.example.demo.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "/api/payment")
public class WidgetController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {
        if (jsonBody == null || jsonBody.trim().isEmpty()) {
            logger.error("Received empty or null request body");
            return ResponseEntity.badRequest().body(new JSONObject() {{
                put("error", "Request body is empty");
            }});
        }

        logger.info("Received request body: " + jsonBody);

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // Parse the request body
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            logger.error("JSON Parsing error: ", e);
            return ResponseEntity.status(400).body(new JSONObject() {{
                put("error", "JSON Parsing error");
            }});
        }

        // Prepare the data to send to Toss Payments API
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // Prepare authorization for Toss API
        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // Make the API call to Toss Payments
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            outputStream.write(obj.toString().getBytes("UTF-8"));
        }

        int code = connection.getResponseCode();
        logger.info("Toss API response code: " + code);
        boolean isSuccess = code == 200;

        // Determine the appropriate response stream (success or error)
        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();
        StringBuilder responseContent = new StringBuilder();

        // Read the response
        try (Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                responseContent.append((char) ch);
            }
        }
        logger.info("Response content: " + responseContent.toString());

        // If the response is empty, log an error and return a 500 response
        if (responseContent.length() == 0) {
            logger.error("Received empty response from Toss API");
            return ResponseEntity.status(500).body(new JSONObject() {{
                put("error", "Received empty response from Toss API");
            }});
        }

        // Parse the response content
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(responseContent.toString());
        } catch (ParseException e) {
            logger.error("Error parsing the Toss API response: ", e);
            return ResponseEntity.status(500).body(new JSONObject() {{
                put("error", "Error parsing the Toss API response");
            }});
        }

        // Return the response from Toss API
        return ResponseEntity.status(code).body(jsonObject);
    }
}