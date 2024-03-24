package server.database;

import commons.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Interface for the Person Class.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByFirstName(String firstName);

    List<Person> findPeopleByFirstNameContainingIgnoreCase(String firstName);
}
