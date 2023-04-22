package br.com.ada.albuns.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.ada.albuns.client.dto.FigurinhaPrototipoDTO;

import java.util.List;

@FeignClient(name = "figurinhas", path="/figurinha/prototipo", contextId="figurinhas-teamplate")
public interface FigurinhaPrototipoClient {

    @GetMapping
    ResponseEntity<List<FigurinhaPrototipoDTO>> findAll(@RequestParam(value = "albumPrototipoId", required = false) String albumPrototipoId);
}
