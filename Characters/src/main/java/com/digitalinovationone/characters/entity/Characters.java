package com.digitalinovationone.characters.entity;

import com.digitalinovationone.characters.enums.CharacterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Characters {

    //Mapeamento da entidade ao JPA para o banco de dados Id "Chave Primária" e colunas (nome,universo e nível de poder)
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String universe;

    @Column(nullable = false)
    private int levelPower;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CharacterType type;
}
