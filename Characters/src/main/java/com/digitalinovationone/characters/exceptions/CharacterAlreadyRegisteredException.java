package com.digitalinovationone.characters.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CharacterAlreadyRegisteredException extends Exception {

    public CharacterAlreadyRegisteredException(String characterName) {
        super(String.format("Character with name %s already registered in the system.", characterName));
    }
}
