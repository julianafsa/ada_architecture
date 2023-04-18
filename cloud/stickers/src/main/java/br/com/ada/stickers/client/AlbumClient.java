package br.com.ada.stickers.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "album", path = "/album", contextId="albuns")
public interface AlbumClient {
    @GetMapping
    ResponseEntity<String> findUserIdByAlbumId(@PathVariable("albumId") String albumId);
}
