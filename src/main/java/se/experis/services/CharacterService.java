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

    public ResponseEntity<List<Character>> getAllCharacters(){
        List<Character> characters = characterRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(characters,status);
    }

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

    public ResponseEntity<Character> addCharacter(Character character){
        Character returnCharacter = characterRepository.save(character);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnCharacter, status);
    }

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
        returnCharacter = characterRepository.save(character);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnCharacter, status);
    }

    public ResponseEntity<Character> deleteCharacter(Long id) {

        HttpStatus status = null;
        if (characterRepository.existsById(id)) {
            status = HttpStatus.OK;

            //characterRepository.delete(characterRepository.getById(id));
            characterRepository.deleteById(id);

        } else {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(status);
    }

}
