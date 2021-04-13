package com.digitalinovationone.characters.controller;

import com.digitalinovationone.characters.dto.CharacterDTO;
import com.digitalinovationone.characters.exceptions.CharacterAlreadyRegisteredException;
import com.digitalinovationone.characters.exceptions.CharacterNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.digitalinovationone.characters.service.CharacterService;


import javax.validation.Valid;
import java.util.List;

//Classe de controle que implementa a interface CharacterControllerDocs mapeada com Spring para realizar os métodos
@RestController
@RequestMapping("/api/v1/characters")
        @AllArgsConstructor(onConstructor = @__(@Autowired))
public class CharacterController implements CharacterControllerDocs {

    private final CharacterService characterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDTO createCharacter(@Valid @RequestBody CharacterDTO characterDTO) throws CharacterAlreadyRegisteredException {
        return characterService.createCharacter(characterDTO);
    }

    @GetMapping("/{name}")
    public CharacterDTO findByName(@PathVariable String name) throws CharacterNotFoundException {
        return characterService.findByName(name);
    }

    @GetMapping
    public List<CharacterDTO> listCharacters() {
        return characterService.listCharacters();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws CharacterNotFoundException {
        characterService.deleteById(id);
    }
}
