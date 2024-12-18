package com.example.demo.notifications.aws;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Service
@AllArgsConstructor
public class SnsMessageSender {

    private final SnsClient snsClient;


    public void sendSms(String phoneNumber, String message) {
        try {
            PublishRequest request = PublishRequest.builder()
                    .phoneNumber(phoneNumber)
                    .message(message)
                    .build();

            System.out.println("Debug: Sending SMS with PublishRequest: " + request);

            PublishResponse response = snsClient.publish(request);

            if (response == null) {
                throw new RuntimeException("SNS Publish returned null response");
            }

            System.out.println("Debug: Successfully sent SMS. Message ID: " + response.messageId());

        } catch (SnsException e) {
            System.err.println("Debug: AWS SNS Exception. Message: " + e.awsErrorDetails().errorMessage());
            throw new RuntimeException("AWS SNS Exception occurred", e);
        } catch (Exception ex) {
            System.err.println("Debug: General exception occurred. Message: " + ex.getMessage());
            throw new RuntimeException("Unexpected exception occurred", ex);
        }
    }
}
