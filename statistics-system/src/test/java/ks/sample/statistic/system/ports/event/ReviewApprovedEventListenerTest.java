package ks.sample.statistic.system.ports.event;

import ks.sample.statistic.system.JmsUtil;
import ks.sample.statistic.system.application.ReviewStatisticsService;
import ks.sample.statistic.system.domain.review.ReviewApprovedEvent;
import ks.sample.statistic.system.domain.review.ReviewStatus;
import ks.sample.statistic.system.domain.statistic.StatisticRepository;
import ks.sample.statistic.system.infrastructure.jms.JmsConfig;
import ks.sample.statistic.system.infrastructure.memory.StatisticStubRepository;
import ks.sample.statistic.system.ports.event.ReviewApprovedEventListener;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        JmsConfig.class,
        ReviewApprovedEventListener.class,
        ReviewStatisticsService.class,
        StatisticStubRepository.class
})
@TestPropertySource({
        "classpath:application.properties"
})
public class ReviewApprovedEventListenerTest {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void should_SaveNewStatistic() {

        jmsTemplate.convertAndSend(new ActiveMQQueue("Consumer.statistics_system.VirtualTopic.Events"), new ReviewApprovedEvent());

        JmsUtil jmsUtil = new JmsUtil(jmsTemplate);
        jmsUtil.waitForAll("Consumer.statistics_system.VirtualTopic.Events");

        int statistic = statisticRepository.countByReviewStatus(ReviewStatus.APPROVED);
        assertThat(statistic).isEqualTo(1);
    }
}