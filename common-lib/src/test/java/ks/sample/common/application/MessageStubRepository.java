package ks.sample.common.application;

import java.util.HashSet;
import java.util.Set;

import ks.sample.common.application.Message;
import ks.sample.common.application.MessageRepository;

public class MessageStubRepository implements MessageRepository {

    private Set<Message> messages = new HashSet<>();

    @Override
    public Message findUnprocessedMessages(String listenerName) {
        return messages
                .stream()
                .filter(message -> message.getListener().equals(listenerName))
                .filter(message -> !message.isSuccess())
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Message message) {
        messages.add(message);
    }
}
