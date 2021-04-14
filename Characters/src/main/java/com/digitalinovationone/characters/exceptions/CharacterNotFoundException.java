package com.digitalinovationone.characters.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Notação de resposta de erro HTTP
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CharacterNotFoundException extends Exception {

    public CharacterNotFoundException(String characterName) {
        super(String.format("Character %s not found in the system.", characterName));
    }

    public CharacterNotFoundException(Long id) {super(String.format("Character with id %s not found in the system.",id));}
}
