package ks.sample.common.utils;

import org.junit.Test;

import ks.sample.common.domain.DomainEvent;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class SubclassFinderTest {

    @Test
    public void should_FindAllEvents() {
        List<Class> subtypes = SubclassFinder.findAllSubtypes(DomainEvent.class, "de/tuhrig/rsd");
        assertThat(subtypes).hasSize(2);
    }
}