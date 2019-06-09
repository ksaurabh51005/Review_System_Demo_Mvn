package ks.sample.statistic.system.infrastructure.database;

import org.springframework.stereotype.Service;

import ks.sample.statistic.system.domain.statistic.Statistic;
import ks.sample.statistic.system.domain.statistic.StatisticId;

@Service
public class StatisticEntityMapper {

    public Statistic toDomain(StatisticEntity statisticEntity) {

        if (statisticEntity == null) {
            return null;
        }

        return Statistic
                .builder()
                .statisticId(new StatisticId(statisticEntity.getStatisticId()))
                .reviewStatus(statisticEntity.getReviewStatus())
                .build();
    }

    public StatisticEntity fromDomain(Statistic statistic) {

        if (statistic == null) {
            return null;
        }

        StatisticEntity statisticEntity = new StatisticEntity();
        statisticEntity.setStatisticId(statistic.getStatisticId().getStatisticId());
        statisticEntity.setReviewStatus(statistic.getReviewStatus());

        return statisticEntity;
    }
}
