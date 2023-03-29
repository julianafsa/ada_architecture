package br.com.ada.albuns.client;

import jakarta.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ada.albuns.client.dto.StickerCreationDTO;
import br.com.ada.albuns.client.dto.StickerDTO;

@FeignClient(name = "stickers", path = "/sticker", contextId="stickers")
public interface StickerClient {
    @PostMapping
    ResponseEntity<StickerDTO> create(@RequestBody @Valid StickerCreationDTO creationDTO);
}
