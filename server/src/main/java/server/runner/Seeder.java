package server.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commons.Currency;
import commons.Event;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.database.ColourRepository;
import server.database.CurrencyRepository;
import server.database.EventRepository;
import server.database.ExpenseRepository;
import server.database.PaymentRepository;
import server.database.PersonRepository;
import server.database.TagRepository;
import server.service.EventService;

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
    private final EventService eventService;


    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public Seeder(CurrencyRepository currencyRepository, ColourRepository colourRepository,
                  PaymentRepository paymentRepository, PersonRepository personRepository,
                  TagRepository tagRepository, EventRepository eventRepository,
                  ExpenseRepository expenseRepository,
                  EventService eventService) {
        this.currencyRepository = currencyRepository;
        this.colourRepository = colourRepository;
        this.paymentRepository = paymentRepository;
        this.personRepository = personRepository;
        this.tagRepository = tagRepository;
        this.eventRepository = eventRepository;
        this.expenseRepository = expenseRepository;
        this.eventService = eventService;
    }

    @Override
    public void run(String... args) {
        // Don't seed the database if '--seed' is not provided as an argument
        if (!Arrays.stream(args).toList().contains("--seed")) {
            return;
        }

        System.out.println("- Resetting the database first");
        eventRepository.deleteAll();
        expenseRepository.deleteAll();
        paymentRepository.deleteAll();
        personRepository.deleteAll();
        tagRepository.deleteAll();
        colourRepository.deleteAll();
        currencyRepository.deleteAll();

        System.out.println("- Seeding the database");
        loadCurrencies();
        loadDummy();
    }

    /**
     * Creates the default currencies.
     */
    private void loadCurrencies() {
        System.out.println("- Adding default currencies");

        Currency euro = new Currency("Euro", "EUR", '€');
        Currency dollar = new Currency("Dollar", "USD", '$');
        Currency swiss = new Currency("Swiss franc", "CHF", 'F');
        currencyRepository.save(euro);
        currencyRepository.save(dollar);
        currencyRepository.save(swiss);
    }

    /**
     * Loads dummy data from save files.
     */
    private void loadDummy() {
        System.out.println("- Adding default events");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(
            Path.of("src/main/resources/events"))) {
            for (Path path : stream) {
                String eventJson = Files.readString(path);
                Event event =
                    new ObjectMapper().registerModule(new JavaTimeModule())
                        .readValue(eventJson, Event.class);

                eventService.importEvent(event);
            }
        } catch (IOException e) {
            System.err.println("Could not get the default events");
        }
    }
}
