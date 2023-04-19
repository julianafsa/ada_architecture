package br.com.ada.albuns.controller;

import br.com.ada.albuns.model.dto.PrototipoDeAlbumDTO;
import br.com.ada.albuns.service.PrototipoDeAlbumService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album/template")
@Slf4j
public class PrototipoDeAlbumController {

    protected final PrototipoDeAlbumService service;

    public PrototipoDeAlbumController(PrototipoDeAlbumService service) {
        this.service = service;
    }

    /* Create Album Teamplate */
    @PostMapping
    public ResponseEntity<PrototipoDeAlbumDTO> create(@RequestBody @Valid PrototipoDeAlbumDTO prototipoDeAlbumDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.create(prototipoDeAlbumDTO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /* Retrive All Album Teamplate */
    @GetMapping
    public ResponseEntity<List<PrototipoDeAlbumDTO>> findAll() {
    	try {
	        List<PrototipoDeAlbumDTO> result = service.findAll();
	        if (result.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	        }
	        return ResponseEntity.ok(result);
    	} catch (Exception ex) {
    		return ResponseEntity.internalServerError().build();
    	}
    }

    /* Retrive Album By Id */
    @GetMapping("/{id}")
    public ResponseEntity<PrototipoDeAlbumDTO> findById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /* Update Album Teamplate */
    @PutMapping("/{id}")
    public ResponseEntity<PrototipoDeAlbumDTO> edit(@PathVariable("id") String id,
                                                    @RequestBody @Valid PrototipoDeAlbumDTO prototipoDeAlbumDTO) {
        try {
            return ResponseEntity.ok(service.edit(id, prototipoDeAlbumDTO));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /* Delete Album Teamplate */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
