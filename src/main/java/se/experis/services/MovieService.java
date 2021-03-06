package se.experis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import se.experis.models.Character;
import se.experis.models.Movie;
import se.experis.repositories.CharacterRepository;
import se.experis.repositories.FranchiseRepository;
import se.experis.repositories.MovieRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;
    private Object Character;

    /**
     * method to get all movies in the db
     *
     * @return a list of movies and status 200
     */
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }

    /**
     * Method for getting a specific movie
     *
     * @param id of the wanted movie
     * @return the wanted movie and http status if ok or not
     */
    public ResponseEntity<Movie> getMovie(Long id) {
        Movie returnMovie = new Movie();
        HttpStatus status;
        // We first check if the movie exists, this saves some computing time.
        if (movieRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnMovie = movieRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     * method for adding a movie to the db
     *
     * @param movie the movie to be added
     * @return the added movie and http status if it was "created"/added
     */
    public ResponseEntity<Movie> addMovie(Movie movie) {
        Movie returnMovie = movieRepository.save(movie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     * method for updating a specific movie in the db
     *
     * @param movie the wanted movie to be updated
     * @return the updated movie and http status if ok or not
     */
    public ResponseEntity<Movie> updateMovie(Movie movie) {
        Movie returnMovie = new Movie();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if (!movieRepository.existsById(movie.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnMovie, status);
        }
        returnMovie = movieRepository.save(movie);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnMovie, status);
    }

    /**
     * method for deleting a movie from the db
     *
     * @param id of the movie to be deleted
     * @return http status if ok or not
     */
    public ResponseEntity<Movie> deleteMovie(Long id) {
        HttpStatus status = null;
        System.out.println("id:" + id);
        if (movieRepository.getById(id) != null) {
            status = HttpStatus.OK;
            movieRepository.deleteById(id);
        } else {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(status);
    }

    /**
     * Method for returning all characters in a movie
     *
     * @param id of the movie
     * @return list of characters in a specific movie and http status
     */
    public ResponseEntity<List<Character>> getAllCharactersInMovie(Long id) {
        //List<Character> characterList = characterRepository.findAll();
        List<Character> characterList = new ArrayList<>();
        Movie movie = new Movie();
        HttpStatus status = null;
        if (movieRepository.getById(id) != null) {
            status = HttpStatus.OK;
            movie = movieRepository.findById(id).get();
            for (int i = 0; i < movie.getCharacters().size(); i++) {
                characterList.add(movie.getCharacters().get(i));
            }
        }
        return new ResponseEntity<>(characterList, status);
    }

    /**
     * method for updating characters in a specific movie
     *
     * @param id           of the specific movie
     * @param characterIds id array of the characters
     * @return returns the updated movie and http status
     */
    public ResponseEntity<Movie> updateCharactersInMovie(Long id, Long[] characterIds) {
        Movie returnMovie = new Movie();
        HttpStatus status;

        if (!movieRepository.existsById(id)) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnMovie, status);
        }
        returnMovie = movieRepository.findById(id).get();
        movieRepository.findById(id).get().characters = new ArrayList<>();
        for (int i = 0; i < characterIds.length; i++) {
            if (characterRepository.existsById(characterIds[i])) {
                returnMovie.characters.add(characterRepository.findById(characterIds[i]).get());

            } else {
                status = HttpStatus.NOT_FOUND;
                return new ResponseEntity<>(returnMovie, status);
            }
        }
        movieRepository.save(returnMovie);

        status = HttpStatus.OK;
        return new ResponseEntity<>(returnMovie, status);
    }

}
