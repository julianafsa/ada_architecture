package br.com.ada.figurinhas.strategy;

import br.com.ada.figurinhas.model.entity.Figurinha;

import java.util.List;

public interface FigurinhaPacoteStrategy {
    List<Figurinha> createFigurinhaPacote(List<Figurinha> figurinhas, Integer size);
}
