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

package commons;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * This is the class that contains all the data that's needed for a person.
 * Object class of a Person as an entity,
 * with name, email, iban and bic.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String firstName;
    public String lastName;
    public String email;
    public String iban;
    public String bic;

    /**
     * Makes the Person class.
     *
     * @param firstName The first name of a Person.
     * @param lastName  The last name of a Person.
     * @param email     The email of a Person.
     * @param iban      The IBAN of a Persons bank account.
     * @param bic       The IBAN of a Persons bank.
     */
    public Person(String firstName, String lastName, String email, String iban, String bic) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.iban = iban;
        this.bic = bic;
    }


    /**
     * The JPA required "no-arg" constructor.
     */
    public Person() {

    }


    /**
     * Verifies the checksum of iban by moving char 0 to 4 to the end and validating
     * by mapping numeric values and checking modulo 97.
     *
     * @param iban takes an iban number (needs to be trimmed when stored in database)
     * @return a boolean if it is a correct/existing iban.
     */
    static public boolean ibanCheckSum(String iban) {
        //checks if the iban is of valid length
        if (iban.length() < 15 || iban.length() > 34) {
            return false;
        }

        //takes char at index 2 and 3 and moves them to the end
        String toCheck = iban.substring(4) + iban.substring(0, 4);
        int checkSum = 0;

        //maps every char to its numeric value
        for (char character : toCheck.toCharArray()) {
            int value = Character.getNumericValue(character);

            if (value <= 9) {
                checkSum = checkSum * 10 + value;
            } else {
                checkSum = checkSum * 100 + value;
            }

            //makes sure it does not overflow
            if (checkSum > 9999999) {
                checkSum = checkSum % 97;
            }
        }

        return checkSum % 97 == 1;
    }


    /**
     * TODO: Should use BIC api, to verify BIC.
     *
     * @param bic takes a bic number
     * @return a boolean if it is a correct/existing bic.
     */
    static public boolean bicCheckSum(String bic) {
        String bicRegex = "^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z]{3})$";
        Pattern pattern = Pattern.compile(bicRegex);
        Matcher bicMatcher = pattern.matcher(bic);
        return bicMatcher.matches();
    }


    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "Person{"
            + "id=" + id
            + ", firstName='" + firstName + '\''
            + ", lastName='" + lastName + '\''
            + ", email='" + email + '\''
            + ", iban='" + iban + '\''
            + ", bic='" + bic + '\''
            + '}';
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Checks whether email is of the right format.
     *
     * @param email new email for Person
     */
    public void setEmail(String email) {
        boolean check = email.matches("^(.+)@(\\S+)$");

        if (check) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("The provided email is not a valid email");
        }
    }


    public String getIban() {
        return iban;
    }

    /**
     * Makes sure the IBAN is correct when setting it.
     *
     * @param iban new iban for person
     */
    public void setIban(String iban) {
        if (ibanCheckSum(iban)) {
            this.iban = iban;
        } else {
            throw new IllegalArgumentException("This is not a valid IBAN");
        }
    }


    public String getBic() {
        return bic;
    }

    /**
     * Makes sure the BIC is correct before setting it.
     *
     * @param bic new BIC of person.
     */
    public void setBic(String bic) {
        if (bicCheckSum(bic)) {
            this.bic = bic;
        } else {
            throw new IllegalArgumentException("This is an incorrect BIC");
        }
    }
}