package se.experis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import se.experis.models.Character;
import se.experis.repositories.CharacterRepository;
import se.experis.repositories.MovieRepository;

import java.util.List;

@Service
public class CharacterService {
    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Gets all characters
     * @return liat of characters and 200 http status
     */
    public ResponseEntity<List<Character>> getAllCharacters(){
        List<Character> characters = characterRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(characters,status);
    }

    /**
     * Gets a specific character by id
     * @param id character id to find
     * @return character and http status if ok/not ok
     */
    public ResponseEntity<Character> getCharacter(Long id){
        Character returnCharacter = new Character();
        HttpStatus status;
        // We first check if the character exists, this saves some computing time.
        if(characterRepository.existsById(id)){
            status = HttpStatus.OK;
            returnCharacter = characterRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnCharacter, status);
    }

    /**
     * adds a specific character
     * @param character
     * @return character and 201 http status
     */
    public ResponseEntity<Character> addCharacter(Character character){
        Character returnCharacter = characterRepository.save(character);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnCharacter, status);
    }

    /**
     * method to update a specifi character
     * @param character object to update
     * @return responseetity w character object updated and http status
     */
    public ResponseEntity<Character> updateCharacter(Character character){
        Character returnCharacter = new Character();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if(!characterRepository.existsById(character.getId())){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnCharacter,status);
        }
        character.movies = characterRepository.findById(character.getId()).get().movies;
        returnCharacter = characterRepository.save(character);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnCharacter, status);
    }

    /**
     * method for deleting a character by id
     * @param id if of character to delete
     * @return http status
     */
    public ResponseEntity<Character> deleteCharacter(Long id) {

        HttpStatus status = null;
        if (characterRepository.existsById(id)) {
            status = HttpStatus.OK;
            characterRepository.deleteById(id);

        } else {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(status);
    }

}
