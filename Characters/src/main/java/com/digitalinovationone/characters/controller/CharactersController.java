package com.digitalinovationone.characters.controller;

import com.digitalinovationone.characters.dto.CharacterDTO;
import com.digitalinovationone.characters.exceptions.CharacterAlreadyRegisteredException;
import com.digitalinovationone.characters.exceptions.CharacterNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.digitalinovationone.characters.service.CharactersService;


import javax.validation.Valid;
import java.util.List;

//Classe de controle que implementa a interface CharacterControllerDocs mapeada com Spring para realizar os métodos
@RestController
@RequestMapping("/api/v1/characters")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CharactersController implements CharacterControllerDocs {

    @Autowired
    public CharactersService charactersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDTO createCharacter(@RequestBody @Valid CharacterDTO characterDTO) throws CharacterAlreadyRegisteredException {
        return charactersService.createCharacter(characterDTO);
    }

    @GetMapping("/{name}")
    public CharacterDTO findByName(@PathVariable String name) throws CharacterNotFoundException {
        return charactersService.findByName(name);
    }

    @GetMapping
    public List<CharacterDTO> listCharacters() {
        return charactersService.listCharacters();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws CharacterNotFoundException {
        charactersService.deleteById(id);
    }
}
