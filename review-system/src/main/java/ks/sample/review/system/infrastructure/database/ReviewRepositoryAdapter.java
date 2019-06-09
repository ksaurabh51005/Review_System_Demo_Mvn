package ks.sample.review.system.infrastructure.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import ks.sample.review.system.domain.Review;
import ks.sample.review.system.domain.ReviewId;
import ks.sample.review.system.domain.ReviewRepository;
import ks.sample.review.system.domain.ReviewStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter that protects the domain layer from Spring Data specific implementations.
 */
@Component
@AllArgsConstructor
public class ReviewRepositoryAdapter implements ReviewRepository {

    private final ReviewSpringDataRepository springDataRepository;
    private final ReviewEntityMapper reviewEntityMapper;

    @Override
    public void save(Review review) {
        ReviewEntity reviewEntity = reviewEntityMapper.fromDomain(review);
        springDataRepository.save(reviewEntity);
    }

    @Override
    public List<Review> findAllByStatus(ReviewStatus reviewStatus) {
        List<ReviewEntity> reviewEntities = springDataRepository.findAllByReviewStatus(reviewStatus);
        return reviewEntities
                .stream()
                .map(reviewEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Review find(ReviewId reviewId) {
        Optional<ReviewEntity> reviewEntity =  springDataRepository.findById(reviewId.getReviewId());
        return reviewEntityMapper.toDomain(reviewEntity.get());
    }
}
