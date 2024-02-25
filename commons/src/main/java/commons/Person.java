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
import java.util.regex.Pattern;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
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

    //the following attributes + toString method are implemented to allow basic functionality
    // in the totalDebt test class
    // TODO figure out if we want to use the firstname lastname fields from the template projects
    // or our own field below
    public String name;
    public String email;
    public String iban;
    public String bic;

    /**
     * This is a constructor for the Person class.
     *
     * @param name  of person
     * @param email of person
     * @param iban  of person
     * @param bic   of person
     */
    public Person(String name, String email, String iban, String bic) {
        this.name = name;
        this.email = email;
        this.iban = iban;
        this.bic = bic;
    }


    /**
     * TODO: consider if this is still necessary.
     *
     * @param firstName of person
     * @param lastName of person
     */
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    /**
     * The JPA required "no-arg" constructor.
     */
    public Person() {

    }


    /**
     * TODO: Should use IBAN checker api, to verify IBAN.
     *
     * @param iban takes an iban number
     *
     * @return a boolean if it is a correct/existing iban.
     */
    public boolean ibanCheckSum(String iban) {
        return true;
    }


    /**
     *TODO: Should use BIC api, to verify BIC.
     *
     * @param bic takes a bic number
     *
     * @return a boolean if it is a correct/existing bic.
     */
    public boolean bicCheckSum(String bic) {
        return true;
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
        return "Person{" + "name='" + name + '\'' + ", email='" + email + '\'' + ", IBAN='" + iban
            + '\'' + ", BIC='" + bic + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        boolean check = Pattern.matches("^(.+)@(\\S+)$", email);

        if (check) {
            this.email = email;
        } else {
            throw new IllegalArgumentException();
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
            System.out.println("This is an incorrect IBAN");
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
            System.out.println("This is an incorrect BIC");
        }
    }

    //    @Override
    //    public String toString() {
    //        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    //    }
}