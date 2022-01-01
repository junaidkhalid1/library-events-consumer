package com.learnkafka.library.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.library.entity.LibraryEvent;
import com.learnkafka.library.jpa.LibraryEventsRepository;
import com.learnkafka.library.service.LibraryEventsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultLibraryEventsService implements LibraryEventsService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LibraryEventsRepository libraryEventsRepository;

    @Override
    public void processLibraryEvent(final ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        final LibraryEvent libraryEvent = objectMapper.readValue(consumerRecord.value(), LibraryEvent.class);
        log.info("libraryEvent : {} ",libraryEvent);

        switch (libraryEvent.getLibraryEventType()) {

            case NEW:
                save(libraryEvent);
                break;
            case UPDATE:
                break;
            default:
                log.info("Invalid Library Event Type");
                break;
        }
    }

    private void save(final LibraryEvent libraryEvent) {
        libraryEvent.getBook().setLibraryEvent(libraryEvent);
        libraryEventsRepository.save(libraryEvent);
        log.info("Successfully Persisted the libraryEvent {} ",libraryEvent);
    }
}
