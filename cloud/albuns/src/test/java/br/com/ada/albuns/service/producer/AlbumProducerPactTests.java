package br.com.ada.albuns.service.producer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaTemplate;

import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import br.com.ada.albuns.model.dto.CreateStickerMessage;

@Provider("albuns")
@Consumer("figurinhas")
@PactFolder("../../pacts")
public class AlbumProducerPactTests {
	private AlbumProducer producer;
	private KafkaTemplate<String, String> kafkaTemplate;

	@BeforeEach
	public void before(PactVerificationContext context) {
		context.setTarget(new MessageTestTarget());
		
		kafkaTemplate = (KafkaTemplate<String, String>)mock(KafkaTemplate.class);
		producer = new AlbumProducer(kafkaTemplate);
	}
	
	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	public void pactVerificationTestTemplate(PactVerificationContext context) {
		context.verifyInteraction();
	}
	
	@PactVerifyProvider("Criacao de figurinhas")
	public MessageAndMetadata verifySimpleMessageEvent() {
		Map<String, Object> metadata = Map.of("contentType", "application/json");
		CreateStickerMessage createStickerMessage = new CreateStickerMessage("AlbumId", "AlbumTemplateId", "DefaultAlbumId");
		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
		
		producer.send(createStickerMessage);
		
		verify(kafkaTemplate).send(any(), any(), stringCaptor.capture());
		
		return new MessageAndMetadata(stringCaptor.getValue().getBytes(), metadata);
	}
}
