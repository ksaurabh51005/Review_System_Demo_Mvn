package ks.sample.statistic.system.ports.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import ks.sample.statistic.system.application.ReviewStatisticsService;
import ks.sample.statistic.system.domain.review.ReviewRejectedEvent;
import ks.sample.statistic.system.domain.review.ReviewStatus;

@Component
@AllArgsConstructor
@Slf4j
public class ReviewRejectedEventListener {

    private ReviewStatisticsService reviewStatisticsService;

    @JmsListener(
            destination = "Consumer.statistics_system.VirtualTopic.Events",
            selector = "_type = 'ReviewRejectedEvent'"
    )
    public void onEvent(ReviewRejectedEvent event) {
        reviewStatisticsService.reviewWasProcessed(ReviewStatus.REJECTED);
        log.info("Review has been rejected. [reviewId={}]", event.getReviewId());
    }
}