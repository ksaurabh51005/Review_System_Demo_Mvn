package ks.sample.review.system.infrastructure.messaging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ks.sample.review.system.infrastructure.database.MessageSpringDataRepository;
import ks.sample.review.system.infrastructure.database.PersistenceConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        PersistenceConfig.class
})
@TestPropertySource({
        "classpath:application.properties"
})

public class MessageSpringDataRepositoryTest {

    @Autowired
    private MessageSpringDataRepository messageSpringDataRepository;

    @Test
    public void name() {


    }
}