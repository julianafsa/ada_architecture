package br.com.ada.stickers.service.consumer;

import br.com.ada.stickers.model.dto.CreateStickerMessage;
import br.com.ada.stickers.service.StickerService;
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
public class StickerConsumer {
    private final String topicName = "TOPIC_CREATE_STICKERS";

    @Autowired
    private StickerService stickerService;

    @KafkaListener(topics = "TOPIC_CREATE_STICKERS", groupId = "group_id" )
    public void consume(ConsumerRecord<String, String> payload){
        log.info("Tópico: {}", topicName);
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("Order: {}", payload.value());

        stickerService.createStickersForAlbum(convertToModel(payload));

    }


    private CreateStickerMessage convertToModel(ConsumerRecord<String, String> payload) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(payload.value(), CreateStickerMessage.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Error to convert data to model");
    }
}
