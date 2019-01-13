package com.sb.kafka.kafkaexample.ticket.application;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sb.kafka.kafkaexample.configuration.Sender;
import com.sb.kafka.kafkaexample.pipe.application.TicketPipe;
import com.sb.kafka.kafkaexample.ticket.domain.TicketKTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TicketController {

    private final Sender sender;

    private final TicketPipe pipe;

    @Autowired
    public TicketController(Sender sender, TicketPipe pipe) {
        this.sender = sender;
        this.pipe = pipe;
    }

    @PostMapping("/ticket/")
    public Mono<TicketDTO> createTicket(@RequestBody TicketDTO ticket) {
        TicketKTO ticketKafka = new TicketKTO(ticket.getTitle(), ticket.getContent());
        sender.send(ticketKafka);
        return Mono.just(new TicketDTO(ticketKafka.getTitle(), ticketKafka.getContent()));
    }

    @GetMapping("/ticket/")
    public Mono<TicketDTO> getTicket() {
        return this.pipe.getTickets().stream()
                .findFirst()
                .map(e -> Mono.just(new TicketDTO(e.getTitle(), e.getContent())))
                .orElse(Mono.empty());
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class TicketDTO {
        private String title;
        private String content;

        public TicketDTO(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public TicketDTO() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
