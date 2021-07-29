package se.experis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.models.Character;
import se.experis.models.Franchise;
import se.experis.models.Movie;
import se.experis.repositories.FranchiseRepository;
import se.experis.repositories.MovieRepository;
import se.experis.services.FranchiseService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/franchises")
public class FranchiseController {
    @Autowired
    private FranchiseService franchiseService;

    @GetMapping()
    public ResponseEntity<List<Franchise>> getAllFranchises(){
        return franchiseService.getAllFranchises();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable Long id){
        return franchiseService.getFranchise(id);
    }

    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise){
        return franchiseService.addFranchise(franchise);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable Long id, @RequestBody Franchise franchise){
        return franchiseService.updateFranchise(id,franchise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Franchise> deleteFranchise(@PathVariable Long id) {
        return franchiseService.deleteFranchise(id);
    }

    @GetMapping("/getAllMoviesInFranchise/{id}")
    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(@PathVariable Long id) {
        return franchiseService.getAllMoviesInFranchise(id);
    }

    @GetMapping("/getAllCharactersInFranchise/{movieId}")
    public ResponseEntity<List<Character>> getAllCharactersInFranchise(@PathVariable Long id) {
        return franchiseService.getAllCharactersInFranchise(id);
    }

    @PutMapping("/updateMoviesInFranchise/{franchiseId}")
    public ResponseEntity<Franchise> updateMoviesInFranchise (@PathVariable long franchiseId, @RequestBody List<Long> movieIds){
        return franchiseService.updateMoviesInFranchise(franchiseId, movieIds);
    }



}
