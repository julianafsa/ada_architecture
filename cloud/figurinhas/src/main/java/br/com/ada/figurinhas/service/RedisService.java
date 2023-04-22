package br.com.ada.stickers.service;

public interface RedisService {
    void save(String key, String value);
    String get(String key);
}
