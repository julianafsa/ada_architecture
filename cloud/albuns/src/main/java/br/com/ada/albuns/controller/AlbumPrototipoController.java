package br.com.ada.albuns.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ada.albuns.model.dto.AlbumTemplateDTO;
import br.com.ada.albuns.service.AlbumTemplateService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/album/template")
@Slf4j
public class AlbumTemplateController {

    protected final AlbumTemplateService service;

    public AlbumTemplateController(AlbumTemplateService service) {
        this.service = service;
    }

    /* Create Album Teamplate */
    @PostMapping
    public ResponseEntity<AlbumTemplateDTO> create(@RequestBody @Valid AlbumTemplateDTO albumTemplateDTO) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.create(albumTemplateDTO));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /* Retrive All Album Teamplate */
    @GetMapping
    public ResponseEntity<List<AlbumTemplateDTO>> findAll() {
    	try {
	        List<AlbumTemplateDTO> result = service.findAll();
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
    public ResponseEntity<AlbumTemplateDTO> findById(@PathVariable("id") String id) {
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
    public ResponseEntity<AlbumTemplateDTO> edit(@PathVariable("id") String id,
                                                 @RequestBody @Valid AlbumTemplateDTO albumTemplateDTO) {
        try {
            return ResponseEntity.ok(service.edit(id, albumTemplateDTO));
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
