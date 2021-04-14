package com.digitalinovationone.characters.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CharacterType {

    HEROI("Capitão América"),
    VILAO("Thanos"),
    FUN("Perna Longa");

    private final String description;
}
