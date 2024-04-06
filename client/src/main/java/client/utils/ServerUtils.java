/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import client.Main;
import commons.Event;
import commons.Expense;
import commons.Person;
import commons.Tag;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.messaging.WebSocketStompClient;

/**
 * A singleton that contains some server utility methods.
 */
@Configuration
@EnableWebSocketMessageBroker
public class ServerUtils {
    private final String server = Main.configManager.getServer();

    /**
     * Validates an admin password.
     *
     * @param password the admin password to check
     * @return whether the admin password is correct
     */
    public boolean validateAdminPassword(String password) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/admin/validate/" + password)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Boolean.class);
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Gets all persons in the system.
     *
     * @return list of persons
     */
    public List<Person> getPeople() {
        /*
            This method talks to the endpoint by initializing a client and sending
            a GET request to the requested endpoint and receiving specified type.
         */
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/person")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<>() {
            });

    }

    /**
     * Gets all tags in the system.
     *
     * @return list of tags
     */
    public List<Tag> getTags() {
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/tag")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<>() {
            });

    }

    /**
     * Gets all events in the system.
     * Note: should only be used by an admin.
     *
     * @return list of events
     */
    public List<Event> getEvents() {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/event")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    /**
     * Create an event based on eventCode.
     *
     * @return list of events
     */
    public Event getEventByCode(String code) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/event/code/" + code)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Event.class);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Gets an event by its id.
     *
     * @param id the id
     * @return the event
     */
    public Event getEventById(String id) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/event/id/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Event.class);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Deletes an event.
     * Note: should only be used by an admin
     *
     * @param event         the event to delete
     * @param adminPassword the admin password
     */
    public void deleteEvent(Event event, String adminPassword) {
        try {
            ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/admin/event/" + event.getId())
                .queryParam("password", adminPassword)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .delete();
        } catch (Exception e) {
            System.err.println("Server did not respond");
        }
    }

    /**
     * Tries to create an event.
     *
     * @param event the event to be created.
     */
    public Event createEvent(Event event) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/event")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.json(event))
                .readEntity(Event.class);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * This updates the database expense (persistence).
     *
     * @param expense the expense to update
     */
    public void updateExpense(Expense expense) {
        try {
            ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/expense")
                .request(APPLICATION_JSON)
                .put(Entity.json(expense));
        } catch (Exception e) {
            System.err.println("Server did not respond");
        }
    }

    /**
     * Gets an expense by its id.
     *
     * @param id the id
     * @return the expense
     */
    public Expense getExpenseById(String id) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/expense/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Expense.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This updates the event in the database.
     *
     * @param event the event to update
     */
    public void updateEvent(Event event) {
        try {
            ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/event")
                .request(APPLICATION_JSON)
                .put(Entity.json(event));
        } catch (Exception e) {
            System.err.println("Server did not respond");
        }

    }

    /**
     * Create a new person in the database.
     *
     * @param person the person to create
     * @return the created person with updated fields (id is created)
     */
    public Person createPerson(Person person) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/person")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.json(person))
                .readEntity(Person.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Updates a person in the database.
     *
     * @param person the person to persist
     */
    public void updatePerson(Person person) {
        try {
            ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/person")
                .request(APPLICATION_JSON)
                .put(Entity.json(person));
        } catch (Exception e) {
            System.err.println("Server did not respond");
        }

    }

    /**
     * Gets a person by its id.
     *
     * @param id the person's id
     * @return the requested person
     */
    public Person getPersonById(String id) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/person/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Person.class);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Deletes a person.
     *
     * @param person the person to delete
     */
    public void deletePerson(Person person) {
        try {
            ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/person/" + person.getId())
                .request(APPLICATION_JSON)
                .delete();
        } catch (Exception e) {
            System.err.println("Server did not respond");
        }

    }

    /**
     * Gets status of the server.
     */
    public int getStatus() {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/status")
                .request()
                .get()
                .getStatus();
        } catch (Exception e) {
            return 404;
        }
    }

    private StompSession session = connect("ws://localhost:8080/websocket"); // TODO change to not hardcoded

    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {
            }).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    public <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    public void send(String dest, Object o){
        session.send(dest, o);
    }
}