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
    private Long characterId;

    @Column(name = "full_name")
    private String fullName;
    @Column(name = "alias")
    private String alias;
    @Column(name = "gender")
    private String gender;
    @Column(name = "picture_url")
    private String pictureURL;


    @ManyToMany(mappedBy = "characters")
    public List<Movie> movies;

    @JsonGetter("movies")
    public List<String> moviesGetter() {
        if(movies != null){
            return movies.stream()
                    .map(movie -> {
                        return "/api/v1/movies/" + movie.getId();
                    }).collect(Collectors.toList());
        }
        return null;
    }


    public Long getId() {
        return characterId;
    }

    public void setId(Long id) {
        this.characterId = id;
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
