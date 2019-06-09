package ks.sample.common.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This class marks a domain entity. A domain entity is a main object
 * in our domain which is identified by its unique ID. This means that
 * is has an identity. It changes its state based on business logic
 * and publishes domain events.
 */
public interface DomainEntity<T extends DomainEntityId> {

    List<DomainEvent> occurredEvents = new ArrayList<>();

    /**
     * Returns the unique ID of the domain entity. Two domain entities
     * with the same ID are considered to be equal.
     *
     * @return The ID of the domain entity
     */
    T getId();

    default List<DomainEvent> getOccurredEvents() {
        List<DomainEvent> copy = new ArrayList<>(occurredEvents);
        occurredEvents.clear();
        return copy;
    }

    default void raise(DomainEvent domainEvent) {
        occurredEvents.add(domainEvent);
    }
}
