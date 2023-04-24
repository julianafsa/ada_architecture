package br.com.ada.figurinhas.controller;

import br.com.ada.figurinhas.model.dto.*;
import br.com.ada.figurinhas.model.entity.Figurinha;
import br.com.ada.figurinhas.model.mapper.FigurinhaMapper;
import br.com.ada.figurinhas.service.FigurinhaService;
import br.com.ada.figurinhas.service.FigurinhaServiceWithJournal;
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
@RequestMapping(value = "/figurinha")
public class FigurinhaController {

    private final FigurinhaService service;
    private final FigurinhaMapper mapper;
    private final FigurinhaServiceWithJournal figurinhaServiceWithJournal;

    public FigurinhaController(final FigurinhaService service,
                             final FigurinhaMapper mapper,
                             final FigurinhaServiceWithJournal figurinhaServiceWithJournal) {
        this.service = service;
        this.mapper = mapper;
        this.figurinhaServiceWithJournal = figurinhaServiceWithJournal;
    }

    @GetMapping
    public ResponseEntity<List<FigurinhaDTO>> findAll() {
        final List<FigurinhaDTO> response = service.findAll();
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FigurinhaDTO> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<FigurinhaDTO>> findAllByAlbumId(@PathVariable("albumId") String albumId) {
        final List<Figurinha> response = service.findByAlbumId(albumId);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapper.parseListDTO(response));
        }
        return ResponseEntity.ok(mapper.parseListDTO(response));
    }

    @PostMapping
    public ResponseEntity<FigurinhaDTO> create(@RequestBody @Valid FigurinhaCreationDTO creationDTO) {
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
    public ResponseEntity<FigurinhaDTO> edit(@PathVariable("id") String id,
                                           @RequestBody @Valid FigurinhaUpdateDTO updateDTO) {
        try {
            return ResponseEntity.ok(mapper.parseDTO(service.edit(id, updateDTO)));
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

    @PostMapping("/buy/pacote")
    public ResponseEntity<List<FigurinhaDTO>> buyFigurinhaPacote(@RequestBody @Valid FigurinhaBuyPacoteDTO figurinhaBuyPacoteDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.parseListDTO(figurinhaServiceWithJournal.buyFigurinhaPacote(figurinhaBuyPacoteDTO)));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<FigurinhaDTO> buyFigurinhaFromAlbum(@RequestBody @Valid FigurinhaBuyFromAlbumDTO figurinhaBuyFromAlbumDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.parseDTO(figurinhaServiceWithJournal.buyFigurinhaFromAlbum(figurinhaBuyFromAlbumDTO)));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
