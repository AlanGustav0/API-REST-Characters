package com.digitalinovationone.characters.controller;

import com.digitalinovationone.characters.dto.CharacterDTO;
import com.digitalinovationone.characters.exceptions.CharacterAlreadyRegisteredException;
import com.digitalinovationone.characters.exceptions.CharacterNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*Esta classe é responsável por todas as respostas de ucesso ou erro de comunicação com o servidor
através Swagger, uma linguagem de descrição de interface para descrever API's RESTful

 */
@Api("Manager Character list")
public interface CharacterControllerDocs {

    @ApiOperation(value = "Character creation operation")
    @ApiResponses( value = {
            @ApiResponse(code = 201, message = "Success character creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    CharacterDTO createCharacter(CharacterDTO characterDTO) throws CharacterAlreadyRegisteredException;

    @ApiOperation(value = "Returns Character found by a given name")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success character found in system"),
            @ApiResponse(code = 404, message = "Character with given name not found.")
    })
    CharacterDTO findByName(@PathVariable String name) throws CharacterNotFoundException;

    @ApiOperation(value = "Returns a list of all characters registered in the system")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "List of all characters registered in the system"),
    })
    List<CharacterDTO> listCharacters();

    @ApiOperation(value = "Delete a beer found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success character deleted in the system"),
            @ApiResponse(code = 404, message = "Character with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws CharacterNotFoundException;


}
