package ks.sample.review.system.domain;

import ks.sample.common.domain.DomainEvent;
import lombok.Data;

@Data
public class ReviewRejectedEvent implements DomainEvent {
    private String reviewId;
    private String reason;
}