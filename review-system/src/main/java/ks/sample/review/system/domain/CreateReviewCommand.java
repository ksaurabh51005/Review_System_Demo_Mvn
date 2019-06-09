package ks.sample.review.system.domain;

import ks.sample.common.domain.Command;
import lombok.Data;

@Data
public class CreateReviewCommand implements Command {
    private String subject;
    private String content;
    private int rating;
}
