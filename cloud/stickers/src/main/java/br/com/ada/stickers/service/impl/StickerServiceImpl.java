package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.model.dto.StickerCreationDTO;
import br.com.ada.stickers.model.dto.StickerDTO;
import br.com.ada.stickers.model.dto.StickerUpdateDTO;
import br.com.ada.stickers.model.entity.Sticker;
import br.com.ada.stickers.model.mapper.StickerMapper;
import br.com.ada.stickers.repository.StickerRepository;
import br.com.ada.stickers.service.StickerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StickerServiceImpl implements StickerService {

    private final StickerRepository repository;
    private final StickerMapper mapper;

    //@PersistenceContext
    //private EntityManager em;

    public StickerServiceImpl(final StickerRepository repository, final StickerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public List<StickerDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public StickerDTO findById(final String id) {
        Optional<Sticker> optional = repository.findById(id);
        if (optional.isPresent()) {
            final Sticker entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    //@Transactional
    public StickerDTO create(final StickerCreationDTO creationDTO) {
        Sticker entity = mapper.parseEntity(creationDTO);
        entity.setId(null);
        entity = repository.save(entity);
        //em.refresh(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public StickerDTO edit(final String id, final StickerUpdateDTO updateDTO) {
        if (repository.existsById(id)) {
            Sticker entity = mapper.parseEntity(updateDTO);
            entity.setId(id);
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
}
