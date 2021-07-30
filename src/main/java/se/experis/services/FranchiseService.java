package se.experis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    /**
     * Fetches all Franchises from the database
     * @return A list with all Franchises and HttpStatus
     */
    public ResponseEntity<List<Franchise>> getAllFranchises(){
        List<Franchise> franchises = franchiseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(franchises,status);
    }

    /**
     *  Tries to fetch Franchise by Id. If Franchise exists, return Franchise, otherwise return NOT_FOUND.
     * @param id Id for the Franchise you want to fetch from the database
     * @return A Franchise object of selected Franchise and HttpStatus
     */
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

    /**
     * Adds a Franchise to the database
     * @param franchise Franchise object you want to add to the database
     * @return The added Franchise object and HttpStatus
     */
    public ResponseEntity<Franchise> addFranchise(Franchise franchise){
        Franchise returnFranchise = franchiseRepository.save(franchise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnFranchise, status);
    }

    /**
     * updates an existing Franchise if object exists. Otherwise return NOT_FOUND
     * @param franchise Franchise object you want to update
     * @return The updated Franchise object and HttpStatus
     */
    public ResponseEntity<Franchise> updateFranchise(Franchise franchise){
        Franchise returnFranchise = new Franchise();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if( !franchiseRepository.existsById(franchise.getId())){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnFranchise,status);
        }
        returnFranchise = franchiseRepository.save(franchise);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnFranchise, status);
    }

    /**
     * Deletes Franchise from the database if selected Franchise can be found.
     * @param id Id of Franchise you want to delete
     * @return HttpStatus if delete was successful or not
     */
    public ResponseEntity<Franchise> deleteFranchise(Long id) {
        HttpStatus status = null;
        if (franchiseRepository.existsById(id)) {
            status = HttpStatus.OK;
            Franchise franchise = franchiseRepository.getById(id);
            franchiseRepository.deleteById(id);
        } else {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(status);
    }

    /**
     * Fetches all movies in a Franchise and returns the list of Movies
     * @param id Id of Franchise you want to select Movies from
     * @return A list of Movies that the Franchise have and HttpStatus
     */
    public ResponseEntity<List<Movie>> getAllMoviesInFranchise(Long id) {
        List<Movie> movieList = new ArrayList<>();
        HttpStatus status = null;
        if(franchiseRepository.existsById(id)) {
            if (movieRepository.findAll() != null) {
                for (int i = 0; i < movieRepository.findAll().size(); i++) {
                    if (movieRepository.findAll().get(i).getFranchise() != null && movieRepository.findAll().get(i).getFranchise().getId() == id) {
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

    /**
     * Fetches all Character that exists in Movies that the Franchise have
     * @param id Id of Franchise you want to select Characters from
     * @return A list of Characters that the Franchise have and HttpStatus
     */
    public ResponseEntity<List<Character>> getAllCharactersInFranchise(Long id) {
        List<Movie> moviesInFranchise = new ArrayList<>();
        List<Character> characterList = new ArrayList<>();
        HttpStatus status = null;
        if (franchiseRepository.existsById(id)) {
            if (movieRepository.findAll() != null) {

                for (int i = 0; i < movieRepository.findAll().size(); i++) {
                    if (movieRepository.findAll().get(i).getFranchise() != null && movieRepository.findAll().get(i).getFranchise().getId() == id) {
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

    /**
     * Takes a id of a Franchise and a list of Movie ids and updates the Franchises Movies to the Movies in the list.
     * Note: It doesn't keep the old Movies in the Franchise. It sets the Franchises Movies to the Movies from the list.
     * @param franchiseId Id of the Franchise you want to update
     * @param movieIds Ids of the Movies you want to add to the Franchise
     * @return A object of the updated Franchise and HttpStatus
     */
    public ResponseEntity<Franchise> updateMoviesInFranchise(long franchiseId, List<Long> movieIds) {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        Franchise franchise = new Franchise();
        Movie movie = new Movie();
        HttpStatus status = null;

        if(!franchiseRepository.existsById(franchiseId)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(franchise,status);
        }

        franchise = franchiseRepository.findById(franchiseId).get();

        for(int i = 0; i < movieIds.size(); i++) {
            if(movieRepository.existsById(movieIds.get(i).longValue())) {
                movie = movieRepository.findById(movieIds.get(i)).get();
                movieArrayList.add(movie);
            }
            else {
                status = HttpStatus.NOT_FOUND;
                return new ResponseEntity<>(franchise, status);
            }
        }

        status = HttpStatus.OK;
        franchise.setMovies(movieArrayList);
        franchiseRepository.save(franchise);
        return new ResponseEntity<>(franchise, status);
    }

}