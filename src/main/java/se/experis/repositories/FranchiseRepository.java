package se.experis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.models.Franchise;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {


}
