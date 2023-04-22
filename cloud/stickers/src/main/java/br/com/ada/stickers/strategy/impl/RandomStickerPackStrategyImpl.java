package br.com.ada.stickers.strategy.impl;

import br.com.ada.stickers.exceptions.InsufficientNumberOfStickersException;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.strategy.StickerPackStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RandomStickerPackStrategyImpl implements StickerPackStrategy {

    @Override
    public List<Sticker> createStickerPack(List<Sticker> stickers, Integer size) {
        if (Objects.isNull(stickers) || stickers.isEmpty() || Objects.isNull(size)) {
            log.error("Não há figurinhas para serem vendidas na banca.");
            throw new RuntimeException();
        }
        if (stickers.size() < size) {
            log.error("Não há figurinhas suficientes para serem vendidas em um pacote.");
            throw new InsufficientNumberOfStickersException();
        }
        final Set<Integer> generatedPositions = new LinkedHashSet<>();
        final Random generator = new Random();
        final Integer max = stickers.size() - 1;
        while (generatedPositions.size() < size) {
            final Integer position = generator.nextInt(max);
            generatedPositions.add(position);
        }
        return generatedPositions
                .stream()
                .map(position -> stickers.get(position))
                .collect(Collectors.toList());
    }
}
