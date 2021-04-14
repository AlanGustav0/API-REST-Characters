package com.digitalinovationone.characters.service;

import com.digitalinovationone.characters.dto.CharacterDTO;
import com.digitalinovationone.characters.entity.Characters;
import com.digitalinovationone.characters.exceptions.CharacterAlreadyRegisteredException;
import com.digitalinovationone.characters.exceptions.CharacterNotFoundException;
import lombok.AllArgsConstructor;
import com.digitalinovationone.characters.mapper.CharacterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.digitalinovationone.characters.repository.CharacterRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Notação de Classe de serviço
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper = CharacterMapper.INSTANCE;

    //Método para criar um novo Character
    public CharacterDTO createCharacter(CharacterDTO characterDTO) throws CharacterAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(characterDTO.getName());
        Characters character = characterMapper.toModel(characterDTO);
        Characters savedCharacter = characterRepository.save(character);
        return characterMapper.toDTO(savedCharacter);
    }

    //Método para listar todos os personagens
    public List<CharacterDTO> listCharacters() {
        return characterRepository.findAll()
                .stream()
                .map(characterMapper::toDTO)
                .collect(Collectors.toList());
    }

    //Localizar Character através do nome
    public CharacterDTO findByName(String name) throws CharacterNotFoundException {
        Characters foundCharacter = characterRepository.findByName(name)
                .orElseThrow(() -> new CharacterNotFoundException(name));
        return characterMapper.toDTO(foundCharacter);
    }


    //Método que verifica se o Character já está registrado
    private void verifyIfIsAlreadyRegistered(String name) throws CharacterAlreadyRegisteredException {
        Optional<Characters> optSaveCharacter = characterRepository.findByName(name);
        if (optSaveCharacter.isPresent()) {
            throw new CharacterAlreadyRegisteredException(name);
        }

    }

    //Deletando Character
    public void deleteById(Long id) throws CharacterNotFoundException {
        verifyIfExists(id);
        characterRepository.deleteById(id);
    }

    private Characters verifyIfExists(Long id) throws CharacterNotFoundException {
        return characterRepository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id));

    }

}




