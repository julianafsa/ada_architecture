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

import br.com.ada.albuns.model.dto.AlbumTemplateDTO;
import br.com.ada.albuns.service.AlbumTemplateService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/templates")
@Slf4j
public class AlbumTemplateController {

  protected final AlbumTemplateService service;

  public AlbumTemplateController(AlbumTemplateService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<AlbumTemplateDTO>> findAll() {
      List<AlbumTemplateDTO> result = service.findAll();
      if (result.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
      }
      return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AlbumTemplateDTO> findById(@PathVariable("id") Long id) {
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

}
