package ks.sample.review.system.infrastructure.jms;

import ks.sample.common.infrastructure.jms.JmsMessageConverter;
import ks.sample.review.system.domain.ReviewApprovedEvent;
import ks.sample.review.system.domain.ReviewRejectedEvent;
import ks.sample.review.system.domain.ReviewSubmittedEvent;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.support.converter.MessageConversionException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class JmsMessageConverterTest {

    private JmsMessageConverter jmsMessageConverter;

    @Before
    public void setUp() throws Exception {
        jmsMessageConverter = new JmsMessageConverter();
    }

    // ------------------------------------------------------------------------------------------------
    // Misc
    // ------------------------------------------------------------------------------------------------

    @Test(expected = MessageConversionException.class)
    public void should_ThrowException_IfTypeIsMissing() throws Exception {
        Message messageWithoutType = new ActiveMQTextMessage();
        jmsMessageConverter.fromMessage(messageWithoutType);
    }

    // ------------------------------------------------------------------------------------------------
    // REVIEW_SUBMITTED_EVENT
    // ------------------------------------------------------------------------------------------------

    @Test
    public void should_Map_ReviewSubmittedEvent_ToTextMessageWithJson() throws JMSException {
        ReviewSubmittedEvent event = new ReviewSubmittedEvent();
        event.setReviewId("some-review-id");
        event.setRating(5);
        event.setSubject("A review");
        event.setContent("This is some content");

        TextMessage message = (TextMessage) jmsMessageConverter.toMessage(event, session());

        assertThat(message.getText()).isEqualTo("{\"reviewId\":\"some-review-id\",\"subject\":\"A review\",\"content\":\"This is some content\",\"rating\":5}");
        assertThat(message.getStringProperty("_type")).isEqualTo("ReviewSubmittedEvent");
    }

    @Test
    public void should_Map_TextMessageWithJson_To_ReviewSubmittedEvent() throws JMSException {
        TextMessage message = new ActiveMQTextMessage();
        message.setText("{\"reviewId\":\"some-review-id\",\"subject\":\"A review\",\"content\":\"This is some content\",\"rating\":5}");
        message.setStringProperty("_type", "ReviewSubmittedEvent");

        ReviewSubmittedEvent event = (ReviewSubmittedEvent) jmsMessageConverter.fromMessage(message);

        assertThat(event.getReviewId()).isEqualTo("some-review-id");
        assertThat(event.getSubject()).isEqualTo("A review");
        assertThat(event.getContent()).isEqualTo("This is some content");
        assertThat(event.getRating()).isEqualTo(5);
    }

    // ------------------------------------------------------------------------------------------------
    // REVIEW_APPROVED_EVENT
    // ------------------------------------------------------------------------------------------------

    @Test
    public void should_Map_ReviewApprovedEvent_ToTextMessageWithJson() throws JMSException {
        ReviewApprovedEvent event = new ReviewApprovedEvent();
        event.setReviewId("some-review-id");

        TextMessage message = (TextMessage) jmsMessageConverter.toMessage(event, session());

        assertThat(message.getText()).isEqualTo("{\"reviewId\":\"some-review-id\"}");
        assertThat(message.getStringProperty("_type")).isEqualTo("ReviewApprovedEvent");
    }

    @Test
    public void should_Map_TextMessageWithJson_To_ReviewApprovedEvent() throws JMSException {
        TextMessage message = new ActiveMQTextMessage();
        message.setText("{\"reviewId\":\"some-review-id\"}");
        message.setStringProperty("_type", "ReviewApprovedEvent");

        ReviewApprovedEvent event = (ReviewApprovedEvent) jmsMessageConverter.fromMessage(message);

        assertThat(event.getReviewId()).isEqualTo("some-review-id");
    }

    // ------------------------------------------------------------------------------------------------
    // REVIEW_REJECTED_EVENT
    // ------------------------------------------------------------------------------------------------

    @Test
    public void should_Map_ReviewRejectedEvent_ToTextMessageWithJson() throws JMSException {
        ReviewRejectedEvent event = new ReviewRejectedEvent();
        event.setReviewId("some-review-id");
        event.setReason("My reason");

        TextMessage message = (TextMessage) jmsMessageConverter.toMessage(event, session());

        assertThat(message.getText()).isEqualTo("{\"reviewId\":\"some-review-id\",\"reason\":\"My reason\"}");
        assertThat(message.getStringProperty("_type")).isEqualTo("ReviewRejectedEvent");
    }

    @Test
    public void should_Map_TextMessageWithJson_To_ReviewRejectedEvent() throws JMSException {
        TextMessage message = new ActiveMQTextMessage();
        message.setText("{\"reviewId\":\"some-review-id\",\"reason\":\"My reason\"}");
        message.setStringProperty("_type", "ReviewRejectedEvent");

        ReviewRejectedEvent event = (ReviewRejectedEvent) jmsMessageConverter.fromMessage(message);

        assertThat(event.getReviewId()).isEqualTo("some-review-id");
        assertThat(event.getReason()).isEqualTo("My reason");
    }

    // ------------------------------------------------------------------------------------------------
    // Utils
    // ------------------------------------------------------------------------------------------------

    private Session session() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
}