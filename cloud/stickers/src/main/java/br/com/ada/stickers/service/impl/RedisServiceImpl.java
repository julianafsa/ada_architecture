package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.config.RedisProperties;
import br.com.ada.stickers.service.RedisService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisProperties redisProperties;
    private final Jedis jedis;
    public RedisServiceImpl(final RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        jedis = new Jedis(redisProperties.getHost(), Integer.parseInt(redisProperties.getPort()));
        jedis.auth(redisProperties.getPassword());
    }

    @Override
    public void save(String key, String value) {
        jedis.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedis.get(key);
    }
}
