package server.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import commons.Colour;
import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import commons.Tag;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


/**
 * Test fot Event repository.
 */

@DataJpaTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;
    private Event event1;
    private Event event2;
    private ArrayList<Tag> tags1;
    private ArrayList<Tag> tags2;
    private Tag tag1;
    private Tag tag2;
    private ArrayList<Person> people;
    private Instant now;

    @BeforeEach
    void setUp() {
        tags1 = new ArrayList<Tag>();
        tags2 = new ArrayList<Tag>();
        tag1 = new Tag("Blue", new Colour("#0000FF"));
        tag2 = new Tag("Pink", new Colour("#FFC0CB"));
        tags1.add(tag1);
        tags1.add(tag2);
        tags2.add(tag1);
        tags2.add(tag2);

        people = new ArrayList<Person>();
        Person receiver1 = new Person("Alice", "needs a surname", "Alice@domain.com",
            "GB33BUKB20201555555555", "ZUOBJEO6XXX");
        Person receiver2 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555556", "ZUOBJEO6XXX"); //changed last digit IBAN
        people.add(receiver1);
        people.add(receiver2);

        now = Instant.now();
        event1 = new Event("Dinner and Drinks", "Dinner and drinks with the group",
            people, tags1, new ArrayList<Expense>(),
            new ArrayList<Payment>(), now);
        event2 = new Event("Dinner and Drinks", "Dinner and drinks with the group",
            people, tags2, new ArrayList<Expense>(),
            new ArrayList<Payment>(), now);
        event1.setCode("123");
        event2.setCode("123");
        eventRepository.save(event1);
        eventRepository.save(event2);
    }

    @AfterEach
    void tearDown() {
        eventRepository.delete(event1);
        eventRepository.delete(event2);
    }
    @Test
    void findByTitleContaining() {
        List<Event> eventsContainingTitle =
            eventRepository.findByTitleContaining("Dinner and Drinks");
        assertEquals(2, eventsContainingTitle.size());
        assertEquals("Dinner and Drinks", eventsContainingTitle.getFirst().getTitle());
    }

    @Test
    void findByPeopleContaining() {
        List<Event> eventsContainingPeople = eventRepository.findByPeopleContaining(people);
        assertEquals(2, eventsContainingPeople.size());
        assertEquals("Alice", eventsContainingPeople.getFirst().getPeople().getFirst().getFirstName());
    }

    @Test
    void findByTags() {
        List<Event> eventsFoundByTags = eventRepository.findByTags(tags1).;
        assertEquals(2, eventsFoundByTags.size());
        assertEquals("Blue", eventsFoundByTags.getFirst().getTags().getFirst().getName());
    }

    @Test
    void findByCreationDate() {
        List<Event> eventsFoundByCreationDate = eventRepository.findByCreationDate(now);
        assertEquals(2, eventsFoundByCreationDate.size());
        assertEquals(now, eventsFoundByCreationDate.getFirst().getCreationDate());
    }
}