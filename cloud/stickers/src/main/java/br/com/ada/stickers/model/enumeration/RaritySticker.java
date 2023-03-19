package br.com.ada.stickers.model.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RaritySticker {
    DEGREE_1("1"),
    DEGREE_2("2"),
    DEGREE_3("3"),
    DEGREE_4("4");

    private String value;
}
