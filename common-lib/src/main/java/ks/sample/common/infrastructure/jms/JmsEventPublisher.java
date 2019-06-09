package ks.sample.common.infrastructure.jms;

import ks.sample.common.application.EventPublisher;
import ks.sample.common.domain.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

@Slf4j
@AllArgsConstructor
public class JmsEventPublisher implements EventPublisher {

    private final JmsTemplate jmsTemplate;
    private final Destination destination;

    @Override
    public void publish(DomainEvent event) {
        jmsTemplate.convertAndSend(destination, event);
        log.trace("Sent event. [destination={}, event={}]", destination, event);
    }
}
