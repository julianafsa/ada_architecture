package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaCreationDTO;
import br.com.ada.stickers.model.dto.PrototipoDeFigurinhaUpdateDTO;
import br.com.ada.stickers.service.PrototipoDeFigurinhaService;
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
@RequestMapping(value = "/sticker/template")
public class PrototipoDeFigurinhaController {

    private final PrototipoDeFigurinhaService service;
    public PrototipoDeFigurinhaController(final PrototipoDeFigurinhaService service) {
        this.service = service;
    
    }
    @GetMapping
    public ResponseEntity<List<PrototipoDeFigurinhaDTO>> findAll(@RequestParam("albumTemplateId") Optional<String> opAlbumTemplateId) {
        final List<PrototipoDeFigurinhaDTO> response = service.findAll(opAlbumTemplateId);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrototipoDeFigurinhaDTO> findById(@PathVariable("id") String id) {
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
    public ResponseEntity<PrototipoDeFigurinhaDTO> create(@RequestBody @Valid PrototipoDeFigurinhaCreationDTO creationDTO) {
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
    public ResponseEntity<PrototipoDeFigurinhaDTO> edit(@PathVariable("id") String id,
                                                        @RequestBody @Valid PrototipoDeFigurinhaUpdateDTO updateDTO) {
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
