package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.FigurinhaAVendaDTO;
import br.com.ada.stickers.model.dto.FigurinhaAVendaCreationDTO;
import br.com.ada.stickers.model.dto.FigurinhaAVendaUpdateDTO;
import br.com.ada.stickers.model.entity.Figurinha;
import br.com.ada.stickers.model.entity.FigurinhaAVenda;
import br.com.ada.stickers.model.mapper.FigurinhaAVendaMapper;
import br.com.ada.stickers.model.mapper.FigurinhaMapper;
import br.com.ada.stickers.repository.FigurinhaAVendaRepository;
import br.com.ada.stickers.service.FigurinhaService;
import br.com.ada.stickers.service.FigurinhaAVendaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FigurinhaAVendaServiceImpl implements FigurinhaAVendaService {

    private final FigurinhaAVendaRepository repository;
    private final FigurinhaAVendaMapper mapper;
    private final FigurinhaService figurinhaService;
    private final FigurinhaMapper figurinhaMapper;

    public FigurinhaAVendaServiceImpl(final FigurinhaAVendaRepository repository,
                                      final FigurinhaAVendaMapper mapper,
                                      final FigurinhaService figurinhaService,
                                      final FigurinhaMapper figurinhaMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.figurinhaService = figurinhaService;
        this.figurinhaMapper = figurinhaMapper;
    }
    
    @Override
    public List<FigurinhaAVendaDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public FigurinhaAVenda findById(final String id) {
        Optional<FigurinhaAVenda> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public FigurinhaAVendaDTO create(final FigurinhaAVendaCreationDTO creationDTO) {
        FigurinhaAVenda entity = mapper.parseEntity(creationDTO);
        Figurinha figurinhaEntity = figurinhaMapper.parseEntity(
                figurinhaService.findById(creationDTO.getSticker().getId()));
        entity.setId(null);
        entity.setSticker(figurinhaEntity);
        entity = repository.save(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public FigurinhaAVendaDTO edit(final String id, final FigurinhaAVendaUpdateDTO updateDTO) {
        final Optional<FigurinhaAVenda> optional = repository.findById(id);
        if (optional.isPresent()) {
            FigurinhaAVenda entity = optional.get();
            entity.setId(id);
            entity.setPrice(updateDTO.getPrice());
            entity = repository.save(entity);
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(id);
    }

    @Override
    public void deleteByStickerId(String stickerId) {
        Optional<FigurinhaAVenda> optional = repository.findByStickerId(stickerId);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(optional.get().getId());
    }

    @Override
    public Optional<FigurinhaAVenda> findByStickerId(String stickerId) {
        return repository.findByStickerId(stickerId);
    }
}
