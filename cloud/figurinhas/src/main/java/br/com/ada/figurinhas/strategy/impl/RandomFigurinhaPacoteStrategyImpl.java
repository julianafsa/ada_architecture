package br.com.ada.figurinhas.strategy.impl;

import br.com.ada.figurinhas.exceptions.InsufficientNumberOfFigurinhasException;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.strategy.FigurinhaPacoteStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RandomFigurinhaPacoteStrategyImpl implements FigurinhaPacoteStrategy {

    @Override
    public List<Figurinha> createFigurinhaPacote(List<Figurinha> figurinhas, Integer size) {
        if (Objects.isNull(figurinhas) || figurinhas.isEmpty() || Objects.isNull(size)) {
            log.error("Não há figurinhas para serem vendidas na banca.");
            throw new RuntimeException();
        }
        if (figurinhas.size() < size) {
            log.error("Não há figurinhas suficientes para serem vendidas em um pacote.");
            throw new InsufficientNumberOfFigurinhasException();
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
