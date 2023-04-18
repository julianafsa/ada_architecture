package br.com.ada.stickers.service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StickerErrorProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String CREATE_STEAKER = "ALBUM_CREATE_ERROR";

    public void send(String message){
        log.info("Mensagem enviada: {" + message + "}");
        ObjectMapper mapper = new ObjectMapper();

        String value = message;
        String topicName = "TOPIC_ALBUM_CREATE_ERROR";

        kafkaTemplate.send(topicName, CREATE_STEAKER,value);

    }
}
