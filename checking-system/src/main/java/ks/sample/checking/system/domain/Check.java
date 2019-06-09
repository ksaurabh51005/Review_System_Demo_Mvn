package ks.sample.checking.system.domain;

import ks.sample.common.domain.DomainEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Jackson mapper default!
public class Check implements DomainEntity<ReviewId> {

    // A list of "bad words" which are considered an inappropriate language. Any
    // review containing one of these words will be rejected.
    //
    // Note that this is just an example and probably not a complete list ;)
    private static final String[] INAPPROPRIATE_CONTENT = new String[] {
            "bad", "wrong", "shit", "fuck"
    };

    private ReviewId reviewId;
    private String text;
    private String rejectionReason;
    private boolean approved;

    public Check(ReviewId reviewId, String subject, String content) {
        this.reviewId = reviewId;
        this.text = subject + " " + content;
        log.info("New check initialized for review. [reviewId={}]", reviewId);
    }

    /**
     * This method will check the review to either approve or reject it.
     * To do so, it will check the content for inappropriate words which
     * might be offending for other users. If the review contains such
     * words, it will be rejected with a dedicated message/reason.
     */
    public void check() {
        boolean containsInappropriateContent = Arrays.stream(INAPPROPRIATE_CONTENT)
                                                     .anyMatch(text.toLowerCase()::contains);
        if(containsInappropriateContent) {
            reject("Your review contains inappropriate content");
        } else {
            approve();
        }
    }

    private void reject(String reason) {
        this.approved = false;
        this.rejectionReason = reason;
        log.info("Review rejected. [reviewId={}, reason={}]", reviewId, reason);

        ReviewRejectedEvent event = new ReviewRejectedEvent();
        event.setReviewId(reviewId.getReviewId());
        event.setReason(reason);
        raise(event);
    }

    private void approve() {
        this.approved = true;
        log.info("Review approved. [reviewId={}]", reviewId);

        ReviewApprovedEvent event = new ReviewApprovedEvent();
        event.setReviewId(reviewId.getReviewId());
        raise(event);
    }

    @Override
    public ReviewId getId() {
        return reviewId;
    }
}
