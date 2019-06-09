package ks.sample.statistic.system.ports.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ks.sample.statistic.system.domain.review.ReviewStatus;
import ks.sample.statistic.system.domain.statistic.StatisticRepository;

@RestController
@RequestMapping("/statistics")
@AllArgsConstructor
public class StatisticsController {

    private StatisticRepository reviewRepository;

    @RequestMapping(path = "/approved", method = RequestMethod.GET)
    public int getNumberOfApprovedReviews() {
        return reviewRepository.countByReviewStatus(ReviewStatus.APPROVED);
    }

    @RequestMapping(path = "/rejected", method = RequestMethod.GET)
    public int getNumberOfRejectedReviews() {
        return reviewRepository.countByReviewStatus(ReviewStatus.REJECTED);
    }

    @RequestMapping(path = "/submitted", method = RequestMethod.GET)
    public int getNumberOfSubmittedReviews() {
        return reviewRepository.countByReviewStatus(ReviewStatus.OPEN);
    }
}