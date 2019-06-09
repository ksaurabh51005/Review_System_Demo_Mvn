package ks.sample.review.system.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ks.sample.review.system.domain.Review;
import ks.sample.review.system.domain.ReviewId;
import ks.sample.review.system.domain.ReviewRepository;

@Service
@AllArgsConstructor
public class ReviewCheckingResultService {

    private ReviewRepository reviewRepository;

    public void approve(ReviewId reviewId) {
        Review review = reviewRepository.find(reviewId);
        review.approve();
        reviewRepository.save(review);
    }

    public void reject(ReviewId reviewId) {
        Review review = reviewRepository.find(reviewId);
        review.reject();
        reviewRepository.save(review);
    }
}
