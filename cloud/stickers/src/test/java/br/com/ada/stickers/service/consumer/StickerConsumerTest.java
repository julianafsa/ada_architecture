package br.com.ada.stickers.service.consumer;

import org.junit.jupiter.api.extension.ExtendWith;
import au.com.dius.pact.consumer.junit5.PactTestFor;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "album_producer", providerType = ProviderType.ASYNCH)
public class StickerConsumerTest {

}
