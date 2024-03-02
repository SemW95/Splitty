package server.database;

import commons.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Repository Interface for the Person Class.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findById(long id);

    List<Person> findByFirstName(String firstName);
}
