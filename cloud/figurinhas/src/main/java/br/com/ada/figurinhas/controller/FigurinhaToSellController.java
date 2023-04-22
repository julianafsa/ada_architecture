package br.com.ada.stickers.controller;

import br.com.ada.stickers.model.dto.StickerToSellCreationDTO;
import br.com.ada.stickers.model.dto.StickerToSellDTO;
import br.com.ada.stickers.model.dto.StickerToSellUpdateDTO;
import br.com.ada.stickers.model.mapper.StickerToSellMapper;
import br.com.ada.stickers.service.StickerToSellService;
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
@RequestMapping(value = "/sticker/sell")
public class StickerToSellController {

    private final StickerToSellService service;
    private final StickerToSellMapper mapper;
    public StickerToSellController(final StickerToSellService service,
                                   final StickerToSellMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<StickerToSellDTO>> findAll() {
        final List<StickerToSellDTO> response = service.findAll();
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StickerToSellDTO> findById(@PathVariable("id") String id) {
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
    public ResponseEntity<StickerToSellDTO> create(@RequestBody @Valid StickerToSellCreationDTO creationDTO) {
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
    public ResponseEntity<StickerToSellDTO> edit(@PathVariable("id") String id,
                                           @RequestBody @Valid StickerToSellUpdateDTO updateDTO) {
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
