/*
 * MIT License
 *
 * Copyright (c) 2024  Khan Shaz
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

package it.unicam.cs.khanshaz123384.api.utils.Interfaces;

/**
 * An interface for generating random colors.
 * <p>
 * This interface defines a method for generating a random color, which can be used for various purposes,
 * such as assigning colors to players in a game. Implementations of this interface should provide a way
 * to generate color values, typically in a format suitable for graphical representation.
 * </p>
 */
public interface IColorGenerator {

    /**
     * Generates a random color.
     * <p>
     * The generated color should be represented as a string. The specific format of the color string
     * can vary depending on the implementation, but common formats include hexadecimal color codes
     * (e.g., "#RRGGBB") or named colors.
     * </p>
     *
     * @return A string representing a randomly generated color.
     */
    String getRandomColor();
}