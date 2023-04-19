package br.com.ada.albuns.client;

import jakarta.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ada.albuns.client.dto.FigurinhaCreationDTO;
import br.com.ada.albuns.client.dto.FigurinhaDTO;

@FeignClient(name = "stickers", path = "/sticker", contextId="stickers")
public interface FigurinhaClient {
    @PostMapping
    ResponseEntity<FigurinhaDTO> create(@RequestBody @Valid FigurinhaCreationDTO creationDTO);
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id);
}
