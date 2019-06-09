package ks.sample.review.system.application;

import ks.sample.common.application.EventPublisher;
import ks.sample.review.system.domain.CreateReviewCommand;
import ks.sample.review.system.domain.Review;
import ks.sample.review.system.domain.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReviewSubmissionService {

    private ReviewRepository reviewRepository;
    private EventPublisher eventPublisher;

    // This method is transactional as we do two things at the same time:
    // saving the review and sending a new event. If the event sending fails,
    // we also want to rollback the saved review. If the saving fails, we also
    // want to rollback the sending of the message.
    @Transactional
    public void createReview(CreateReviewCommand createReviewCommand) {

        Review review = Review.createReview(createReviewCommand);

        // Note: The order of the following two actions is not important in the
        // sense of transactions. If one of it fails, the other will be rolled
        // back. No matter if we first save the entity or first send the message.
        // You can play around with it, using the integrations tests.
        reviewRepository.save(review);
        eventPublisher.publish(review.getOccurredEvents());
    }
}