package br.com.ada.albuns.service.producer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestPrototipo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.kafka.core.KafkaPrototipo;

import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import br.com.ada.albuns.model.dto.CreateFigurinhaMessage;

@Provider("albuns")
@Consumer("figurinhas")
@PactFolder("../../pacts")
public class AlbumProducerPactTests {
	private AlbumProducer producer;
	private KafkaPrototipo<String, String> kafkaPrototipo;

	@BeforeEach
	public void before(PactVerificationContext context) {
		context.setTarget(new MessageTestTarget());
		
		kafkaPrototipo = (KafkaPrototipo<String, String>)mock(KafkaPrototipo.class);
		producer = new AlbumProducer(kafkaPrototipo);
	}
	
	@TestPrototipo
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	public void pactVerificationTestPrototipo(PactVerificationContext context) {
		context.verifyInteraction();
	}
	
	@PactVerifyProvider("Criacao de figurinhas")
	public MessageAndMetadata verifySimpleMessageEvent() {
		Map<String, Object> metadata = Map.of("contentType", "application/json");
		CreateFigurinhaMessage createFigurinhaMessage = new CreateFigurinhaMessage("AlbumId", "AlbumPrototipoId", "PadraoAlbumId");
		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
		
		producer.send(createFigurinhaMessage);
		
		verify(kafkaPrototipo).send(any(), any(), stringCaptor.capture());
		
		return new MessageAndMetadata(stringCaptor.getValue().getBytes(), metadata);
	}
}
