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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for the person class.
 */
public class PersonTest {

    @Test
    void equalTest() {
        Person p1 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555555", "ZUOBJEO6XXX");
        Person p2 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555555", "ZUOBJEO6XXX"); //same as P1
        Person p3 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555556", "ZUOBJEO6XXX"); //changed last digit IBAN

        assertNotEquals(p1, p3);

        assertEquals(p1, p2);
    }


    @Test
    void hashTest() {
        Person p1 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555555", "ZUOBJEO6XXX");
        Person p2 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555555", "ZUOBJEO6XXX"); //same as P1
        Person p3 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555556", "ZUOBJEO6XXX"); //changed last digit IBAN

        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertEquals(p1.hashCode(), p2.hashCode());
    }


    @Test
    void toStringTest() {
        Person p1 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555555", "ZUOBJEO6XXX");

        String text = p1.toString();
        String compare = "Person{id=0, firstName='Alice', lastName='needs a surname', "
            + "email='Alice@domain.com', iban='GB33BUKB20201555555555', bic='ZUOBJEO6XXX'}";

        assertEquals(text, compare);
    }

    @Test
    void emailCheck() {
        Person p1 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555555", "ZUOBJEO6XXX");
        p1.setEmail("Peter@domain.com");

        assertEquals("Peter@domain.com", p1.getEmail());

        assertThrows(IllegalArgumentException.class, () -> {
            p1.setEmail("Invalid#domain.com");
        });

    }

    @Test
    void ibanCheck() {
        Person p1 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555555", "ZUOBJEO6XXX");

        //sets proper iban
        p1.setIban("NL91ABNA0417164300");
        assertEquals("NL91ABNA0417164300", p1.getIban());

        //different countries
        p1.setIban("RO49AAAA1B31007593840000");
        assertEquals("RO49AAAA1B31007593840000", p1.getIban());
        p1.setIban("MF8420041010050500013M02606");
        assertEquals("MF8420041010050500013M02606", p1.getIban());
        p1.setIban("LV80BANK0000435195001");
        assertEquals("LV80BANK0000435195001", p1.getIban());
        p1.setIban("JO94CBJO0010000000000131000302");
        assertEquals("JO94CBJO0010000000000131000302", p1.getIban());
        p1.setIban("LT121000011101001000");
        assertEquals("LT121000011101001000", p1.getIban());
    }

    @Test
    void ibanCheckIllegal() {
        Person p1 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555555", "ZUOBJEO6XXX");

        //too short
        assertThrows(IllegalArgumentException.class, () -> {
            p1.setIban("NL02ABNA01");
        });

        //too long
        assertThrows(IllegalArgumentException.class, () -> {
            p1.setIban("NL02ABNA0100000000000000000000000000000000000000");
        });

        //incorrect checksum
        assertThrows(IllegalArgumentException.class, () -> {
            p1.setIban("NL91ABNA0417164301");
        });
    }

    @Test
    void bicCheck() {
        Person p1 =
            new Person("Alice", "needs a surname", "Alice@domain.com",
                "GB33BUKB20201555555555", "ZUOBJEO6XXX");
        p1.setBic("ZUOBJEO6XXY");

        assertEquals("ZUOBJEO6XXY", p1.getBic());
    }

}
