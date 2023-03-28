package br.com.ada.stickers.strategy.impl;

import br.com.ada.stickers.exceptions.InsufficientNumberOfStickers;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.strategy.StickerPackStrategy;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RandomStickerPackStrategyImpl implements StickerPackStrategy {

    @Override
    public List<Sticker> createStickerPack(List<Sticker> stickers, Integer size) {
        if (Objects.isNull(stickers) || stickers.isEmpty() || Objects.isNull(size)) {
            throw new RuntimeException();
        }
        if (stickers.size() < size) {
            throw new InsufficientNumberOfStickers();
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
