package br.com.ada.albuns.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ada.albuns.client.dto.PrototipoDeFigurinhaDTO;

import java.util.List;

@FeignClient(name = "stickers", path="/sticker/template", contextId="stickers-teamplate")
public interface PrototipoDeFigurinhaClient {

    @GetMapping
    ResponseEntity<List<PrototipoDeFigurinhaDTO>> findAll(@RequestParam(value = "albumTemplateId", required = false) String albumTemplateId);
}
