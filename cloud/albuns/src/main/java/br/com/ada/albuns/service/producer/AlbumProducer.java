package br.com.ada.albuns.service.producer;

import br.com.ada.albuns.model.dto.CreateFigurinhaMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaPrototipo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumProducer {

    private final KafkaPrototipo<String, String> kafkaPrototipo;
    private static final String CREATE_STEAKER = "CREATE_STEAKER";

    public void send(CreateFigurinhaMessage message){
        try{
            log.info("Mensagem enviada: {" + message + "}");
            ObjectMapper mapper = new ObjectMapper();

            String value = mapper.writeValueAsString(message);
            String topicName = "TOPIC_CREATE_STICKERS";

            kafkaPrototipo.send(topicName, CREATE_STEAKER,value);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
