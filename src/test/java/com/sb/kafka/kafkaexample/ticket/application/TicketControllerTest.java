package com.sb.kafka.kafkaexample.ticket.application;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.sb.kafka.kafkaexample.pipe.application.TicketPipeTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static com.sb.kafka.kafkaexample.testutils.Await.awaitFor;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1,
        topics = {TicketPipeTest.TEST_TOPIC})
public class TicketControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void newTicket_shouldCreateNewTicket() {
        createTicket(new TicketDTO("Title1", "Content1"));
    }

    @Test
    public void getTicket_shouldReturnStatus200() {
        getTicket()
                .expectStatus().isOk();
    }

    @Test
    public void getTicket_shouldReturnTicket() throws InterruptedException {
        createTicket(new TicketDTO("Title1", "Content1"));

        awaitFor(()-> getTicket(), Duration.ofMillis(100))
                .forEach((e) -> e
                .expectBody(TicketDTO.class)
                .isEqualTo(new TicketDTO("Title1", "Content1")));
    }

    private WebTestClient.ResponseSpec getTicket() {
        return this.webTestClient.get()
                .uri("/ticket/")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange();
    }

    private void createTicket(TicketDTO ticketDTO) {
        this.webTestClient.post()
                .uri("/ticket/")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(ticketDTO), TicketDTO.class)
                .exchange()
                .expectStatus().isOk();
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class TicketDTO {
        private String title;
        private String content;

        public TicketDTO() {
        }

        public TicketDTO(String title, String content) {
            this.title = title;
            this.content = content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TicketDTO ticketDTO = (TicketDTO) o;

            if (title != null ? !title.equals(ticketDTO.title) : ticketDTO.title != null) return false;
            return content != null ? content.equals(ticketDTO.content) : ticketDTO.content == null;
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (content != null ? content.hashCode() : 0);
            return result;
        }
    }
}