package br.com.ada.albuns.service.consumer;

import br.com.ada.albuns.service.AlbumService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlbumErrorConsumer {
    private final String topicName = "TOPIC_ALBUM_CREATE_ERROR";


    @Qualifier("AlbumServiceWithJournal")
    @Autowired
    private AlbumService service;



    @KafkaListener(topics = "TOPIC_ALBUM_CREATE_ERROR", groupId = "group_id" )
    public void consume(ConsumerRecord<String, String> message){
        log.info("Tópico: {}", topicName);
        log.info("key: {}", message.key());
        log.info("Headers: {}", message.headers());
        log.info("Partion: {}", message.partition());
        log.info("Order: {}", message.value());

        service.delete(message.value());

    }

}
