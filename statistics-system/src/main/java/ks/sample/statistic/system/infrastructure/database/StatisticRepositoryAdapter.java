package ks.sample.statistic.system.infrastructure.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import ks.sample.statistic.system.domain.review.ReviewStatus;
import ks.sample.statistic.system.domain.statistic.Statistic;
import ks.sample.statistic.system.domain.statistic.StatisticRepository;

/**
 * Adapter that protects the domain layer from Spring Data specific implementations.
 */
@Component
@AllArgsConstructor
public class StatisticRepositoryAdapter implements StatisticRepository {

    private final StatisticSpringDataRepository springDataRepository;
    private final StatisticEntityMapper statisticEntityMapper;

    @Override
    public void save(Statistic review) {
        StatisticEntity statisticEntity = statisticEntityMapper.fromDomain(review);
        springDataRepository.save(statisticEntity);
    }

    @Override
    public int countByReviewStatus(ReviewStatus reviewStatus) {
        return springDataRepository.countByReviewStatus(reviewStatus);
    }
}
