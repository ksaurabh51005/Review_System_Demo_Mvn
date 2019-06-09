package ks.sample.review.system.infrastructure.database;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import ks.sample.common.application.Message;
import ks.sample.common.application.MessageRepository;

/**
 * Adapter that protects the domain layer from Spring Data specific implementations.
 */
@Component
@AllArgsConstructor
public class MessageRepositoryAdapter implements MessageRepository {

    private final MessageSpringDataRepository springDataRepository;
    private final MessageEntityMapper messageEntityMapper;

    @Override
    public Message findUnprocessedMessages(String listenerName) {
        MessageEntity messageEntity =  springDataRepository.findFirstByListenerAndSuccessAndExecutionCountLessThan(listenerName, false, 2);
        return messageEntityMapper.toDomain(messageEntity);
    }

    @Override
    public void save(Message message) {
        MessageEntity messageEntity = messageEntityMapper.fromDomain(message);
        springDataRepository.save(messageEntity);
    }
}