package se.experis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> movies = movieRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(movies,status);
    }

    public ResponseEntity<Movie> getMovie(Long id){
        Movie returnMovie = new Movie();
        HttpStatus status;
        // We first check if the movie exists, this saves some computing time.
        if(movieRepository.existsById(id)){
            status = HttpStatus.OK;
            returnMovie = movieRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnMovie, status);
    }

    public ResponseEntity<Movie> addMovie(Movie movie){
        Movie returnMovie = movieRepository.save(movie);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnMovie, status);
    }

    public ResponseEntity<Movie> updateMovie(Long id, Movie movie){
        Movie returnMovie = new Movie();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if(id != movie.getId()){
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnMovie,status);
        }
        returnMovie = movieRepository.save(movie);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnMovie, status);
    }

    public ResponseEntity<Movie> deleteMovie(Long id) {
        HttpStatus status = null;
        System.out.println("id:"+id);
        if (movieRepository.getById(id) != null) {
            status = HttpStatus.OK;
            movieRepository.deleteById(id);
        } else {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(status);
    }

    public ResponseEntity<List<Character>> getAllCharactersInMovie(Long id) {
        //List<Character> characterList = characterRepository.findAll();
        List<Character> characterList = new ArrayList<>();
        Movie movie = new Movie();
        HttpStatus status = null;
        if(movieRepository.getById(id) != null) {
            status = HttpStatus.OK;
            movie = movieRepository.findById(id).get();
            for(int i=0; i<movie.getCharacters().size(); i++) {
                characterList.add(movie.getCharacters().get(i));
            }
        }
        return new ResponseEntity<>(characterList,status);
    }

    public ResponseEntity<Movie> updateCharactersInMovie(Long id, Long[] characterIds){
        Movie returnMovie = new Movie();
        HttpStatus status;

        if(!movieRepository.existsById(id)){
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(returnMovie,status);
        }
        returnMovie = movieRepository.findById(id).get();
        movieRepository.findById(id).get().characters = new ArrayList<>();
        for (int i = 0; i < characterIds.length; i++) {
            if(characterRepository.existsById(characterIds[i])) {
                returnMovie.characters.add(characterRepository.findById(characterIds[i]).get());

            }else {
                status = HttpStatus.NOT_FOUND;
                return new ResponseEntity<>(returnMovie,status);
            }
        }
        movieRepository.save(returnMovie);

        status = HttpStatus.OK;
        return new ResponseEntity<>(returnMovie, status);
    }

}
