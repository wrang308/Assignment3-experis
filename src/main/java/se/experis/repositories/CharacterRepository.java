package se.experis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.models.Character;

public interface CharacterRepository extends JpaRepository<Character, Integer> {


}
