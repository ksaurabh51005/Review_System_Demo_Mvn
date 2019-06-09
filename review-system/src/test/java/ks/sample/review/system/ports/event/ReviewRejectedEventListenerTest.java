package ks.sample.review.system.ports.event;

import ks.sample.review.system.application.ReviewCheckingResultService;
import ks.sample.review.system.domain.Review;
import ks.sample.review.system.domain.ReviewFixtures;
import ks.sample.review.system.domain.ReviewRejectedEvent;
import ks.sample.review.system.domain.ReviewRepository;
import ks.sample.review.system.domain.ReviewStatus;
import ks.sample.review.system.infrastructure.memory.MessageStubRepository;
import ks.sample.review.system.infrastructure.memory.ReviewStubRepository;
import ks.sample.review.system.ports.event.ReviewRejectedEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ReviewRejectedEventListener.class,
        ReviewCheckingResultService.class,
        ReviewStubRepository.class,
        MessageStubRepository.class,
        JacksonAutoConfiguration.class
})
public class ReviewRejectedEventListenerTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewRejectedEventListener reviewRejectedEventListener;

    @Test
    public void should_RejectReview() {

        Review openFiveStarSmartphoneReview = ReviewFixtures.anInitialFiveStarSmartphoneReview();
        reviewRepository.save(openFiveStarSmartphoneReview);

        ReviewRejectedEvent event = new ReviewRejectedEvent();
        event.setReviewId(openFiveStarSmartphoneReview.getReviewId().getReviewId());

        reviewRejectedEventListener.onMessage(event);

        Review review = reviewRepository.find(openFiveStarSmartphoneReview.getReviewId());
        assertThat(review.getReviewStatus()).isEqualTo(ReviewStatus.REJECTED);
    }
}