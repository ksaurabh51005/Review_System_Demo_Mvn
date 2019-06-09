package ks.sample.statistic.system.infrastructure.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ks.sample.statistic.system.domain.review.ReviewStatus;
import ks.sample.statistic.system.infrastructure.database.PersistenceConfig;
import ks.sample.statistic.system.infrastructure.database.StatisticEntity;
import ks.sample.statistic.system.infrastructure.database.StatisticSpringDataRepository;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        PersistenceConfig.class
})
@TestPropertySource({
        "classpath:application.properties"
})
@Transactional
public class StatisticSpringDataRepositoryTest {

    @Autowired
    private StatisticSpringDataRepository springDataRepository;

    @Test
    public void should_SaveAuditingColumns() {
        StatisticEntity entity = new StatisticEntity();
        entity.setStatisticId("MY_ID");
        entity.setReviewStatus(ReviewStatus.REJECTED);

        springDataRepository.save(entity);

        Optional<StatisticEntity> loaded = springDataRepository.findById("MY_ID");

        assertThat(loaded.get().getCreatedDate()).isNotNull();
        assertThat(loaded.get().getLastModifiedDate()).isNotNull();
    }
}