package ks.sample.review.system.domain;

import ks.sample.review.system.domain.Rating;
import ks.sample.review.system.domain.Review;

public class ReviewFixtures {

    public static Review anInitialFiveStarSmartphoneReview() {
        String subject = "Best Smartphone Ever!";
        String content = "I've tested a lot of smartphones over the last couple of years. However," +
                "this one is definitely the best. High display resolution, fast CPU and long battery" +
                "life.";
        return new Review(subject, content, new Rating(5));
    }

    public static Review anApprovedFiveStarSmartphoneReview() {
        Review review = anInitialFiveStarSmartphoneReview();
        review.approve();
        return review;
    }
}
