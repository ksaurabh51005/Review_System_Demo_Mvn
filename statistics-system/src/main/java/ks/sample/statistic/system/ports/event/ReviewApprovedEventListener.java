package ks.sample.statistic.system.ports.event;

import ks.sample.statistic.system.application.ReviewStatisticsService;
import ks.sample.statistic.system.domain.review.ReviewApprovedEvent;
import ks.sample.statistic.system.domain.review.ReviewStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ReviewApprovedEventListener {

    private ReviewStatisticsService reviewStatisticsService;

    @JmsListener(
            destination = "Consumer.statistics_system.VirtualTopic.Events",
            selector = "_type = 'ReviewApprovedEvent'"
    )
    public void onEvent(ReviewApprovedEvent event) {
        reviewStatisticsService.reviewWasProcessed(ReviewStatus.APPROVED);
        log.info("Review has been approved. [reviewId={}]", event.getReviewId());
    }
}