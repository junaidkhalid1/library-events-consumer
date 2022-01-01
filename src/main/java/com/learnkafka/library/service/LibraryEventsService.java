package com.learnkafka.library.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface LibraryEventsService {

    void processLibraryEvent(ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException;
}
