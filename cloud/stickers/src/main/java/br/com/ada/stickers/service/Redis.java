package br.com.ada.stickers.service;

import java.math.BigDecimal;

public interface Redis {

    void updateBalance(String key, BigDecimal value);
    String get(String key);
}
