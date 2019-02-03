package com.sb.kafka.kafkaexample.pipe.application;

import com.sb.kafka.kafkaexample.pipe.domain.Ticket;
import com.sb.kafka.kafkaexample.ticket.domain.TicketKTO;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.ArrayList;
import java.util.List;

public class TicketPipe {

    private List<Ticket> tickets = new ArrayList<>();


    @KafkaListener(topics = "ticket.pipe")
    public void addTicket(final TicketKTO ticket) {
        tickets.add(new Ticket(ticket.getTitle(), ticket.getContent()));
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
