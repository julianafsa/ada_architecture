package br.com.ada.stickers.service;

import br.com.ada.stickers.model.dto.TransacaoFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.TransacaoFigurinhaDTO;

import java.util.List;
public interface TransacaoFigurinhaService {
    List<TransacaoFigurinhaDTO> findAll();
    TransacaoFigurinhaDTO findById(String id);
    TransacaoFigurinhaDTO create(TransacaoFigurinhaCreationDTO creationDTO);
}
