package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.TransacaoFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.TransacaoFigurinhaDTO;
import br.com.ada.stickers.model.entity.Figurinha;
import br.com.ada.stickers.model.entity.TransacaoFigurinha;
import br.com.ada.stickers.model.mapper.TransacaoFigurinhaMapper;
import br.com.ada.stickers.model.mapper.FigurinhaMapper;
import br.com.ada.stickers.repository.TransacaoFigurinhaRepository;
import br.com.ada.stickers.service.TransacaoFigurinhaService;
import br.com.ada.stickers.service.FigurinhaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TransacaoFigurinhaServiceImpl implements TransacaoFigurinhaService {

    private final TransacaoFigurinhaRepository repository;
    private final TransacaoFigurinhaMapper mapper;
    private final FigurinhaService figurinhaService;
    private final FigurinhaMapper figurinhaMapper;
    //private final Redis jedis;
    public TransacaoFigurinhaServiceImpl(final TransacaoFigurinhaRepository repository, final TransacaoFigurinhaMapper mapper, final FigurinhaService figurinhaService, final FigurinhaMapper figurinhaMapper) {
        //final Redis jedis) {
        this.repository = repository;
        this.mapper = mapper;
        this.figurinhaService = figurinhaService;
        this.figurinhaMapper = figurinhaMapper;
        //this.jedis = jedis;
    }
    
    @Override
    public List<TransacaoFigurinhaDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public TransacaoFigurinhaDTO findById(final String id) {
        Optional<TransacaoFigurinha> optional = repository.findById(id);
        if (optional.isPresent()) {
            final TransacaoFigurinha entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public TransacaoFigurinhaDTO create(final TransacaoFigurinhaCreationDTO creationDTO) {
        TransacaoFigurinha entity = mapper.parseEntity(creationDTO);
        Figurinha figurinhaEntity = figurinhaMapper.parseEntity(
                figurinhaService.findById(creationDTO.getSticker().getId()));
        entity.setId(null);
        entity.setSticker(figurinhaEntity);
        entity = repository.save(entity);
//        this.jedis.save(entity.getSourceAlbumId(), entity.getPrice());
        return mapper.parseDTO(entity);
    }

}
