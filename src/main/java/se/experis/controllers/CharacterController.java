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

    //Makes a request to characterservice and returns getting a list of all characters
    @GetMapping()
    public ResponseEntity<List<Character>> getAllCharacters(){
        return characterService.getAllCharacters();
    }

    //Makes a request to characterservice for getting a specific character with given id
    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacter(@PathVariable Long id){
        return characterService.getCharacter(id);
    }

    //Makes a post-request to characterservice to add/create new character
    @PostMapping
    public ResponseEntity<Character> addCharacter(@RequestBody Character character){
        return characterService.addCharacter(character);
    }

    //Maes a request to characterservice for updating an existing character
    @PutMapping("/")
    public ResponseEntity<Character> updateCharacter( @RequestBody Character character){
        return characterService.updateCharacter(character);
    }

    //Maes a request to characterservice for deleting an existing character
    @DeleteMapping("/{id}")
    public ResponseEntity<Character> deleteCharacter(@PathVariable Long id) {
        return characterService.deleteCharacter(id);
    }

}
