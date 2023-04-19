package br.com.ada.stickers.strategy.impl;

import br.com.ada.stickers.exceptions.NumeroInsuficienteDeFigurinhaException;
import br.com.ada.stickers.model.entity.Figurinha;
import br.com.ada.stickers.strategy.PacoteDeFigurinhaStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RandomPacoteDeFigurinhaStrategyImpl implements PacoteDeFigurinhaStrategy {

    @Override
    public List<Figurinha> createStickerPack(List<Figurinha> figurinhas, Integer size) {
        if (Objects.isNull(figurinhas) || figurinhas.isEmpty() || Objects.isNull(size)) {
            log.error("Não há figurinhas para serem vendidas na banca.");
            throw new RuntimeException();
        }
        if (figurinhas.size() < size) {
            log.error("Não há figurinhas suficientes para serem vendidas em um pacote.");
            throw new NumeroInsuficienteDeFigurinhaException();
        }
        final Set<Integer> generatedPositions = new LinkedHashSet<>();
        final Random generator = new Random();
        final Integer max = figurinhas.size() - 1;
        while (generatedPositions.size() < size) {
            final Integer position = generator.nextInt(max);
            generatedPositions.add(position);
        }
        return generatedPositions
                .stream()
                .map(position -> figurinhas.get(position))
                .collect(Collectors.toList());
    }
}
