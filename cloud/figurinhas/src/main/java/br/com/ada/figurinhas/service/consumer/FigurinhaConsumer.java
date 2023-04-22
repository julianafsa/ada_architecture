package br.com.ada.figurinhas.service.consumer;

import br.com.ada.figurinhas.model.dto.CreateFigurinhaMessage;
import br.com.ada.figurinhas.service.FigurinhaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FigurinhaConsumer {
    private final String topicName = "TOPIC_CREATE_STICKERS";

    @Autowired
    private FigurinhaService figurinhaService;

    @KafkaListener(topics = "TOPIC_CREATE_STICKERS", groupId = "group_id" )
    public void consume(ConsumerRecord<String, String> message){
        log.info("Tópico: {}", topicName);
        log.info("key: {}", message.key());
        log.info("Headers: {}", message.headers());
        log.info("Partion: {}", message.partition());
        log.info("Order: {}", message.value());

        figurinhaService.createFigurinhasForAlbum(convertToModel(message));

    }


    private CreateFigurinhaMessage convertToModel(ConsumerRecord<String, String> payload) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(payload.value(), CreateFigurinhaMessage.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Error to convert data to model");
    }
}
