package ks.sample.review.system.infrastructure.memory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ks.sample.review.system.domain.Review;
import ks.sample.review.system.domain.ReviewId;
import ks.sample.review.system.domain.ReviewRepository;
import ks.sample.review.system.domain.ReviewStatus;

public class ReviewStubRepository implements ReviewRepository {

    private Set<Review> reviews = new HashSet<>();

    @Override
    public void save(Review review) {
        reviews.add(review);
    }

    @Override
    public List<Review> findAllByStatus(ReviewStatus reviewStatus) {
        return reviews
                .stream()
                .filter(review -> review.getReviewStatus() == reviewStatus)
                .collect(Collectors.toList());
    }

    @Override
    public Review find(ReviewId reviewId) {
        return reviews
                .stream()
                .filter(review -> review.getReviewId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }
}