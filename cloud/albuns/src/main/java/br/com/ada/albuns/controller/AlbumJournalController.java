package br.com.ada.albuns.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ada.albuns.model.dto.AlbumJournalDTO;
import br.com.ada.albuns.service.AlbumJournalService;

@RestController
@RequestMapping("/album/journal")
public class AlbumJournalController {

    protected final AlbumJournalService service;

    public AlbumJournalController(AlbumJournalService service) {
        this.service = service;
    }

    /* Retrieve All Album Journals */
    @GetMapping
    public ResponseEntity<List<AlbumJournalDTO>> findAll() {
        List<AlbumJournalDTO> result = service.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
        }
        return ResponseEntity.ok(result);
    }
}
