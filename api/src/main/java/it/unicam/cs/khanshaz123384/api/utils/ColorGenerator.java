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

package it.unicam.cs.khanshaz123384.api.utils;

import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IColorGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the {@link IColorGenerator} interface that provides color generation services.
 *
 * <p>This class maintains a list of predefined colors and provides a method to retrieve a random color.
 * Each color is removed from the list after being selected to ensure it is only assigned once.</p>
 */
public class ColorGenerator implements IColorGenerator {

    // Predefined list of colors
    private static final List<String> AVAILABLE_COLORS = Arrays.asList(
            "Red", "Blue", "Green", "Yellow", "Orange", "Purple", "Pink", "Brown"
    );

    private final List<String> availableColors;

    /**
     * Constructs a {@code ColorGenerator} instance with the default list of colors.
     */
    public ColorGenerator() {availableColors = new ArrayList<>(AVAILABLE_COLORS);}

    /**
     * Retrieves a random color from the available colors and removes it from the list.
     *
     * <p>This method ensures that each color is assigned only once by removing it from the list
     * after it has been selected. If all colors have been used, it throws an exception.</p>
     *
     * @return A randomly selected color.
     * @throws IllegalStateException if no colors are available to choose from.
     */
    @Override
    public String getRandomColor() {
        if (availableColors.isEmpty())
            throw new IllegalStateException("No colors available");

        Random random = new Random();
        int index = random.nextInt(availableColors.size());
        return availableColors.remove(index);
    }
}