package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.config.RedisProperties;
import br.com.ada.stickers.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    private final RedisProperties redisProperties;
    private Jedis jedis;
    public RedisServiceImpl(final RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        this.connect();
    }

    @Override
    public void save(String key, String value) {
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            final String errorMessage = "[REDIS] Error: " + e.getMessage() + ". Trying to reconnect...";
            log.error(errorMessage);
            this.connect();
            jedis.set(key, value);
        }
    }

    @Override
    public String get(String key) {
        String value;
        try {
            value = jedis.get(key);
        } catch (Exception e) {
            final String errorMessage = "[REDIS] Error: " + e.getMessage() + ". Trying to reconnect...";
            log.error(errorMessage);
            this.connect();
            value = jedis.get(key);
        }
        return value;
    }

    private void connect() {
        jedis = new Jedis(redisProperties.getHost(), Integer.parseInt(redisProperties.getPort()));
        jedis.auth(redisProperties.getPassword());
    }
}
