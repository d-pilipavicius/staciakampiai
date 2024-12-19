package com.example.demo.notifications.event;

import com.example.demo.notifications.aws.SnsMessageSender;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReservationEventListener {

    private final SnsMessageSender snsMessageSender;

    @EventListener
    public void handleReservationCreatedEvent(ReservationCreatedEvent event) {
        snsMessageSender.sendSms(event.getPhoneNumber(), event.getText());
        System.out.println("sms sent!!!");
    }
}
