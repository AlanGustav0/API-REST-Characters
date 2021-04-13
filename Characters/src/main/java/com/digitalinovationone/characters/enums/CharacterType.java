package com.digitalinovationone.characters.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CharacterType {

    MARVEL("Capitão América"),
    DCCOMICS("Homen de Ferro");

    private final String description;
}
