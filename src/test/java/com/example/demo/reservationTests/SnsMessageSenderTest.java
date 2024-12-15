package com.example.demo.reservationTests;

import com.example.demo.notifications.aws.SnsMessageSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SnsException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SnsMessageSenderTest {

    @Mock
    private SnsClient snsClient;

    @InjectMocks
    private SnsMessageSender snsMessageSender;

    @Test
    void shouldSendSmsSuccessfully() {
        // Arrange
        String phoneNumber = "+37061773903";
        String message = "Test SMS message";
        PublishRequest request = PublishRequest.builder()
                .phoneNumber(phoneNumber)
                .message(message)
                .build();

        // Act
        snsMessageSender.sendSms(phoneNumber, message);

        // Assert
        verify(snsClient, times(1)).publish(request);
    }

    @Test
    void shouldHandleSnsException() {
        // Arrange
        String phoneNumber = "+37061773903";
        String message = "Test SMS message";
        PublishRequest request = PublishRequest.builder()
                .phoneNumber(phoneNumber)
                .message(message)
                .build();

        doThrow(SnsException.class).when(snsClient).publish(any(PublishRequest.class));

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> snsMessageSender.sendSms(phoneNumber, message));
    }
}

