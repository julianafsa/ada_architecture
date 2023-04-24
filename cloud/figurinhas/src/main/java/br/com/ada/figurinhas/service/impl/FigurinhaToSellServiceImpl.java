package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.exceptions.FigurinhaAlreadyAvailableForSaleException;
import br.com.ada.figurinhas.model.dto.FigurinhaDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaToSellCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaToSellDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaToSellUpdateDTO;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.entity.FigurinhaToSell;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.model.mapper.FigurinhaToSellMapper;
import br.com.ada.figurinhas.repository.FigurinhaToSellRepository;
import br.com.ada.figurinhas.service.FigurinhaService;
import br.com.ada.figurinhas.service.FigurinhaToSellService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FigurinhaToSellServiceImpl implements FigurinhaToSellService {

    private final FigurinhaToSellRepository repository;
    private final FigurinhaToSellMapper mapper;
    private final FigurinhaService figurinhaService;
    private final FigurinhaMapper figurinhaMapper;

    public FigurinhaToSellServiceImpl(final FigurinhaToSellRepository repository,
                                    final FigurinhaToSellMapper mapper,
                                    final FigurinhaService figurinhaService,
                                    final FigurinhaMapper figurinhaMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.figurinhaService = figurinhaService;
        this.figurinhaMapper = figurinhaMapper;
    }
    
    @Override
    public List<FigurinhaToSellDTO> findAll() {
        return mapper.parseListDTO(repository.findAll());
    }

    @Override
    public FigurinhaToSell findById(final String id) {
        Optional<FigurinhaToSell> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException();
    }

    @Override
    public FigurinhaToSellDTO create(final FigurinhaToSellCreationDTO creationDTO) {
        final String figurinhaId = creationDTO.getFigurinha().getId();
        final FigurinhaDTO figurinhaDTO = figurinhaService.findById(figurinhaId);
        Figurinha figurinhaEntity = figurinhaMapper.parseEntity(figurinhaDTO);
        final Optional<FigurinhaToSell> figurinhaAlreadyForSale = repository.findByFigurinhaId(figurinhaId);
        if (figurinhaAlreadyForSale.isPresent()) {
            final String errorMessage = "Figurinha " + figurinhaId + " is already for sale.";
            throw new FigurinhaAlreadyAvailableForSaleException(errorMessage);
        }
        FigurinhaToSell entity = mapper.parseEntity(creationDTO);
        entity.setId(null);
        entity.setFigurinha(figurinhaEntity);
        entity = repository.save(entity);
        return mapper.parseDTO(entity);
    }

    @Override
    public FigurinhaToSellDTO edit(final String id, final FigurinhaToSellUpdateDTO updateDTO) {
        final Optional<FigurinhaToSell> optional = repository.findById(id);
        if (optional.isPresent()) {
            FigurinhaToSell entity = optional.get();
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
    public void deleteByFigurinhaId(String figurinhaId) {
        Optional<FigurinhaToSell> optional = repository.findByFigurinhaId(figurinhaId);
        if (!optional.isPresent()) {
            throw new EntityNotFoundException();
        }
        repository.deleteById(optional.get().getId());
    }

    @Override
    public Optional<FigurinhaToSell> findByFigurinhaId(String figurinhaId) {
        return repository.findByFigurinhaId(figurinhaId);
    }
}
