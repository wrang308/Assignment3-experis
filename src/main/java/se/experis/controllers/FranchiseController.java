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

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/franchises")
public class FranchiseController {
    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping()
    public ResponseEntity<List<Franchise>> getAllFranchises(){
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises,status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Franchise> getFranchise(@PathVariable Long id){
        Franchise returnFranchise = new Franchise();
        HttpStatus status;
        // We first check if the author exists, this saves some computing time.
        if(franchiseRepository.existsById(id)){
            status = HttpStatus.OK;
            returnFranchise = franchiseRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnFranchise, status);
    }

    @PostMapping
    public ResponseEntity<Franchise> addFranchise(@RequestBody Franchise franchise){
        Franchise returnFranchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Franchise> updateFranchise(@PathVariable Long id, @RequestBody Franchise franchise){
        Franchise returnFranchise = new Franchise();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if(id != franchise.getId()){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnFranchise,status);
        }
        returnFranchise = franchiseRepository.save(franchise);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnFranchise, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Franchise> deleteFranchise(@PathVariable Long id) {
        HttpStatus status = null;
       // System.out.println("id:"+id);
        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            Franchise franchise = franchiseRepository.getById(id);
            franchiseRepository.deleteById(id);
        } else {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(status);
    }

    @GetMapping("/getAllMoviesInFranchise/{id}")
    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(@PathVariable Long id) {
        List<Movie> movieList = new ArrayList<>();
        HttpStatus status = null;
        if(franchiseRepository.existsById(id)) {
            if (movieRepository.findAll() != null) {
                for (int i = 0; i < movieRepository.findAll().size(); i++) {
                    if (movieRepository.findAll().get(i).getFranchise().getId() == id) {
                        status = HttpStatus.OK;
                        movieList.add(movieRepository.findAll().get(i));
                    }
                }
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        }
        else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(movieList,status);
    }

    @GetMapping("/getAllCharactersInFranchise/{movieId}")
    public ResponseEntity<List<Character>> getAllCharactersInFranchise(@PathVariable Long movieId) {
        List<Movie> moviesInFranchise = new ArrayList<>();
        List<Character> characterList = new ArrayList<>();
        HttpStatus status = null;
        if (franchiseRepository.existsById(movieId)) {
            if (movieRepository.findAll() != null) {

                for (int i = 0; i < movieRepository.findAll().size(); i++) {
                    if (movieRepository.findAll().get(i).getFranchise().getId() == movieId) {
                        moviesInFranchise.add(movieRepository.findAll().get(i));
                    }
                }
                if(moviesInFranchise.size() != 0) {
                    status = HttpStatus.OK;
                    for(int i = 0; i<moviesInFranchise.size(); i++) {
                        for(int j=0; j<moviesInFranchise.get(i).getCharacters().size(); j++) {
                            if(!characterList.contains(moviesInFranchise.get(i).getCharacters().get(j).getId())) {
                                characterList.add(moviesInFranchise.get(i).getCharacters().get(j));
                            }
                        }
                    }
                }
                else{
                    status = HttpStatus.NOT_FOUND;
                }
            }
        }
        return new ResponseEntity<>(characterList,status);
    }



}
