package ks.sample.statistic.system.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ks.sample.statistic.system.domain.review.ReviewStatus;
import ks.sample.statistic.system.domain.statistic.Statistic;
import ks.sample.statistic.system.domain.statistic.StatisticRepository;

@Service
@AllArgsConstructor
public class ReviewStatisticsService {

    private StatisticRepository statisticRepository;

    public void reviewWasProcessed(ReviewStatus reviewStatus) {
        Statistic statistic = new Statistic(reviewStatus);
        statisticRepository.save(statistic);
    }
}
