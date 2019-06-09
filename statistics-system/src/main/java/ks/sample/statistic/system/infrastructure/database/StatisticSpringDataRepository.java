package ks.sample.statistic.system.infrastructure.database;

import org.springframework.data.repository.CrudRepository;

import ks.sample.statistic.system.domain.review.ReviewStatus;

/**
 * Spring data repository.
 *
 * @see "http://docs.spring.io/spring-data/jpa/docs/current/reference/html"
 */
public interface StatisticSpringDataRepository extends CrudRepository<StatisticEntity, String> {

    int countByReviewStatus(ReviewStatus reviewStatus);
}