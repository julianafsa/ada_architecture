package br.com.ada.figurinhas.service;

public interface RedisService {
    void save(String key, String value);
    String get(String key);
}
