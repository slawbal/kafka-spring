package com.sb.kafka.kafkaexample.pipe.application;

import com.sb.kafka.kafkaexample.configuration.Sender;
import com.sb.kafka.kafkaexample.pipe.domain.Ticket;
import com.sb.kafka.kafkaexample.ticket.domain.TicketKTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;

import static com.sb.kafka.kafkaexample.testutils.Await.awaitForList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 2,
        topics = {TicketPipeTest.TEST_TOPIC})
public class TicketPipeTest {

    public static final String TEST_TOPIC = "ticket.pipe-test";

    @Autowired
    private Sender sender;

    @Autowired
    private TicketPipe systemUnderTest;

    @Autowired
    private ConsumerFactory<String, TicketKTO> consumerFactory;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @Test
    public void senderShouldPushEventToTopic() throws InterruptedException {
        sender.send(new TicketKTO("Topic title", "Topic content"));

        awaitForList(() ->
                systemUnderTest.getTickets(), Duration.ofMillis(100))
                .forEach(e -> assertThat(e).contains(new Ticket("Topic title", "Topic content")));
    }


}