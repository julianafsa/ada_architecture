package br.com.ada.figurinhas.service.consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.util.ReflectionTestUtils;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactFolder;
import au.com.dius.pact.core.model.messaging.Message;
import au.com.dius.pact.core.model.messaging.MessagePact;
import br.com.ada.figurinhas.service.FigurinhaService;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "albuns", providerType = ProviderType.ASYNCH)
@PactFolder("../../pacts")
public class FigurinhaConsumerPactTest {
	private FigurinhaConsumer consumer;
	private FigurinhaService service;
	
	@Pact(consumer = "figurinhas")
	MessagePact criarFigurinhasPact(MessagePactBuilder builder) {
		PactDslJsonBody body = new PactDslJsonBody();
		body.stringType("albumId", "AlbumId");
		body.stringType("albumPrototipoId", "AlbumPrototipoId");
		body.stringType("padraoAlbumId", "PadraoAlbumId");
		
		return builder
				.expectsToReceive("Criacao de figurinhas")
				.withMetadata(Map.of("contentType", "application/json"))
				.withContent(body)
				.toPact();
				
	}
	
	@BeforeEach
	public void setUp() {
		this.consumer = new FigurinhaConsumer();
		this.service = mock(FigurinhaService.class);
		ReflectionTestUtils.setField(this.consumer, "figurinhaService", service);
	}

	@Test
	@PactTestFor(pactMethod = "criarFigurinhasPact", providerType = ProviderType.ASYNCH)
	public void test(List<Message> messages) {
		byte[] kafkaBytes = messages.get(0).contentsAsBytes();
		ConsumerRecord<String, String> record = new ConsumerRecord<String, String>("", 0, 0L, "key", new String(kafkaBytes));
		
		assertDoesNotThrow(() -> {
			consumer.consume(record);
		});
	}
}
