package com.digitalinovationone.characters.controller;

import com.digitalinovationone.characters.builder.CharacterDTOBuilder;
import com.digitalinovationone.characters.dto.CharacterDTO;
import com.digitalinovationone.characters.exceptions.CharacterNotFoundException;
import com.digitalinovationone.characters.service.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.digitalinovationone.characters.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CharacterControllerTest {

    private static final String CHARACTER_API_URL_PATH = "/api/v1/characters";
    private static final long VALID_CHARACTER_ID = 1L;
    private static final long INVALID_CHARACTER_ID = 2L;

    private MockMvc mockMvc;

    //Sinalizamos que esta classe trata-se de um Mock
    @Mock
    private CharacterService characterService;

    @InjectMocks
    private CharacterController characterController;

    //Aqui é a configuração do mockMVC, para fazer um mapeamento de arquivo Json e testar apenas a classe ControllerTest
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(characterController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenACharacterIsCreated() throws Exception {
        // Criamos um personagem
        CharacterDTO characterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();

        // Quando recebemos um personagem
        when(characterService.createCharacter(characterDTO)).thenReturn(characterDTO);

        /* Então, verificamos com o mockMVC se o método POST está sendo executado com sucesso, ele irá simular
        da mesma forma como se estivesse realizando o POST de um arquivo Json através do POSTMAN.
        */
        mockMvc.perform(post(CHARACTER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(characterDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(characterDTO.getName())))
                .andExpect(jsonPath("$.universe", is(characterDTO.getUniverse())))
                .andExpect(jsonPath("$.levelPower",is(characterDTO.getLevelPower())))
                .andExpect(jsonPath("$.type", is(characterDTO.getType().toString())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // Criamos um personagem
        CharacterDTO characterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();
        characterDTO.setUniverse(null);

        // Aqui verificamos quando um personagem é criado sem os parâmetros, se está validando caso o campo seja nulo e retornando BadRequest
        mockMvc.perform(post(CHARACTER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(characterDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // Criamos o personagem
        CharacterDTO characterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();

        //Quando recebemos o retorno deste personagem
        when(characterService.findByName(characterDTO.getName())).thenReturn(characterDTO);

        // Então, verificamos se o retorno GET está sendo executado com sucesso
        mockMvc.perform(MockMvcRequestBuilders.get(CHARACTER_API_URL_PATH + "/" + characterDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(characterDTO.getName())))
                .andExpect(jsonPath("$.universe", is(characterDTO.getUniverse())))
                .andExpect(jsonPath("$.levelPower", is(characterDTO.getLevelPower())))
                .andExpect(jsonPath("$.type", is(characterDTO.getType().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        // Criamos um personagem
        CharacterDTO characterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();

        //Quando recebemos o retorno deste personagem
        when(characterService.findByName(characterDTO.getName())).thenThrow(CharacterNotFoundException.class);

        // Então, verificamos se o status está retornando NotFound, o personagem não foi encontrado
        mockMvc.perform(MockMvcRequestBuilders.get(CHARACTER_API_URL_PATH + "/" + characterDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETListWithCharacterIsCalledThenOkStatusIsReturned() throws Exception {
        // Criamos o personagem
        CharacterDTO characterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();

        //Quando recebemos uma lista de personagens
        when(characterService.listCharacters()).thenReturn(Collections.singletonList(characterDTO));

        // Então, validamos se o status esta retornando OK
        mockMvc.perform(MockMvcRequestBuilders.get(CHARACTER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(characterDTO.getName())))
                .andExpect(jsonPath("$[0].universe", is(characterDTO.getUniverse())))
                .andExpect(jsonPath("$[0].levelPower", is(characterDTO.getLevelPower())))
                .andExpect(jsonPath("$[0].type", is(characterDTO.getType().toString())));
    }

    @Test
    void whenGETListWithoutCharacterIsCalledThenOkStatusIsReturned() throws Exception {
        // Criamos um personagem
        CharacterDTO characterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();

        //Quando uma lista é retornada
        when(characterService.listCharacters()).thenReturn(Collections.singletonList(characterDTO));

        // Então, validados se uma lista está sendo retornada sem personagens
        mockMvc.perform(MockMvcRequestBuilders.get(CHARACTER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // Criamos um personagem
        CharacterDTO characterDTO = CharacterDTOBuilder.builder().build().toCharacterDTO();

        //Quando recebemos um ID
        doNothing().when(characterService).deleteById(characterDTO.getId());

        // Então, validamos se este ID não foi encontrado para ser deletado
        mockMvc.perform(MockMvcRequestBuilders.delete(CHARACTER_API_URL_PATH + "/" + characterDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(CharacterNotFoundException.class).when(characterService).deleteById(INVALID_CHARACTER_ID);

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(CHARACTER_API_URL_PATH + "/" + INVALID_CHARACTER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



}
