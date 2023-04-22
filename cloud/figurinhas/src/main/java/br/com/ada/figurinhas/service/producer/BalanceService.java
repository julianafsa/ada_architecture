package br.com.ada.stickers.service.producer;

import java.math.BigDecimal;

public interface BalanceService {

    void incrementBalance(String userId, BigDecimal value);
    void decrementBalance(String userId, BigDecimal value);
    BigDecimal getBalance(String userId);
}
