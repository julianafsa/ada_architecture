package br.com.ada.albuns.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ada.albuns.client.dto.StickerTemplateDTO;

import java.util.List;

@FeignClient(name = "stickers", path="/sticker/template", contextId="stickers-teamplate")
public interface StickerTemplateClient {

    @GetMapping
    ResponseEntity<List<StickerTemplateDTO>> findAll(@RequestParam(value = "albumTemplateId", required = false) String albumTemplateId);
}
