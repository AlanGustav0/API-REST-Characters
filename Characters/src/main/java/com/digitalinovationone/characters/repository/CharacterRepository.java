package com.digitalinovationone.characters.repository;

//Importando JPARepository do spring
import com.digitalinovationone.characters.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Characters,Long> {

    Optional<Characters> findByName(String name);
}
