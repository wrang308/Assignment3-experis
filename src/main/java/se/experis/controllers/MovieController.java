package se.experis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.models.Character;
import se.experis.models.Movie;
import se.experis.repositories.CharacterRepository;
import se.experis.repositories.MovieRepository;
import se.experis.services.MovieService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    /**
     * Makes a request to movieservice
     * @return a list of all movies
     */
    @GetMapping()
    public ResponseEntity<List<Movie>> getAllMovies(){
        return movieService.getAllMovies();
    }

    /**
     * Makes a request to movieservice
     * @param id if of wanted movie
     * @return the requested movie object
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        return movieService.getMovie(id);
    }

    /**
     * Makes a request to movieservic to add a movie
     * @param movie object to add
     * @return the added movie object
     */
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
       return movieService.addMovie(movie);
    }

    /**
     * Makes a request to movieservic to update a movie
     * @param movie to update
     * @return the updated movie object
     */
    @PutMapping("/")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie){
        return movieService.updateMovie(movie);
    }

    /**
     * Makes a request to movieservic to delete a movie
     * @param id of the movie to delete
     * @return the deleted movie object
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        return movieService.deleteMovie(id);
    }

    /**
     * Makes a request to movieservic to get all characters in a movie
     * @param id movie id
     * @return a list of characters in specific movie
     */
    @GetMapping("/getAllCharactersInMovie/{id}")
    public ResponseEntity<List<Character>> getAllCharactersInMovie(@PathVariable Long id) {
        return movieService.getAllCharactersInMovie(id);
    }

    /**
     * Makes a request to movieservic to update a character in a movie
     * @param id of the specific movie
     * @param characterIds array of character ids
     * @return a updated list of characters
     */
    @PutMapping("/updateCharactersInMovie/{id}")
    public ResponseEntity<Movie> updateCharactersInMovie(@PathVariable Long id, @RequestBody Long[] characterIds) {
        return movieService.updateCharactersInMovie(id, characterIds);
    }

}
