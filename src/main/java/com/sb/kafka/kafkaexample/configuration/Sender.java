package com.sb.kafka.kafkaexample.configuration;

import com.sb.kafka.kafkaexample.ticket.domain.TicketKTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class Sender {

    @Autowired
    private KafkaTemplate<String, TicketKTO> kafkaTemplate;

    public void send(TicketKTO ticket) {
        System.out.println("TRANSMITING");
        this.kafkaTemplate.send("ticket.pipe", ticket);
    }


}
