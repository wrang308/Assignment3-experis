package se.experis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.models.Character;
import se.experis.models.Franchise;
import se.experis.models.Movie;
import se.experis.services.FranchiseService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/franchises")
public class FranchiseController {
    @Autowired
    private FranchiseService franchiseService;

    /**
     *  Makes a request to Fetch all Franchises from the database
     * @return A ResponseEntity with a list of Franchises
     */
    @GetMapping()
    public ResponseEntity<List<Franchise>> getAllFranchises(){
        return franchiseService.getAllFranchises();
    }

    /**
     * Makes a request to fetch a Franchise by id
     * @param id Id of Franchise you want to fetch
     * @return A ResponseEntity with selected Franchise object
     */
    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable Long id){
        return franchiseService.getFranchise(id);
    }

    /**
     * Makes a request to add a Franchise object to the database
     * @param franchise Franchise object you want to add to the database
     * @return A ResponseEntity with added Franchise object
     */
    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise){
        return franchiseService.addFranchise(franchise);
    }

    /**
     * Makes a request to update a Franchise in the database
     * @param franchise A Franchise object that you want to update
     * @return A ResponseEntity with updated Franchise object
     */
    @PutMapping("/")
    public ResponseEntity<Franchise> updateFranchise(@RequestBody Franchise franchise){
        return franchiseService.updateFranchise(franchise);
    }

    /**
     * Makes a request to delete a Franchise from the database
     * @param id Id of Franchise that you want to delete
     * @return A ResponseEntity of status of delete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Franchise> deleteFranchise(@PathVariable Long id) {
        return franchiseService.deleteFranchise(id);
    }

    /**
     *  Makes a request to fetch all Movies that a Franchise have
     * @param id Id of the Franchise that you want to fetch Movies from
     * @return A ResponseEntity with a list of Movies that is present in the Franchise
     */
    @GetMapping("/getAllMoviesInFranchise/{id}")
    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(@PathVariable Long id) {
        return franchiseService.getAllMoviesInFranchise(id);
    }

    /**
     *  Makes a request to fetch all Characters that exists in the Franchise's different Movies
     * @param id Id of Franchise that you want to fetch Characters from
     * @return A ResponseEntity with a list of Characters that is present in the Franchise's Movies
     */
    @GetMapping("/getAllCharactersInFranchise/{id}")
    public ResponseEntity<List<Character>> getAllCharactersInFranchise(@PathVariable Long id) {
        return franchiseService.getAllCharactersInFranchise(id);
    }

    /**
     * Makes a request to update the Movies that a Franchise have
     * @param franchiseId Id of Franchise that you want to update Movies in
     * @param movieIds A list of Movie ids that you want the Franchise to have
     * @return A ResponseEntity with the updated Franchise
     */
    @PutMapping("/updateMoviesInFranchise/{franchiseId}")
    public ResponseEntity<Franchise> updateMoviesInFranchise (@PathVariable long franchiseId, @RequestBody List<Long> movieIds){
        return franchiseService.updateMoviesInFranchise(franchiseId, movieIds);
    }
    
}