package server.database;

import commons.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Interface for the Person Class.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findById(long id);

    List<Person> findByFirstName(String firstName);

    List<Person> findPeopleByFirstNameContainingIgnoreCase(String firstName);
}
