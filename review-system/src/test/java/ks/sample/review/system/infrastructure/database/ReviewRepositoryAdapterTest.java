package ks.sample.review.system.infrastructure.database;

import ks.sample.review.system.domain.Review;
import ks.sample.review.system.domain.ReviewFixtures;
import ks.sample.review.system.domain.ReviewStatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        PersistenceConfig.class,
        ReviewRepositoryAdapter.class,
        ReviewEntityMapper.class
})
@TestPropertySource({
        "classpath:application.properties"
})
@Transactional
public class ReviewRepositoryAdapterTest {

    @Autowired
    private ReviewRepositoryAdapter reviewRepositoryAdapter;

    @Test
    public void should_SaveReview() {
        Review review = ReviewFixtures.anInitialFiveStarSmartphoneReview();
        reviewRepositoryAdapter.save(review);
        Review loaded = reviewRepositoryAdapter.find(review.getReviewId());
        assertThat(loaded).isEqualTo(loaded);
    }

    @Test
    public void should_FindReviews_ByStatus() {
        Review review = ReviewFixtures.anInitialFiveStarSmartphoneReview();
        reviewRepositoryAdapter.save(review);
        List<Review> loaded = reviewRepositoryAdapter.findAllByStatus(ReviewStatus.OPEN);
        assertThat(loaded).hasSize(1);
    }

    @Test
    public void should_ReturnEmptyList_IfNoReviewsFound_ByStatus() {
        Review review = ReviewFixtures.anInitialFiveStarSmartphoneReview();
        reviewRepositoryAdapter.save(review);
        List<Review> loaded = reviewRepositoryAdapter.findAllByStatus(ReviewStatus.APPROVED);
        assertThat(loaded).hasSize(0);
    }
}