package br.com.ada.figurinhas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "albuns", path = "/album", contextId="album-client")
public interface AlbumClient {
    @GetMapping("/usuario/{albumId}")
    ResponseEntity<String> findUsuarioIdByAlbumId(@PathVariable("albumId") String albumId);
}
