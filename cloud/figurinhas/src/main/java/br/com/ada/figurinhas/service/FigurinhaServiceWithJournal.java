package br.com.ada.figurinhas.service;

import br.com.ada.figurinhas.model.dto.FigurinhaBuyFromAlbumDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaBuyPacoteDTO;
import br.com.ada.figurinhas.model.entity.Figurinha;

import java.util.List;

public interface FigurinhaServiceWithJournal {
    List<Figurinha> buyFigurinhaPacote(FigurinhaBuyPacoteDTO figurinhaBuyPacoteDTO);
    Figurinha buyFigurinhaFromAlbum(FigurinhaBuyFromAlbumDTO figurinhaBuyFromAlbumDTO);
}
