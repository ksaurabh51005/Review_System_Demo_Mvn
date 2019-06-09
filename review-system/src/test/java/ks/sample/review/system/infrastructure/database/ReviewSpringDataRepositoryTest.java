package ks.sample.review.system.infrastructure.database;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ks.sample.review.system.domain.ReviewStatus;
import ks.sample.review.system.infrastructure.database.PersistenceConfig;
import ks.sample.review.system.infrastructure.database.ReviewEntity;
import ks.sample.review.system.infrastructure.database.ReviewSpringDataRepository;

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
public class ReviewSpringDataRepositoryTest {

    @Autowired
    private ReviewSpringDataRepository springDataRepository;

    @Test
    public void should_SaveAuditingColumns() {
        ReviewEntity entity = new ReviewEntity();
        entity.setReviewId("MY_ID");
        entity.setReviewStatus(ReviewStatus.REJECTED);

        springDataRepository.save(entity);

        Optional<ReviewEntity> loaded = springDataRepository.findById("MY_ID");

        assertThat(loaded.get().getCreatedDate()).isNotNull();
        assertThat(loaded.get().getLastModifiedDate()).isNotNull();
    }
}