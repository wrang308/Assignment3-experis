package se.experis.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int characterId;

    @Column(name = "full_name")
    private String fullName;
    @Column(name = "alias")
    private String alias;
    @Column(name = "gender")
    private String gender;
    @Column(name = "picture")
    private String pictureURL;


    @ManyToMany
    @JoinTable(
            name = "character_movie",
            joinColumns = {@JoinColumn(name = "character_id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id")}
    )
    public List<Movie> movies;

    @JsonGetter("movies")
    public List<String> movies() {
        return movies.stream()
                .map(book -> {
                    return "/api/v1/books/" + movies.getId();
                }).collect(Collectors.toList());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }
}
