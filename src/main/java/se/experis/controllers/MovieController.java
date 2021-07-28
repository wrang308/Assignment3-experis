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

    @GetMapping()
    public ResponseEntity<List<Movie>> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        return movieService.getMovie(id);
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
       return movieService.addMovie(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movie){
        return movieService.updateMovie(id,movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        return movieService.deleteMovie(id);
    }

    @GetMapping("/getAllCharactersInMovie/{id}")
    public ResponseEntity<List<Character>> getAllCharactersInMovie(@PathVariable Long id) {
        return movieService.getAllCharactersInMovie(id);
    }

    @PutMapping("/updateCharactersInMovie/{id}")
    public ResponseEntity<Movie> getAllCharactersInFranchise(@PathVariable Long id, @RequestBody Long[] characterIds) {
        return movieService.updateCharactersInMovie(id, characterIds);
    }

}
