package com.digitalinovationone.characters.mapper;

import com.digitalinovationone.characters.dto.CharacterDTO;
import com.digitalinovationone.characters.entity.Characters;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

//Interface Mapeada para DTO (Data Transfer Object)
@Mapper
public interface CharacterMapper {

    CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);

    Characters toModel(CharacterDTO characterDTO);

    CharacterDTO toDTO(Characters characters);
}
