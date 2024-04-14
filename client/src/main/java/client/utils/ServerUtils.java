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
import commons.Payment;
import commons.Person;
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

    // /**
    //  * Gets all tags in the system.
    //  *
    //  * @return list of tags
    //  */
    // public List<Tag> getTags() {
    //     return ClientBuilder.newClient(new ClientConfig())
    //         .target(server).path("/tag")
    //         .request(APPLICATION_JSON)
    //         .accept(APPLICATION_JSON)
    //         .get(new GenericType<>() {
    //         });
    // }

    /**
     * Gets all events in the system.
     * Note: should only be used by an admin.
     *
     * @return list of events
     */
    public List<Event> getEvents(String adminPassword) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/admin/event")
                .queryParam("password", adminPassword)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
        } catch (Exception e) {
            System.err.println("Something went wrong: " + e);
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
        Expense oldExpense = getExpenseById(expense.getId());
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
    private Expense createExpense(Expense expense) {
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
        Event oldEvent = getEventById(event.getId());
        undoStack.add(() -> justUpdateEvent(oldEvent));
        justUpdateEvent(event);
    }

    private void justUpdateEvent(Event event) {
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
    private Person createPerson(Person person) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/person")
                .request(APPLICATION_JSON)
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
        Person oldPerson = getPersonById(person.getId());
        undoStack.add(() -> justUpdatePerson(oldPerson));
        justUpdatePerson(person);
    }

    private void justUpdatePerson(Person person) {
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
    private void deletePerson(Person person) {
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

    /**
     * Deletes an expense.
     *
     * @param expense the expense to delete
     */
    private void deleteExpense(Expense expense) {
        try {
            ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/expense/" + expense.getId())
                .request(APPLICATION_JSON)
                .delete();
        } catch (Exception e) {
            System.err.println("Server did not respond");
        }
    }

    /**
     * Create a new payment in the database.
     *
     * @param payment the payment to create
     * @return the created payment with updated fields (id is created)
     */
    private Payment createPayment(Payment payment) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/payment")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.json(payment))
                .readEntity(Payment.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets a payment by its id.
     *
     * @param id the payment's id
     * @return the requested payment
     */
    public Payment getPaymentById(String id) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/payment/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Payment.class);
        } catch (Exception e) {
            System.err.println("Could not get payment by its id: " + e);
            return null;
        }
    }

    /**
     * Updates a payment in the database.
     *
     * @param payment the payment to update
     */
    public void updatePayment(Payment payment) {
        Payment oldPayment = getPaymentById(payment.getId());
        undoStack.add(() -> justUpdatePayment(oldPayment));
        justUpdatePayment(payment);
    }

    private void justUpdatePayment(Payment payment) {
        try {
            ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/payment")
                .request(APPLICATION_JSON)
                .put(Entity.json(payment));
        } catch (Exception e) {
            System.err.println("Could not update payment: " + e);
        }
    }

    /**
     * Deletes a payment.
     *
     * @param payment the payment to delete
     */
    private void deletePayment(Payment payment) {
        try {
            ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/payment/" + payment.getId())
                .request(APPLICATION_JSON)
                .delete();
        } catch (Exception e) {
            System.err.println("Could not delete payment: " + e);
        }
    }

    /**
     * Fully imports the event.
     *
     * @param event the event to import
     * @return the imported event
     */
    public Event importEvent(Event event) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                .target(server).path("/event/import")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.json(event))
                .readEntity(Event.class);
        } catch (Exception e) {
            System.err.println("Could not import the event: " + e);
            return null;
        }
    }

    /**
     * Create a payment and add it to an event.
     *
     * @param payment the payment
     * @param event   the event
     * @return the created payment
     */
    public Payment createPaymentForEvent(Payment payment, Event event) {
        Event oldEvent = getEventById(event.getId());
        Payment createdPayment = createPayment(payment);
        event.getPayments().add(createdPayment);

        undoStack.add(() -> {
            justUpdateEvent(oldEvent);
            deletePayment(createdPayment);
        });

        justUpdateEvent(event);

        return createdPayment;
    }

    /**
     * Create an expense and add it to an event.
     *
     * @param expense the expense
     * @param event   the event
     * @return the created expense
     */
    public Expense createExpenseForEvent(Expense expense, Event event) {
        Event oldEvent = getEventById(event.getId());
        Expense createdExpense = createExpense(expense);

        undoStack.add(() -> {
            justUpdateEvent(oldEvent);
            deleteExpense(createdExpense);
        });

        event.getExpenses().add(createdExpense);
        justUpdateEvent(event);

        return createdExpense;
    }

    /**
     * Create a person and add it to an event.
     *
     * @param person the person
     * @param event  the event
     * @return the created person
     */
    public Person createPersonForEvent(Person person, Event event) {
        Event oldEvent = getEventById(event.getId());
        Person createdPerson = createPerson(person);

        undoStack.add(() -> {
            justUpdateEvent(oldEvent);
            deletePerson(createdPerson);
        });

        event.getPeople().add(createdPerson);
        justUpdateEvent(event);

        return createdPerson;
    }

    /**
     * Delete a payment from an event.
     *
     * @param payment the payment
     * @param event   the event
     */
    public void deletePaymentFromEvent(Payment payment, Event event) {
        Payment oldPayment = getPaymentById(payment.getId());
        Event oldEvent = getEventById(event.getId());
        oldEvent.getPayments().remove(payment);

        undoStack.add(() -> {
            Payment newPayment = createPayment(oldPayment);
            oldEvent.getPayments().add(newPayment);
            justUpdateEvent(oldEvent);
        });

        event.getPayments().remove(payment);
        justUpdateEvent(event);
        deletePayment(payment);
    }

    /**
     * Delete an expense from an event.
     *
     * @param expense the expense
     * @param event   the event
     */
    public void deleteExpenseFromEvent(Expense expense, Event event) {
        Expense oldExpense = getExpenseById(expense.getId());
        Event oldEvent = getEventById(event.getId());
        oldEvent.getExpenses().remove(expense);

        undoStack.add(() -> {
            Expense newExpense = createExpense(oldExpense);
            oldEvent.getExpenses().add(newExpense);
            justUpdateEvent(oldEvent);
        });

        event.getExpenses().remove(expense);
        justUpdateEvent(event);
        deleteExpense(expense);
    }

    /**
     * Delete a person from an event.
     *
     * @param person the person
     * @param event  the event
     */
    public void deletePersonFromEvent(Person person, Event event) {
        Person oldPerson = getPersonById(person.getId());
        Event oldEvent = getEventById(event.getId());
        oldEvent.getPeople().remove(person);

        undoStack.add(() -> {
            Person newPerson = createPerson(oldPerson);
            oldEvent.getPeople().add(newPerson);
            justUpdateEvent(oldEvent);
        });

        event.getPeople().remove(person);
        justUpdateEvent(event);
        deletePerson(person);
    }
}