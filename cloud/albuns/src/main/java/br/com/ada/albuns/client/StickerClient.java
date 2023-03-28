package br.com.ada.albuns.client;

import jakarta.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ada.albuns.client.dto.StickerCreationDTO;
import br.com.ada.albuns.client.dto.StickerDTO;
import br.com.ada.albuns.client.dto.StickerUpdateDTO;

import java.util.List;

@FeignClient(name = "stickers", path = "/sticker", contextId="stickers")
public interface StickerClient {

    @GetMapping
    ResponseEntity<List<StickerDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<StickerDTO> findById(@PathVariable("id") String id);

    @PostMapping
    ResponseEntity<StickerDTO> create(@RequestBody @Valid StickerCreationDTO creationDTO);

    @PutMapping("/{id}")
    ResponseEntity<StickerDTO> edit(@PathVariable("id") String id,
                                           @RequestBody @Valid StickerUpdateDTO updateDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable("id") String id);
}
