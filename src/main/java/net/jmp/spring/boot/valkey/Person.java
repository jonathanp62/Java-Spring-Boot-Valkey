package net.jmp.spring.boot.valkey;

/*
 * (#)Person.java   0.2.0   05/05/2025
 *
 * @author   Jonathan Parker
 *
 * MIT License
 *
 * Copyright (c) 2025 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.io.*;

import java.util.List;
import java.util.Objects;

/// The person class.
///
/// @version    0.2.0
/// @since      0.2.0
///
public class Person implements Serializable {
    /// The serial version UID.
    @Serial
    private static final long serialVersionUID = 4908743651839331113L;

    /// The person's first name.
    private String firstName;

    /// The person's last name.
    private String lastName;

    /// The person's age.
    private int age;

    /// The person's addresses.
    private List<Address> addresses;

    /// The person's phone numbers.
    private List<String> phoneNumbers;

    /// The default constructor.
    public Person() {
        super();
    }

    /// Get the first name.
    ///
    /// @return     java.lang.String
    public String getFirstName() {
        return this.firstName;
    }

    /// Set the first name.
    ///
    /// @param  firstName   java.lang.String
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /// Get the last name.
    ///
    /// @return     java.lang.String
    public String getLastName() {
        return this.lastName;
    }

    /// Set the last name.
    ///
    /// @param  lastName    java.lang.String
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /// Get the age.
    ///
    /// @return     int
    public int getAge() {
        return this.age;
    }

    /// Set the age.
    ///
    /// @param  age int
    public void setAge(final int age) {
        this.age = age;
    }

    /// Get the addresses.
    ///
    /// @return     java.util.List<net.jmp.spring.boot.valkey.Address>
    public List<Address> getAddresses() {
        return this.addresses;
    }

    /// Set the addresses.
    ///
    /// @param  addresses   java.util.List<net.jmp.spring.boot.valkey.Address>
    public void setAddresses(final List<Address> addresses) {
        this.addresses = addresses;
    }

    /// Get the phone numbers.
    ///
    /// @return     java.util.List<java.lang.String>
    public List<String> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    /// Set the phone numbers.
    ///
    /// @param  phoneNumbers    java.util.List<java.lang.String>
    public void setPhoneNumbers(final List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    /// Method to perform the default serialization for
    /// all non-transient and non-static fields.
    ///
    /// @param  out java.io.ObjectOutputStream
    /// @throws     java.io.IOException When an I/O error occurs
    @Serial
    private void writeObject(final ObjectOutputStream out) throws IOException {
        /* Perform the default serialization for all non-transient, non-static fields */

        out.defaultWriteObject();
    }

    /// Method to perform the default
    /// de-serialization for the class.
    ///
    /// @param  in  java.io.ObjectInputStream
    /// @throws     java.io.IOException                 When an I/O error occurs
    /// @throws     java.lang.ClassNotFoundException    When the class is not found
    @Serial
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        /* Always perform the default de-serialization first */

        in.defaultReadObject();
    }

    /// The default equals method.
    ///
    /// @param  o   java.lang.Object
    /// @return     boolean
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        final Person person = (Person) o;

        return Objects.equals(this.firstName, person.firstName) && Objects.equals(this.lastName, person.lastName) && Objects.equals(this.addresses, person.addresses) && Objects.equals(this.phoneNumbers, person.phoneNumbers) && this.age == person.age;
    }

    /// The default hashCode method.
    ///
    /// @return     int
    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.age, this.addresses, this.phoneNumbers);
    }
}
