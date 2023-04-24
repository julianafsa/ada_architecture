package br.com.ada.figurinhas.controller;

import br.com.ada.figurinhas.model.dto.FigurinhaJournalCreationDTO;
import br.com.ada.figurinhas.model.dto.FigurinhaJournalDTO;
import br.com.ada.figurinhas.service.FigurinhaJournalService;
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
@RequestMapping(value = "/figurinha/journal")
public class FigurinhaJournalController {

    protected final FigurinhaJournalService service;
    public FigurinhaJournalController(final FigurinhaJournalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FigurinhaJournalDTO>> findAll() {
        final List<FigurinhaJournalDTO> response = service.findAll();
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FigurinhaJournalDTO> findById(@PathVariable("id") String id) {
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
    public ResponseEntity<FigurinhaJournalDTO> create(@RequestBody @Valid FigurinhaJournalCreationDTO creationDTO) {
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

}
