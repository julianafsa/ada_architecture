package br.com.ada.albuns.client;

import jakarta.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ada.albuns.client.dto.StickerTemplateCreationDTO;
import br.com.ada.albuns.client.dto.StickerTemplateDTO;
import br.com.ada.albuns.client.dto.StickerTemplateUpdateDTO;

import java.util.List;

@FeignClient(name = "stickers", path="/sticker/template", contextId="stickers-teamplate")
public interface StickerTemplateClient {

    @GetMapping
    ResponseEntity<List<StickerTemplateDTO>> findAll(@RequestParam(value = "albumTemplateId", required = false) String albumTemplateId);

    @GetMapping("/{id}")
    ResponseEntity<StickerTemplateDTO> findById(@PathVariable("id") String id);

    @PostMapping
    ResponseEntity<StickerTemplateDTO> create(@RequestBody @Valid StickerTemplateCreationDTO creationDTO);

    @PutMapping("/{id}")
    ResponseEntity<StickerTemplateDTO> edit(@PathVariable("id") String id,
                                           @RequestBody @Valid StickerTemplateUpdateDTO updateDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable("id") String id);
}
