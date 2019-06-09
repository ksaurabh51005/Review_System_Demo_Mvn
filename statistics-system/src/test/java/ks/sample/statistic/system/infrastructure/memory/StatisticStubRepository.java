package ks.sample.statistic.system.infrastructure.memory;

import java.util.HashSet;
import java.util.Set;

import ks.sample.statistic.system.domain.review.ReviewStatus;
import ks.sample.statistic.system.domain.statistic.Statistic;
import ks.sample.statistic.system.domain.statistic.StatisticRepository;

public class StatisticStubRepository implements StatisticRepository {

    private Set<Statistic> reviews = new HashSet<>();

    @Override
    public void save(Statistic review) {
        reviews.add(review);
    }

    @Override
    public int countByReviewStatus(ReviewStatus reviewStatus) {
        return (int) reviews
                        .stream()
                        .filter(review -> review.getReviewStatus() == reviewStatus)
                        .count();
    }
}