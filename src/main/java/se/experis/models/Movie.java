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

    private String movieTitle;
    private String genre;
    private String postalCode;
    private String director;
    private String picture;
    private String trailer;

    @ManyToOne
    @JoinColumn(name = "franchise")
    public Franchise franchise;

    @JsonGetter("franchise")
    public String franchise() {
        if(franchise != null){
            return "/api/v1/franchise/" + franchise.getId();
        }else{
            return null;
        }
    }

    // Book entity
    @ManyToMany(mappedBy = "characters")
    public List<Character> characters;

    @JsonGetter("characters")
    public List<String> librariesGetter() {
        if(characters != null){
            return characters.stream()
                    .map(library -> {
                        return "/api/v1/libraries/" + characters.getId();
                    }).collect(Collectors.toList());
        }
        return null;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }



}
