package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.StickerDTO;
import br.com.ada.stickers.model.dto.StickerToSellCreationDTO;
import br.com.ada.stickers.model.dto.StickerToSellDTO;
import br.com.ada.stickers.model.dto.StickerToSellUpdateDTO;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.model.entity.StickerToSell;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.model.mapper.StickerToSellMapper;
import br.com.ada.stickers.repository.StickerToSellRepository;
import br.com.ada.stickers.service.StickerService;
import br.com.ada.stickers.service.StickerToSellService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StickerToSellServiceImpl implements StickerToSellService {

    private final StickerToSellRepository repository;
    private final StickerToSellMapper mapper;
    private final StickerService stickerService;
    private final StickerMapper stickerMapper;

    public StickerToSellServiceImpl(final StickerToSellRepository repository,
                                    final StickerToSellMapper mapper,
                                    final StickerService stickerService,
                                    final StickerMapper stickerMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.stickerService = stickerService;
        this.stickerMapper = stickerMapper;
    }
    
    @Override
    public List<StickerToSellDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public StickerToSell findById(final String id) {
        Optional<StickerToSell> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public StickerToSellDTO create(final StickerToSellCreationDTO creationDTO) {
        final String stickerId = creationDTO.getSticker().getId();
        final StickerDTO stickerDTO = stickerService.findById(stickerId);
        Sticker stickerEntity = stickerMapper.parseEntity(stickerDTO);
        StickerToSell entity = mapper.parseEntity(creationDTO);
        entity.setId(null);
        entity.setSticker(stickerEntity);
        entity = repository.save(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public StickerToSellDTO edit(final String id, final StickerToSellUpdateDTO updateDTO) {
        final Optional<StickerToSell> optional = repository.findById(id);
        if (optional.isPresent()) {
            StickerToSell entity = optional.get();
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
        Optional<StickerToSell> optional = repository.findByStickerId(stickerId);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(optional.get().getId());
    }

    @Override
    public Optional<StickerToSell> findByStickerId(String stickerId) {
        return repository.findByStickerId(stickerId);
    }
}
