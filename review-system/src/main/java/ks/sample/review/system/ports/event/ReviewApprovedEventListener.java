package ks.sample.review.system.ports.event;

import ks.sample.review.system.domain.ReviewApprovedEvent;
import ks.sample.review.system.domain.ReviewId;
import ks.sample.common.application.MessageHandler;
import ks.sample.review.system.application.ReviewCheckingResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewApprovedEventListener extends MessageHandler<ReviewApprovedEvent> {

    private ReviewCheckingResultService reviewCheckingResultService;

    @Override
    public void onMessage(ReviewApprovedEvent reviewApprovedEvent) {
        ReviewId reviewId = new ReviewId(reviewApprovedEvent.getReviewId());
        reviewCheckingResultService.approve(reviewId);
    }
}