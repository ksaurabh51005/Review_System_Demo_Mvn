package ks.sample.statistic.system.domain.statistic;

import ks.sample.statistic.system.domain.review.ReviewStatus;

public interface StatisticRepository {
    void save(Statistic review);
    int countByReviewStatus(ReviewStatus open);
}
