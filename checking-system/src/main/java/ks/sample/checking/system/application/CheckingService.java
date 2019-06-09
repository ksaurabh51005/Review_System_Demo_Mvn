package ks.sample.checking.system.application;

import ks.sample.common.application.EventPublisher;
import ks.sample.checking.system.domain.Check;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckingService {

    private EventPublisher eventPublisher;

    public void checkReview(Check check) {
        check.check();
        eventPublisher.publish(check.getOccurredEvents());
    }
}