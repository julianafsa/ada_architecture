package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.*;
import br.com.ada.stickers.model.mapper.FigurinhaMapper;
import br.com.ada.stickers.service.FigurinhaService;
import br.com.ada.stickers.service.FigurinhaServiceWithTransacao;
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
@RequestMapping(value = "/sticker")
public class FigurinhaController {

    private final FigurinhaService service;
    private final FigurinhaMapper mapper;
    private final FigurinhaServiceWithTransacao figurinhaServiceWithTransacao;

    public FigurinhaController(final FigurinhaService service,
                               final FigurinhaMapper mapper,
                               final FigurinhaServiceWithTransacao figurinhaServiceWithTransacao) {
        this.service = service;
        this.mapper = mapper;
        this.figurinhaServiceWithTransacao = figurinhaServiceWithTransacao;
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

    @PostMapping("/buy/pack")
    public ResponseEntity<List<FigurinhaDTO>> buyStickerPack(@RequestBody @Valid FigurinhaBuyPackDTO figurinhaBuyPackDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.parseListDTO(figurinhaServiceWithTransacao.buyStickerPack(figurinhaBuyPackDTO)));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/buy")
    public ResponseEntity<FigurinhaDTO> buyStickerFromAlbum(@RequestBody @Valid FigurinhaBuyFromAlbumDTO figurinhaBuyFromAlbumDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(mapper.parseDTO(figurinhaServiceWithTransacao.buyStickerFromAlbum(figurinhaBuyFromAlbumDTO)));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
