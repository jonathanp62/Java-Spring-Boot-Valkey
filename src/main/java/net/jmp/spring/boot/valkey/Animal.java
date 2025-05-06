package net.jmp.spring.boot.valkey;

/*
 * (#)Animal.java   0.2.0   05/06/2025
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

import java.util.Objects;

/// The address class.
///
/// @version    0.2.0
/// @since      0.2.0
public class Animal {
    //// The type.
    private String type;

    //// The name.
    private String name;

    //// The color.
    private String color;

    //// The age.
    private int age;

    /// The default constructor.
    public Animal() {
        super();
    }

    /// Get the type.
    ///
    /// @return     java.lang.String
    public String getType() {
        return this.type;
    }

    /// Set the type.
    ///
    /// @param  type    java.lang.String
    public void setType(final String type) {
        this.type = type;
    }

    /// Get the name.
    ///
    /// @return     java.lang.String
    public String getName() {
        return this.name;
    }

    /// Set the name.
    ///
    /// @param  name    java.lang.String
    public void setName(final String name) {
        this.name = name;
    }

    /// Get the color.
    ///
    /// @return     java.lang.String
    public String getColor() {
        return this.color;
    }

    /// Set the color.
    ///
    /// @param  color   java.lang.String
    public void setColor(final String color) {
        this.color = color;
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

    /// The default equals method.
    ///
    /// @param  o   java.lang.Object
    /// @return     boolean
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        final Animal animal = (Animal) o;

        return this.age == animal.age && Objects.equals(this.type, animal.type) && Objects.equals(this.name, animal.name) && Objects.equals(this.color, animal.color);
    }

    /// The default hashCode method.
    ///
    /// @return     int
    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.name, this.color, this.age);
    }
}
