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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.glassfish.jersey.client.ClientConfig;

/**
 * A singleton that contains some server utility methods.
 */
public class ServerUtils {
    private final String server = Main.configManager.getHttpServer();

    /**
     * A stack of callbacks which upon running will undo an update.
     */
    private final Stack<Runnable> undoStack = new Stack<>();

    /**
     * Will try to undo an update.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            undoStack.pop().run();
            System.out.println("- UNDID something");
        }
    }

    /**
     * Manually add an undo onto the undo stack.
     *
     * @param anUndo a runnable which will redo a change
     */
    public void addAnUndo(Runnable anUndo) {
        undoStack.add(anUndo);
    }

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
    public Event getEventByCode(String code) throws ServerException {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/event/code/" + code)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Event.class);
        } catch (WebApplicationException e) {
            if (e.getResponse().getStatus() == 404) {
                return null;
            }
            throw new ServerException(e.toString());
        } catch (Exception e) {
            throw new ServerException(e.toString());
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
            String newId = ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/event")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.json(event))
                .readEntity(String.class);
            event.setId(newId);
            return event;
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
        Expense oldExpense = getExpenseById(expense.getId());
        System.out.println("- ADDED UNDO");
        undoStack.add(() -> justUpdateExpense(oldExpense));
        justUpdateExpense(expense);
    }

    private void justUpdateExpense(Expense expense) {
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
     * Tries to create an expense.
     *
     * @param expense the expense to be created.
     * @return the created expense
     */
    public Expense createExpense(Expense expense) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/expense")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.json(expense))
                .readEntity(Expense.class);
        } catch (Exception e) {
            System.err.println("Couldn't create expense: " + e);
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
     * Gets status of the server using long polling.
     * So it will only return the result after some amount of time.
     */
    public boolean serverOnline() {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/status")
                .request()
                .get()
                .getStatus() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}