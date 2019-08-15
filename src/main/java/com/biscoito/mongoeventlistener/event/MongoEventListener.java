package com.biscoito.mongoeventlistener.event;

import com.biscoito.mongoeventlistener.config.BigDataKafkaConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.MongoMappingEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MongoEventListener extends AbstractMongoEventListener<Object> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final BigDataKafkaConfiguration bigDataKafkaConfiguration;

    @Override
    public void onBeforeSave(BeforeSaveEvent<Object> event) {
        super.onBeforeSave(event);
        if (bigDataKafkaConfiguration.getEvent() == BigDataKafkaConfiguration.Event.ON_BEFORE_SAVE) {
            publishMessage(event);
        }
    }

    @Override
    public void onAfterSave(final AfterSaveEvent<Object> event) {
        super.onAfterSave(event);
        if (bigDataKafkaConfiguration.getEvent() == BigDataKafkaConfiguration.Event.ON_AFTER_SAVE) {
            publishMessage(event);
        }
    }

    private void publishMessage(MongoMappingEvent<Object> event) {
        final String payload = serialize(event.getSource());
        kafkaTemplate.send(bigDataKafkaConfiguration.getTopicName(), event.getCollectionName(), payload);
    }

    private String serialize(final Object source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
