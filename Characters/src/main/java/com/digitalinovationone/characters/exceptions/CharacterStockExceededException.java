package com.digitalinovationone.characters.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CharacterStockExceededException extends Exception {

    public CharacterStockExceededException(Long id, int levelToIncrement) {
        super(String.format("Beers with %d ID to increment informed exceeds the max stock capacity: %s", id, levelToIncrement));
    }
}
