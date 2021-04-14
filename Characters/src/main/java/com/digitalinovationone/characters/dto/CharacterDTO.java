package com.digitalinovationone.characters.dto;

import com.digitalinovationone.characters.enums.CharacterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @Size(min = 1, max = 200)
    private String universe;

    @NotNull
    @Max(50000)
    private int levelPower;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CharacterType type;
}
