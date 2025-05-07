package net.jmp.spring.boot.valkey;

/*
 * (#)AnimalSerializer.java 0.2.0   05/07/2025
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

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.Serializer;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;

/// The animal serializer class.
///
/// @version    0.2.0
/// @since      0.2.0
public class AnimalSerializer extends Serializer<Animal> {
    /// The default constructor.
    public AnimalSerializer() {
        super();
    }

    /// The default write method.
    ///
    /// @param  kryo    com.esotericsoftware.kryo.kryo5.Kryo
    /// @param  output  com.esotericsoftware.kryo.kryo5.io.Output
    /// @param  animal  net.jmp.spring.boot.valkey.Animal
    @Override
    public void write(final Kryo kryo, final Output output, final Animal animal) {
        output.writeString(animal.getType());
        output.writeString(animal.getName());
        output.writeString(animal.getColor());
        output.writeInt(animal.getAge());
    }

    /// The default read method.
    ///
    /// @param  kryo    com.esotericsoftware.kryo.kryo5.Kryo
    /// @param  input   com.esotericsoftware.kryo.kryo5.io.Input
    /// @param  type    java.lang.Class<? extends net.jmp.spring.boot.valkey.Animal>
    /// @return         net.jmp.spring.boot.valkey.Animal
    @Override
    public Animal read(final Kryo kryo, final Input input, final Class<? extends Animal> type) {
        final Animal animal = new Animal();

        animal.setType(input.readString());
        animal.setName(input.readString());
        animal.setColor(input.readString());
        animal.setAge(input.readInt());

        return animal;
    }
}
