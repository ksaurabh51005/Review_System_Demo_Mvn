package ks.sample.review.system.application;

import ks.sample.review.system.JmsUtil;
import ks.sample.review.system.infrastructure.database.PersistenceConfig;
import ks.sample.review.system.infrastructure.database.ReviewRepositoryAdapter;
import ks.sample.review.system.infrastructure.jms.JmsConfig;
import ks.sample.review.system.domain.CreateReviewCommand;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        JmsConfig.class,
        PersistenceConfig.class,
        ReviewSubmissionService.class,
        ReviewSubmissionServiceJmsTransactionTest.MyTestListener.class
})
public class ReviewSubmissionServiceJmsTransactionTest {

    @MockBean
    private ReviewRepositoryAdapter reviewRepositoryAdapter;

    @Autowired
    private ReviewSubmissionService reviewSubmissionService;

    @Autowired
    private JmsTemplate jmsTemplate;

    private static boolean eventHasOccured;

    @Before
    public void setUp() throws Exception {
        eventHasOccured = false;
    }

    @Test
    @SneakyThrows
    public void should_NotSendEvent_IfReviewSavingFails() {

        CreateReviewCommand createReviewCommand = new CreateReviewCommand();
        createReviewCommand.setSubject("My review");
        createReviewCommand.setContent("Some content");
        createReviewCommand.setRating(1);

        // We simulate a failing JPA transaction. Note that we don't throw an exception,
        // as the sending of the JMS message happens after the repository save. So the
        // JMS message wouldn't be sent at all in case of an exception. However, we
        // simulate that the transaction fails and is marked for rollback - for whatever
        // reason.
        doAnswer(invocation -> {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return null;
        }).when(reviewRepositoryAdapter).save(any());

        reviewSubmissionService.createReview(createReviewCommand);

        JmsUtil jmsUtil = new JmsUtil(jmsTemplate);
        jmsUtil.waitForAll("Consumer.review_system.VirtualTopic.Events");

        assertThat(eventHasOccured).isFalse();
    }

    @Component
    static class MyTestListener {
        @JmsListener(destination = "Consumer.review_system.VirtualTopic.Events")
        public void onEvent() {
            eventHasOccured = true;
        }
    }
}