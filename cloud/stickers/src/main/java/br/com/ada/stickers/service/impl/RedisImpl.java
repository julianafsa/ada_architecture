package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.service.Redis;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;

@Service
public class RedisImpl implements Redis {

    private Jedis jedis;
    public RedisImpl() {
        jedis = new Jedis("usw1-fresh-fish-33216.upstash.io",33216);
        jedis.auth("c0426cdda92844148acaf5d6b50ffe77");
    }

    @Override
    public void updateBalance(String key, BigDecimal value) {
        String oldValue = this.get(key);
        if (oldValue != null) {
            BigDecimal oldValueAsString = new BigDecimal(oldValue);
            BigDecimal total = value.add(oldValueAsString);
            jedis.set(key, total.toString());
        } else {
            jedis.set(key, value.toString());
        }
    }

    @Override
    public String get(String key) {
        return jedis.get(key);
    }
}
