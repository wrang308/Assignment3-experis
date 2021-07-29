package se.experis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.models.Character;
import se.experis.repositories.CharacterRepository;
import se.experis.services.CharacterService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/characters")
public class CharacterController {
    @Autowired
    private CharacterService characterService;

    @GetMapping()
    public ResponseEntity<List<Character>> getAllCharacters(){
        return characterService.getAllCharacters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacter(@PathVariable Long id){
        return characterService.getCharacter(id);
    }

    @PostMapping
    public ResponseEntity<Character> addCharacter(@RequestBody Character character){
        return characterService.addCharacter(character);
    }

    @PutMapping("/")
    public ResponseEntity<Character> updateCharacter( @RequestBody Character character){
        return characterService.updateCharacter(character);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Character> deleteCharacter(@PathVariable Long id) {
        return characterService.deleteCharacter(id);
    }

}
