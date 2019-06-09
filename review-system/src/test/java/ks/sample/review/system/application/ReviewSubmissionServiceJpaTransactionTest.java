package ks.sample.review.system.application;

import ks.sample.common.application.EventPublisher;
import ks.sample.review.system.application.ReviewSubmissionService;
import ks.sample.review.system.domain.CreateReviewCommand;
import ks.sample.review.system.domain.Review;
import ks.sample.review.system.domain.ReviewFixtures;
import ks.sample.review.system.domain.ReviewStatus;
import ks.sample.review.system.infrastructure.database.PersistenceConfig;
import ks.sample.review.system.infrastructure.database.ReviewEntityMapper;
import ks.sample.review.system.infrastructure.database.ReviewRepositoryAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        PersistenceConfig.class,
        ReviewRepositoryAdapter.class,
        ReviewSubmissionService.class,
        ReviewEntityMapper.class
})
public class ReviewSubmissionServiceJpaTransactionTest {

    @MockBean
    private EventPublisher eventPublisherMock;

    @Autowired
    private ReviewRepositoryAdapter reviewRepositoryAdapter;

    @Autowired
    private ReviewSubmissionService reviewSubmissionService;

    @Test
    public void should_NotSaveReview_IfEventSendingFails() {

        Review review = ReviewFixtures.anInitialFiveStarSmartphoneReview();
        doThrow(new RuntimeException()).when(eventPublisherMock).publish(anyList());

        CreateReviewCommand createReviewCommand = new CreateReviewCommand();
        createReviewCommand.setSubject("My review");
        createReviewCommand.setContent("Some content");
        createReviewCommand.setRating(1);

        try {
            reviewSubmissionService.createReview(createReviewCommand);
        } catch (RuntimeException e){
            // we ignore this exception as it's the one we've mocked before...
        }
        List<Review> reviews = reviewRepositoryAdapter.findAllByStatus(ReviewStatus.OPEN);
        assertThat(reviews).hasSize(0); // Nothing must be saved as the transaction was rolled back!
    }
}