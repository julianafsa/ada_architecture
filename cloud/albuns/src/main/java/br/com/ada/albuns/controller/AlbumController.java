package br.com.ada.albuns.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ada.albuns.model.dto.AlbumDTO;
import br.com.ada.albuns.service.AlbumService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/album")
@Slf4j
public class AlbumController {

    protected final AlbumService service;

    public AlbumController(AlbumService service) {
        this.service = service;
    }

    /* Create Album */
    @PostMapping
    public ResponseEntity<AlbumDTO> create(@RequestBody @Valid AlbumDTO albumDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.create(albumDTO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /* Retrieve All Albums */
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> findAll() {
        List<AlbumDTO> result = service.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        return ResponseEntity.ok(result);
    }

    /* Retrieve Album By Id */
    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
