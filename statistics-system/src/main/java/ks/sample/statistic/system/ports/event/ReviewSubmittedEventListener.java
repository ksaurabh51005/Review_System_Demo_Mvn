package ks.sample.statistic.system.ports.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import ks.sample.statistic.system.application.ReviewStatisticsService;
import ks.sample.statistic.system.domain.review.ReviewStatus;
import ks.sample.statistic.system.domain.review.ReviewSubmittedEvent;

@Component
@AllArgsConstructor
@Slf4j
public class ReviewSubmittedEventListener {

    private ReviewStatisticsService reviewStatisticsService;

    @JmsListener(
            destination = "Consumer.statistics_system.VirtualTopic.Events",
            selector = "_type = 'ReviewSubmittedEvent'"
    )
    public void onEvent(ReviewSubmittedEvent event) {
        reviewStatisticsService.reviewWasProcessed(ReviewStatus.OPEN);
        log.info("Review has been submitted. [reviewId={}]", event.getReviewId());
    }
}