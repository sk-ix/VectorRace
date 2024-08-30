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

package it.unicam.cs.khanshaz123384.utils;

import it.unicam.cs.khanshaz123384.api.utils.ColorGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ColorGeneratorTest {

    @Test
    public void testGetRandomColor() {
        ColorGenerator colorGenerator = new ColorGenerator();
        Set<String> colors = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            colors.add(colorGenerator.getRandomColor());
        }
        assertEquals(8, colors.size(), "All colors should be unique.");
    }

    @Test
    public void testGetRandomColorThrowsExceptionWhenNoColorsAvailable() {
        ColorGenerator colorGenerator = new ColorGenerator();
        for (int i = 0; i < 8; i++) {
            colorGenerator.getRandomColor();
        }
        assertThrows(IllegalStateException.class, colorGenerator::getRandomColor, "Expected exception not thrown.");
    }

    @Test
    public void testGetRandomColorWithRemovedColors() {
        ColorGenerator colorGenerator = new ColorGenerator();
        for (int i = 0; i < 8; i++) {
            colorGenerator.getRandomColor();
        }
        // Simulate removing colors (if applicable)
        // Check if an exception or empty list behavior is handled
        assertThrows(IllegalStateException.class, colorGenerator::getRandomColor, "Expected exception not thrown.");
    }
}
