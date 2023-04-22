package br.com.ada.figurinhas.controller;

import br.com.ada.figurinhas.model.dto.FigurinhaToSellCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaToSellDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaToSellUpdateDTO;
import br.com.ada.figurinhas.model.mapper.FigurinhaToSellMapper;
import br.com.ada.figurinhas.service.FigurinhaToSellService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/figurinha/sell")
public class FigurinhaToSellController {

    private final FigurinhaToSellService service;
    private final FigurinhaToSellMapper mapper;
    public FigurinhaToSellController(final FigurinhaToSellService service,
                                   final FigurinhaToSellMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<FigurinhaToSellDTO>> findAll() {
        final List<FigurinhaToSellDTO> response = service.findAll();
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FigurinhaToSellDTO> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(mapper.parseDTO(service.findById(id)));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<FigurinhaToSellDTO> create(@RequestBody @Valid FigurinhaToSellCreationDTO creationDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.create(creationDTO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FigurinhaToSellDTO> edit(@PathVariable("id") String id,
                                           @RequestBody @Valid FigurinhaToSellUpdateDTO updateDTO) {
        try {
            return ResponseEntity.ok(service.edit(id, updateDTO));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
