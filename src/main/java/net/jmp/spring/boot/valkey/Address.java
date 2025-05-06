package net.jmp.spring.boot.valkey;

/*
 * (#)Address.java  0.2.0   05/05/2025
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

import java.util.Objects;

/// The address class.
///
/// Kryo5 apparently does not work when
/// serializable classes actually implement
/// the Serializable readObject and writeObject
/// methods.
///
/// @version    0.2.0
/// @since      0.2.0
public class Address implements Serializable {
    /// The serial version UID.
    @Serial
    private static final long serialVersionUID = 3158623631552775700L;

    /// The name of the street.
    private String streetName;

    /// The city.
    private String city;

    /// The state.
    private String state;

    /// The zip code.
    private String zipCode;

    /// The default constructor.
    public Address() {
        super();
    }

    /// Get the name of the street.
    ///
    /// @return     java.lang.String
    public String getStreetName() {
        return this.streetName;
    }

    /// Set the name of the street.
    ///
    /// @param  streetName  java.lang.String
    public void setStreetName(final String streetName) {
        this.streetName = streetName;
    }

    /// Get the city.
    ///
    /// @return     java.lang.String
    public String getCity() {
        return this.city;
    }

    /// Set the city.
    ///
    /// @param  city    java.lang.String
    public void setCity(final String city) {
        this.city = city;
    }

    /// Get the state.
    ///
    /// @return     java.lang.String
    public String getState() {
        return this.state;
    }

    /// Set the state.
    ///
    /// @param  state   java.lang.String
    public void setState(final String state) {
        this.state = state;
    }

    /// Get the zip code.
    ///
    /// @return     java.lang.String
    public String getZipCode() {
        return this.zipCode;
    }

    /// Set the zip code.
    ///
    /// @param  zipCode java.lang.String
    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
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

        final Address address = (Address) o;

        return Objects.equals(this.streetName, address.streetName) && Objects.equals(this.city, address.city) && Objects.equals(this.state, address.state) && Objects.equals(this.zipCode, address.zipCode);
    }

    /// The default hashCode method.
    ///
    /// @return     int
    @Override
    public int hashCode() {
        return Objects.hash(this.streetName, this.city, this.state, this.zipCode);
    }
}
