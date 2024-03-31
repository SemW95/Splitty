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
import java.util.List;
import org.glassfish.jersey.client.ClientConfig;

/**
 * A singleton that contains some server utility methods.
 */
public class ServerUtils {
    private final String server = Main.configManager.getServer();

    /**
     * Validates an admin password.
     *
     * @param password the admin password to check
     * @return whether the admin password is correct
     */
    public boolean validateAdminPassword(String password) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/admin/validate/" + password)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(Boolean.class);
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
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/event")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<>() {
            });
    }

    /**
     * Create an event based on eventCode.
     *
     * @return list of events
     */
    public Event getEventByCode(String code) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/event/code/" + code)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(Event.class);
    }

    /**
     * Gets an event by its id.
     *
     * @param id the id
     * @return the event
     */
    public Event getEventById(String id) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/event/id/" + id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(Event.class);
    }

    /**
     * Deletes an event.
     * Note: should only be used by an admin
     *
     * @param event         the event to delete
     * @param adminPassword the admin password
     */
    public void deleteEvent(Event event, String adminPassword) {
        ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/admin/event/" + event.getId())
            .queryParam("password", adminPassword)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .delete();
    }

    /**
     * Tries to create an event.
     *
     * @param event the event to be created.
     */
    public Event createEvent(Event event) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/event")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.json(event))
            .readEntity(Event.class);
    }

    /**
     * This updates the database expense (persistence).
     *
     * @param expense the expense to update
     */
    public void updateExpense(Expense expense) {
        ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/expense")
            .request(APPLICATION_JSON)
            .put(Entity.json(expense));
    }

    /**
     * Gets an expense by its id.
     *
     * @param id the id
     * @return the expense
     */
    public Expense getExpenseById(String id) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/expense/" + id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(Expense.class);
    }

    /**
     * This updates the event in the database.
     *
     * @param event the event to update
     */
    public void updateEvent(Event event) {
        ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/event")
            .request(APPLICATION_JSON)
            .put(Entity.json(event));
    }

    /**
     * Create a new person in the database.
     *
     * @param person the person to create
     * @return the created person with updated fields (id is created)
     */
    public Person createPerson(Person person) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/person")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.json(person))
            .readEntity(Person.class);
    }

    /**
     * Updates a person in the database.
     *
     * @param person the person to persist
     */
    public void updatePerson(Person person) {
        ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/person")
            .request(APPLICATION_JSON)
            .put(Entity.json(person));
    }

    /**
     * Gets a person by its id.
     *
     * @param id the person's id
     * @return the requested person
     */
    public Person getPersonById(String id) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/person/" + id)
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(Person.class);
    }

    /**
     * Deletes a person.
     *
     * @param person the person to delete
     */
    public void deletePerson(Person person) {
        ClientBuilder.newClient(new ClientConfig())
            .target(server).path("/person/" + person.getId())
            .request(APPLICATION_JSON)
            .delete();
    }
}