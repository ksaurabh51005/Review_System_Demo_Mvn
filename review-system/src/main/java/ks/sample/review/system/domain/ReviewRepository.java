package ks.sample.review.system.domain;

import java.util.List;

public interface ReviewRepository {
    void save(Review review);
    List<Review> findAllByStatus(ReviewStatus reviewStatus);
    Review find(ReviewId reviewId);
}
