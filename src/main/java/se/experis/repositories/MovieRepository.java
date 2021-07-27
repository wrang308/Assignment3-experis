package se.experis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
