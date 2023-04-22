package br.com.ada.figurinhas.controller;

import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaPrototipoUpdateDTO;
import br.com.ada.figurinhas.service.FigurinhaPrototipoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/figurinha/prototipo")
public class FigurinhaPrototipoController {

    private final FigurinhaPrototipoService service;
    public FigurinhaPrototipoController(final FigurinhaPrototipoService service) {
        this.service = service;
    
    }
    @GetMapping
    public ResponseEntity<List<FigurinhaPrototipoDTO>> findAll(@RequestParam("albumPrototipoId") Optional<String> opAlbumPrototipoId) {
        final List<FigurinhaPrototipoDTO> response = service.findAll(opAlbumPrototipoId);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FigurinhaPrototipoDTO> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<FigurinhaPrototipoDTO> create(@RequestBody @Valid FigurinhaPrototipoCreationDTO creationDTO) {
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
    public ResponseEntity<FigurinhaPrototipoDTO> edit(@PathVariable("id") String id,
                                           @RequestBody @Valid FigurinhaPrototipoUpdateDTO updateDTO) {
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
