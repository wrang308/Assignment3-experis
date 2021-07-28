package se.experis.models;
import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_title")
    private String movieTitle;
    @Column(name = "genre")
    private String genre;
    @Column(name = "release_year")
    private int releaseYear;
    @Column(name = "director")
    private String director;
    @Column(name = "picture_url")
    private String pictureURL;
    @Column(name = "trailer_url")
    private String trailerURL;

    @ManyToOne
    @JoinColumn(name = "franchise_id")
    public Franchise franchise;

    @JsonGetter("franchise")
    public String franchise() {
        if(franchise != null){
            return "/api/v1/franchises/" + franchise.getId();
        }else{
            return null;
        }
    }

    // Book entity
    @ManyToMany
    @JoinTable(
            name = "movie_character",
            joinColumns = {@JoinColumn(name = "character_id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id")}
    )
    public List<Character> characters;

    @JsonGetter("characters")
    public List<String> characters() {
        return characters.stream()
                .map(character -> {
                    return "/api/v1/characters/" + character.getId();
                }).collect(Collectors.toList());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String picture) {
        this.pictureURL = picture;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailer) {
        this.trailerURL = trailer;
    }

    public Franchise getFranchise() {
        return franchise;
    }

    public List<Character> getCharacters() {
        return characters;
    }



}
