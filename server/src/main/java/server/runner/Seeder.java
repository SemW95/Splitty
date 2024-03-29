package server.runner;

import commons.Colour;
import commons.Currency;
import commons.Event;
import commons.Expense;
import commons.Payment;
import commons.Person;
import commons.Tag;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.database.ColourRepository;
import server.database.CurrencyRepository;
import server.database.EventRepository;
import server.database.ExpenseRepository;
import server.database.PaymentRepository;
import server.database.PersonRepository;
import server.database.TagRepository;

/**
 * This a seeder class for initializing default values.
 * It is run everytime the server is started up.
 */
@Component
public class Seeder implements CommandLineRunner {
    private final CurrencyRepository currencyRepository;
    private final ColourRepository colourRepository;
    private final PaymentRepository paymentRepository;
    private final ExpenseRepository expenseRepository;
    private final PersonRepository personRepository;
    private final TagRepository tagRepository;
    private final EventRepository eventRepository;


    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public Seeder(CurrencyRepository currencyRepository, ColourRepository colourRepository,
                  PaymentRepository paymentRepository, PersonRepository personRepository,
                  TagRepository tagRepository, EventRepository eventRepository,
                  ExpenseRepository expenseRepository) {
        this.currencyRepository = currencyRepository;
        this.colourRepository = colourRepository;
        this.paymentRepository = paymentRepository;
        this.personRepository = personRepository;
        this.tagRepository = tagRepository;
        this.eventRepository = eventRepository;
        this.expenseRepository = expenseRepository;
    }

    //TODO At one point make dev version to decide what gets called at startup
    @Override
    public void run(String... args) {
        //cleans the database except currencies and tags(THE ORDER MATTERS BIG -> SMALL)
        eventRepository.deleteAll();
        paymentRepository.deleteAll();
        expenseRepository.deleteAll();
        personRepository.deleteAll();

        //fills the database
        loadCurrencies();
        loadTags();
        loadDummy();
    }

    /**
     * Creates the default currencies.
     * TODO check which are not in the DB and add accordingly
     */
    private void loadCurrencies() {
        // Check if there are no currencies
        if (currencyRepository.count() == 0) {
            System.out.println("Adding default currencies...");

            Currency euro = new Currency("Euro", "EUR", '€');
            Currency dollar = new Currency("Dollar", "USD", '$');
            Currency swiss = new Currency("Swiss franc", "CHF", 'F');
            currencyRepository.save(euro);
            currencyRepository.save(dollar);
            currencyRepository.save(swiss);
        }
    }

    /**
     * Creates the default currencies.
     * TODO check which are not in the DB and add accordingly
     */
    private void loadTags() {
        // Check if there are no tags
        if (tagRepository.count() == 0) {
            System.out.println("Adding default tags...");


            Colour defaultGreen = new Colour(147, 196, 125);
            Colour defaultBlue = new Colour(74, 134, 232);
            Colour defaultRed = new Colour(224, 102, 102);
            colourRepository.save(defaultGreen);
            colourRepository.save(defaultBlue);
            colourRepository.save(defaultRed);

            Tag food = new Tag("food", defaultGreen);
            Tag entranceFee = new Tag("entrance fees", defaultBlue);
            Tag travel = new Tag("travel", defaultRed);
            tagRepository.save(food);
            tagRepository.save(entranceFee);
            tagRepository.save(travel);
        }
    }

    /**
     * Creates dummy data for dev and testing purposes.
     */
    private void loadDummy() {

        //setting up people
        Person alanTuring = new Person("Alan", "Turing", "alan@domain.com",
            "AL35202111090000000001234567",
            "ZUOBJEO6XXX");
        Person graceHopper = new Person("Grace", "Hopper", "grace@domain.com",
            "AD1400080001001234567890",
            "HABALT22TIP");
        Person johnNeumann = new Person("John", "von Neumann", "john@domain.com",
            "NL91ABNA0417164300",
            "CBVILT2X");
        List<Person> participants = List.of(alanTuring, graceHopper, johnNeumann);
        personRepository.save(alanTuring);
        personRepository.save(graceHopper);
        personRepository.save(johnNeumann);

        //setting expense
        Instant now = Instant.now();
        Tag tag = tagRepository.findTagByName("food").orElse(null);
        Expense expense = new Expense("Food", participants,
            alanTuring, new BigDecimal(24.99), tag, now);
        expenseRepository.save(expense);

        //setting event
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(1);
        List<Tag> tags = List.of(tag, tagRepository.findTagByName("entrance fees").get());
        List<Expense> expenses = List.of(expense);
        Event dinner = new Event("Celebration Dinner", "Dinner and drinks with the group",
            participants, tags, expenses,
            new ArrayList<Payment>(), start, end, now);
        dinner.setCode("1234");
        eventRepository.save(dinner);

        Event ride = new Event("Uber ride", "We took an uber to get to a restaurant",
            new ArrayList<>(), List.of(tagRepository.findTagByName("travel").get()),
            new ArrayList<>(),
            new ArrayList<>(), start, end, now.minusSeconds(60 * 60 * 24 * 5));
        ride.setCode("5678");
        eventRepository.save(ride);
    }
}
