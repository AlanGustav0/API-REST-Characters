package com.digitalinovationone.characters.repository;

//Importando JPARepository do spring
import com.digitalinovationone.characters.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Characters,Long> {

    Optional<Characters> findByName(String name);
}
