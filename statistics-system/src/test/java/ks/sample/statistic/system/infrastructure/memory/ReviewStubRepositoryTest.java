package ks.sample.statistic.system.infrastructure.memory;

import ks.sample.statistic.system.domain.StatisticFixtures;
import ks.sample.statistic.system.domain.review.ReviewStatus;
import ks.sample.statistic.system.domain.statistic.Statistic;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ReviewStubRepositoryTest {

    @Test
    public void should_SaveReview() throws Exception {
        StatisticStubRepository memoryReviewRepository = new StatisticStubRepository();
        Statistic statistic = StatisticFixtures.anApprovedStatistic();
        memoryReviewRepository.save(statistic);

        assertThat(memoryReviewRepository.countByReviewStatus(ReviewStatus.APPROVED)).isEqualTo(1);
    }
}