package ks.sample.review.system.infrastructure.database;

import org.springframework.data.repository.CrudRepository;

import ks.sample.review.system.domain.ReviewStatus;

import java.util.List;

/**
 * Spring data repository.
 *
 * @see "http://docs.spring.io/spring-data/jpa/docs/current/reference/html"
 */
public interface ReviewSpringDataRepository extends CrudRepository<ReviewEntity, String> {

    List<ReviewEntity> findAllByReviewStatus(ReviewStatus reviewStatus);
}