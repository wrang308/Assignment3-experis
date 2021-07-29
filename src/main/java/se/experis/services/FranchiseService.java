package se.experis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import se.experis.models.Character;
import se.experis.models.Franchise;
import se.experis.models.Movie;
import se.experis.repositories.FranchiseRepository;
import se.experis.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FranchiseService {
    @Autowired
    private FranchiseRepository franchiseRepository;

    @Autowired
    private MovieRepository movieRepository;

    public ResponseEntity<List<Franchise>> getAllFranchises(){
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises,status);
    }

    public ResponseEntity<Franchise> getFranchise(Long id){
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

    public ResponseEntity<Franchise> addFranchise(Franchise franchise){
        Franchise returnFranchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }

    public ResponseEntity<Franchise> updateFranchise(Long id, Franchise franchise){
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

    public ResponseEntity<Franchise> deleteFranchise(Long id) {
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

    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(Long id) {
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

    public ResponseEntity<List<Character>> getAllCharactersInFranchise(Long id) {
        List<Movie> moviesInFranchise = new ArrayList<>();
        List<Character> characterList = new ArrayList<>();
        HttpStatus status = null;
        if (franchiseRepository.existsById(id)) {
            if (movieRepository.findAll() != null) {

                for (int i = 0; i < movieRepository.findAll().size(); i++) {
                    if (movieRepository.findAll().get(i).getFranchise().getId() == id) {
                        moviesInFranchise.add(movieRepository.findAll().get(i));
                    }
                }
                if(moviesInFranchise.size() != 0) {
                    status = HttpStatus.OK;
                    for(int i = 0; i<moviesInFranchise.size(); i++) {
                        for(int j=0; j<moviesInFranchise.get(i).getCharacters().size(); j++) {
                            if(!characterList.contains(moviesInFranchise.get(i).getCharacters().get(j))) {
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

    public ResponseEntity<Franchise> updateMoviesInFranchise(long franchiseId, List<Long> movieIds) {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        Franchise franchise = new Franchise();
        Movie movie = new Movie();
        HttpStatus status = null;

        if(franchiseRepository.existsById(franchiseId)) {
            franchise = franchiseRepository.findById(franchiseId).get();
            for(int i=0; i<movieIds.size(); i++) {

                if(movieRepository.existsById(movieIds.get(i))) {
                    movie = movieRepository.findById(movieIds.get(i)).get();
                    movieArrayList.add(movie);
                }
                else {
                    status = HttpStatus.NOT_FOUND;
                    return new ResponseEntity<>(franchise, status);
                }
            }
        }
        status = HttpStatus.OK;
        franchise.setMovies(movieArrayList);
        franchiseRepository.save(franchise);
        return new ResponseEntity<>(franchise, status);
    }

}
