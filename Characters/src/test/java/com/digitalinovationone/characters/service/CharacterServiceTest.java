package com.digitalinovationone.characters.service;

import com.digitalinovationone.characters.builder.CharacterDTOBuilder;
import com.digitalinovationone.characters.dto.CharacterDTO;
import com.digitalinovationone.characters.entity.Characters;
import com.digitalinovationone.characters.enums.CharacterType;
import com.digitalinovationone.characters.exceptions.CharacterAlreadyRegisteredException;
import com.digitalinovationone.characters.exceptions.CharacterNotFoundException;
import com.digitalinovationone.characters.mapper.CharacterMapper;
import com.digitalinovationone.characters.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    private static final CharacterType HEROI = CharacterType.HEROI;

    @Mock
    private CharacterRepository characterRepository;

    private CharacterMapper characterMapper = CharacterMapper.INSTANCE;

    @InjectMocks
    private CharacterService characterService;

    @Test
    void whenCharacterInformedThenItShouldBeCreated() throws CharacterAlreadyRegisteredException {
        // Criando o dado que será mapeado como personagem
        CharacterDTO expectedCharacterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();
        Characters expectedSavedCharacter = characterMapper.toModel(expectedCharacterDTO);

        // Quando receber esta informação, através do Mockito nós recebemos o personagem para realizar o teste
        when(characterRepository.findByName(expectedCharacterDTO.getName())).thenReturn(Optional.empty());
        when(characterRepository.save(expectedSavedCharacter)).thenReturn(expectedSavedCharacter);

        //Então, camamos o método de CharactersDTO e nos retorna outro DTO
        CharacterDTO createdCharacterDTO = characterService.createCharacter(expectedCharacterDTO);

        //Aqui realizamos o teste através do assertThat do Junit se os dados passados estão de acordo com a classe CharacterDTOBuilder
        //O "assertThat" trata-se do Hancrest que neste caso é utilizado para fazer o Matcher dos elementos
        assertThat(createdCharacterDTO.getId(), is(equalTo(expectedCharacterDTO.getId())));
        assertThat(createdCharacterDTO.getName(), is(equalTo(expectedCharacterDTO.getName())));
        assertThat(createdCharacterDTO.getLevelPower(), is(equalTo(expectedCharacterDTO.getLevelPower())));

        //Aqui verificamos se o tipo do personagem é igual a HEROI, para isso foi criada a constante e passada por parâmetro
        assertThat(createdCharacterDTO.getType(), is(equalTo((HEROI))));
    }

    @Test
    void whenAlreadyRegisteredCharacterInformedThenAnExceptionShouldBeThrown(){
        // Criando o personagem, aqui neste teste método vamos verificar se o personagem já foi criado
        CharacterDTO expectedCharacterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();
        Characters duplicatedCharacter = characterMapper.toModel(expectedCharacterDTO);

        // Quando receber o personagem, Mock do nome do personagem
        when(characterRepository.findByName(expectedCharacterDTO.getName())).thenReturn(Optional.of(duplicatedCharacter));

        // Então fazemos o assertThat e validamos se uma exceção foi lançada
        assertThrows(CharacterAlreadyRegisteredException.class, () -> characterService.createCharacter(expectedCharacterDTO));
    }

    @Test
    void whenValidCharacterNameIsGivenThenReturnACharacter() throws CharacterNotFoundException {
        // Criando o perssonagem
        CharacterDTO expectedFoundCharacterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();
        Characters expectedFoundCharacter = characterMapper.toModel(expectedFoundCharacterDTO);

        // Qundo recebemos o nome
        when(characterRepository.findByName(expectedFoundCharacter.getName())).thenReturn(Optional.of(expectedFoundCharacter));

        // Então validadamos o nome
        CharacterDTO foundCharacterDTO = characterService.findByName(expectedFoundCharacterDTO.getName());

        assertThat(foundCharacterDTO, is(equalTo(expectedFoundCharacterDTO)));
    }

    @Test
    void whenNotRegisteredCharacterNameIsGivenThenThrowAnException() {
        // Criamos o personagem
        CharacterDTO expectedFoundCharacterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();

        // Quando recebemos este personagem
        when(characterRepository.findByName(expectedFoundCharacterDTO.getName())).thenReturn(Optional.empty());

        // Então, fazemos a verificação e tratamos a exceção
        assertThrows(CharacterNotFoundException.class, () -> characterService.findByName(expectedFoundCharacterDTO.getName()));
    }

    @Test
    void whenListCharacterIsCalledThenReturnAListOfCharacters() {
        // Criamos o personagem
        CharacterDTO expectedFoundCharacterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();
        Characters expectedFoundCharacter = characterMapper.toModel(expectedFoundCharacterDTO);

        //Quando recebemos a lsita de personagens
        when(characterRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundCharacter));

        //Então criamos uma lista de personagens e validados se estamos recebendo uma lista de personagens
        List<CharacterDTO> foundListCharactersDTO = characterService.listCharacters();

        assertThat(foundListCharactersDTO, is(not(empty())));
        assertThat(foundListCharactersDTO.get(0), is(equalTo(expectedFoundCharacterDTO)));
    }

    @Test
    void whenListCharacterIsCalledThenReturnAnEmptyListOfCharacters() {
        //Quando recebemos uma coleção
        when(characterRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //Então, verificamos se esta lista esta ou não vazia
        List<CharacterDTO> foundListBeersDTO = characterService.listCharacters();

        assertThat(foundListBeersDTO, is(empty()));
    }

    @Test
    void whenExclusionIsCalledWithValidIdThenACharacterShouldBeDeleted() throws CharacterNotFoundException{
        // Criamos um personagem
        CharacterDTO expectedDeletedCharacterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();
        Characters expectedDeletedCharacter = characterMapper.toModel(expectedDeletedCharacterDTO);

        // Quando encontramos o ID para validar este personagem
        when(characterRepository.findById(expectedDeletedCharacterDTO.getId())).thenReturn(Optional.of(expectedDeletedCharacter));
        doNothing().when(characterRepository).deleteById(expectedDeletedCharacterDTO.getId());

        // Então chamamos o método de exclusão de personagem e validados nosso teste
        characterService.deleteById(expectedDeletedCharacterDTO.getId());

        verify(characterRepository, times(1)).findById(expectedDeletedCharacterDTO.getId());
        verify(characterRepository, times(1)).deleteById(expectedDeletedCharacterDTO.getId());
    }
}
