package com.digitalinovationone.characters.builder;

import com.digitalinovationone.characters.dto.CharacterDTO;
import com.digitalinovationone.characters.enums.CharacterType;
import lombok.Builder;


@Builder
public class CharacterDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Capitão América";

    @Builder.Default
    private String universe = "Marvel";

    @Builder.Default
    private int levelPower = 48000;

    @Builder.Default
    private CharacterType type = CharacterType.MARVEL;

    public CharacterDTO toCharacterDTO() {
        return new CharacterDTO(id,
                name,
                universe,
                levelPower,
                type);
    }

}
