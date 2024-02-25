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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * TODO.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String firstName;
    public String lastName;

    //the following attributes + tostring method are implemented to allow basic functionality
    // in the totaldebt test class
    // TODO figure out if we want to use the firstname lastname fields from the template projects
    // or our own field below
    public String name;
    public String email;
    public String iban;
    public String bic;

    /**
     * TODO.
     *
     * @param name  todo
     * @param email todo
     * @param iban  todo
     * @param bic   todo
     */
    public Person(String name, String email, String iban, String bic) {
        this.name = name;
        this.email = email;
        this.iban = iban;
        this.bic = bic;
    }


    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * The JPA required "no-arg" constructor
     */
    public Person() {

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
    //    @Override
    //    public String toString() {
    //        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    //    }
}