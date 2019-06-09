package ks.sample.checking.system.ports.event;

import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import ks.sample.checking.system.application.CheckingService;
import ks.sample.checking.system.domain.Check;
import ks.sample.checking.system.domain.ReviewId;
import ks.sample.checking.system.domain.ReviewSubmittedEvent;

@Component
@AllArgsConstructor
public class ReviewSubmittedEventListener {

    private CheckingService checkingService;

    @JmsListener(
            destination = "Consumer.checking_system.VirtualTopic.Events",
            selector = "_type = 'ReviewSubmittedEvent'"
    )
    public void onEvent(ReviewSubmittedEvent reviewSubmittedEvent) {
        Check check = new Check(
                new ReviewId(reviewSubmittedEvent.getReviewId()),
                reviewSubmittedEvent.getSubject(),
                reviewSubmittedEvent.getContent()
        );
        checkingService.checkReview(check);
    }
}
