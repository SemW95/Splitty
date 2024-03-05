package server.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import commons.Colour;
import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import commons.Tag;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
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
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ColourRepository colorRepository;
    @Autowired
    private PersonRepository personRepository;
    private Event event1;
    private Event event2;
    private List<Tag> tags1;
    private List<Tag> tags2;
    private Tag tag1;
    private Tag tag2;

    private List<Person> people2;
    private List<Person> people1;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        tags1 = new ArrayList<Tag>();
        tags2 = new ArrayList<Tag>();

        // Saving colors to the repo such that the database is not burning in the eternal flame
        var col1 = colorRepository.save(new Colour("#0000FF"));
        var col2 = colorRepository.save(new Colour("#FFC0CB"));
        tag1 = new Tag("Food", col1);
        tag2 = new Tag("Drive", col2);

        // Save tags such that the objects are persistent, otherwise the database will scream in pain..
        tag1 = tagRepository.save(tag1);
        tag2 = tagRepository.save(tag2);

        tags1.add(tag1);
        tags2.add(tag2);


        people1 = new ArrayList<Person>();
        people2 = new ArrayList<Person>();
        Person receiver1 =
            personRepository.save(new Person("Alice", "needs a surname", "Alice@domain.com",
                "AL35202111090000000001234567", "ZUOBJEO6XXX"));
        Person receiver2 =
            personRepository.save(new Person("John", "needs a surname", "john@domain.com",
                "AD1400080001001234567890", "ZUOBJEO6XXX")); //different valid ibn
        people1.add(receiver1);
        people2.add(receiver2);

        now = LocalDateTime.now();
        event1 =
            eventRepository.save(
                new Event("Dinner and Drinks", "Dinner and drinks with the group",
                    new ArrayList<>(), tags1, new ArrayList<Expense>(),
                    new ArrayList<Payment>(), now)
            );
        event2 = eventRepository.save(new Event("Uber drive", "Uber drive with friends",
            new ArrayList<>(), tags2, new ArrayList<Expense>(),
            new ArrayList<Payment>(), now.minusSeconds(10)));

        event1.setPeople(people1);
        event1.setPeople(people2);

        tags1.add(tagRepository.save(new Tag()));
    }

//    @AfterEach
//    void tearDown() {
//        eventRepository.delete(event1);
//        eventRepository.delete(event2);
//        tagRepository.delete(tag1);
//        tagRepository.delete(tag2);
//        // TODO: delete others
//    }

    @Test
    void findByTitleContaining() {
        List<Event> drinkEvents =
            eventRepository.findByTitleContainingIgnoreCase("drinks");
        assertEquals(1, drinkEvents.size());
        assertEquals("Dinner and Drinks", drinkEvents.getFirst().getTitle());
    }

    @Test
    void findByTags() {
        List<Event> eventsFoundByTags = eventRepository.findByTagsIn(tags1);
        assertEquals(1, eventsFoundByTags.size());
        assertEquals("Food", eventsFoundByTags.getFirst().getTags().getFirst().getName());
    }

    @Test
    void findByCreationDate() {
        List<Event> eventsFoundByCreationDate = eventRepository.findByCreationDate(now);
        assertEquals(1, eventsFoundByCreationDate.size());
        assertEquals(now, eventsFoundByCreationDate.getFirst().getCreationDate());
    }
}