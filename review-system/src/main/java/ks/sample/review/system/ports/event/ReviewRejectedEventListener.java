package ks.sample.review.system.ports.event;

import ks.sample.common.application.MessageHandler;
import ks.sample.review.system.application.ReviewCheckingResultService;
import ks.sample.review.system.domain.ReviewId;
import ks.sample.review.system.domain.ReviewRejectedEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewRejectedEventListener extends MessageHandler<ReviewRejectedEvent> {

    private ReviewCheckingResultService reviewCheckingResultService;

    @Override
    public void onMessage(ReviewRejectedEvent reviewRejectedEvent) {
        ReviewId reviewId = new ReviewId(reviewRejectedEvent.getReviewId());
        reviewCheckingResultService.reject(reviewId);
    }
}