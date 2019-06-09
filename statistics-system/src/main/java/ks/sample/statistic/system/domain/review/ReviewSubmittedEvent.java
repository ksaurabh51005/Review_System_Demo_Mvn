package ks.sample.statistic.system.domain.review;

import ks.sample.common.domain.DomainEvent;
import lombok.Data;

@Data
public class ReviewSubmittedEvent implements DomainEvent {
    private String reviewId;
}