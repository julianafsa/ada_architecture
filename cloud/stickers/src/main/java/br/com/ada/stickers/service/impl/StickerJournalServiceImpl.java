package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.StickerJournalCreationDTO;
import br.com.ada.stickers.model.dto.StickerJournalDTO;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.model.entity.StickerJournal;
import br.com.ada.stickers.model.mapper.StickerJournalMapper;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.repository.StickerJournalRepository;
import br.com.ada.stickers.service.StickerJournalService;
import br.com.ada.stickers.service.StickerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StickerJournalServiceImpl implements StickerJournalService {

    private final StickerJournalRepository repository;
    private final StickerJournalMapper mapper;
    private final StickerService stickerService;
    private final StickerMapper stickerMapper;
    //private final Redis jedis;
    public StickerJournalServiceImpl(final StickerJournalRepository repository, final StickerJournalMapper mapper, final StickerService stickerService, final StickerMapper stickerMapper) {
        //final Redis jedis) {
        this.repository = repository;
        this.mapper = mapper;
        this.stickerService = stickerService;
        this.stickerMapper = stickerMapper;
        //this.jedis = jedis;
    }
    
    @Override
    public List<StickerJournalDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public StickerJournalDTO findById(final String id) {
        Optional<StickerJournal> optional = repository.findById(id);
        if (optional.isPresent()) {
            final StickerJournal entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public StickerJournalDTO create(final StickerJournalCreationDTO creationDTO) {
        StickerJournal entity = mapper.parseEntity(creationDTO);
        Sticker stickerEntity = stickerMapper.parseEntity(
                stickerService.findById(creationDTO.getSticker().getId()));
        entity.setId(null);
        entity.setSticker(stickerEntity);
        entity = repository.save(entity);
//        this.jedis.save(entity.getSourceAlbumId(), entity.getPrice());
        return mapper.parseDTO(entity);
    }

}