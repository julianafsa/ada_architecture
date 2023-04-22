package br.com.ada.stickers.service.impl;

import br.com.ada.stickers.service.RedisService;
import br.com.ada.stickers.service.producer.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
@Slf4j
@Service
public class BalanceServiceImpl implements BalanceService {

    private RedisService redisService;
    public BalanceServiceImpl(final RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void incrementBalance(String userId, BigDecimal value) {
        BigDecimal balance = null;
        final String oldValueAsString = redisService.get(userId);
        if (Objects.nonNull(oldValueAsString)) {
            balance = value.add(new BigDecimal(oldValueAsString));
        } else {
            balance = value;
        }
        log.info("[BalanceService.incrementBalance] UserId = {}, Balance = {}", userId, balance);
        redisService.save(userId, balance.toString());
    }

    @Override
    public void decrementBalance(String userId, BigDecimal value) {
        BigDecimal balance = null;
        final String oldValueAsString = redisService.get(userId);
        if (Objects.nonNull(oldValueAsString)) {
            balance = value.subtract(new BigDecimal(oldValueAsString));
        } else {
            balance = value;
        }
        log.info("[BalanceService.decrementBalance] UserId = {}, Balance = {}", userId, balance);
        redisService.save(userId, balance.toString());
    }

    @Override
    public BigDecimal getBalance(String key) {
        final String value = redisService.get(key);
        if (Objects.nonNull(value)) {
            return new BigDecimal(value);
        }
        return BigDecimal.ZERO;
    }

}
