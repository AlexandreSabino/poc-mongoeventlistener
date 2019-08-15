package com.biscoito.mongoeventlistener.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "big-data")
@Component
public class BigDataKafkaConfiguration {

    private String topicName;

    private Event event;

    public enum Event {
        ON_BEFORE_SAVE,
        ON_AFTER_SAVE
    }
}
