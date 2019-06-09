package ks.sample.checking.system.domain;

import ks.sample.common.domain.DomainEvent;
import lombok.Data;

@Data
public class ReviewSubmittedEvent implements DomainEvent {
    private String reviewId;
    private String subject;
    private String content;
}