package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoUpdateDTO;
import br.com.ada.figurinhas.model.entity.FigurinhaPrototipo;
import br.com.ada.figurinhas.model.mapper.FigurinhaPrototipoMapper;
import br.com.ada.figurinhas.repository.FigurinhaPrototipoRepository;
import br.com.ada.figurinhas.service.FigurinhaPrototipoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FigurinhaPrototipoServiceImpl implements FigurinhaPrototipoService {

    private final FigurinhaPrototipoRepository repository;
    private final FigurinhaPrototipoMapper mapper;

    public FigurinhaPrototipoServiceImpl(final FigurinhaPrototipoRepository repository, final FigurinhaPrototipoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public List<FigurinhaPrototipoDTO> findAll(Optional<String> opAlbumPrototipoId) {
    	List<FigurinhaPrototipo> figurinhaPrototipos;
    	if (opAlbumPrototipoId.isPresent()) {
    		figurinhaPrototipos = repository.findByAlbumPrototipoId(opAlbumPrototipoId.get());
    	} else {
    		figurinhaPrototipos = repository.findAll();
    	}
        return mapper.parseListDTO(figurinhaPrototipos);
    }

    @Override
    public FigurinhaPrototipoDTO findById(final String id) {
        Optional<FigurinhaPrototipo> optional = repository.findById(id);
        if (optional.isPresent()) {
            final FigurinhaPrototipo entity = optional.get();
            return mapper.parseDTO(entity);
        }
        throw new EntityNotFoundException();
    }

    @Override
    public FigurinhaPrototipoDTO create(final FigurinhaPrototipoCreationDTO creationDTO) {
        FigurinhaPrototipo entity = mapper.parseEntity(creationDTO);
        entity.setId(null);
        entity = repository.save(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public FigurinhaPrototipoDTO edit(final String id, final FigurinhaPrototipoUpdateDTO updateDTO) {
        if (repository.existsById(id)) {
            FigurinhaPrototipo entity = mapper.parseEntity(updateDTO);
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
