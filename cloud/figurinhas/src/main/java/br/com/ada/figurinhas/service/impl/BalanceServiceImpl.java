package br.com.ada.figurinhas.service.impl;

import br.com.ada.figurinhas.service.RedisService;
import br.com.ada.figurinhas.service.BalanceService;
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
    public void incrementBalance(String usuarioId, BigDecimal value) {
        BigDecimal balance = null;
        final String oldValueAsString = redisService.get(usuarioId);
        if (Objects.nonNull(oldValueAsString)) {
            balance = value.add(new BigDecimal(oldValueAsString));
        } else {
            balance = value;
        }
        log.info("[BalanceService.incrementBalance] UsuarioId = {}, Balance = {}", usuarioId, balance);
        redisService.save(usuarioId, balance.toString());
    }

    @Override
    public void decrementBalance(String usuarioId, BigDecimal value) {
        BigDecimal balance = null;
        final String oldValueAsString = redisService.get(usuarioId);
        if (Objects.nonNull(oldValueAsString)) {
            balance = value.subtract(new BigDecimal(oldValueAsString));
        } else {
            balance = value;
        }
        log.info("[BalanceService.decrementBalance] UsuarioId = {}, Balance = {}", usuarioId, balance);
        redisService.save(usuarioId, balance.toString());
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
