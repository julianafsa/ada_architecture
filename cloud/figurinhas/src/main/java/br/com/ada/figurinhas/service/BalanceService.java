package br.com.ada.figurinhas.service;

import java.math.BigDecimal;

public interface BalanceService {

    void incrementBalance(String usuarioId, BigDecimal value);
    void decrementBalance(String usuarioId, BigDecimal value);
    BigDecimal getBalance(String usuarioId);
}
