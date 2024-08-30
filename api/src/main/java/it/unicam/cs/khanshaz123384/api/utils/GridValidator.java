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

import java.util.List;

/**
 * Provides methods to validate grid lines and grid size.
 *
 * <p>This class includes static methods for validating the content of individual grid lines and the overall
 * size of the grid. It ensures that each line contains only valid characters and that the grid has the expected
 * number of rows and columns.</p>
 */
public class GridValidator {

    private static final int EXPECTED_ROWS = 38;
    private static final int EXPECTED_COLS = 60;

    /**
     * Validates that a grid line contains only allowed characters.
     *
     * <p>Allowed characters are 'S', 'F', '*', and '#'. Any line containing other characters is considered invalid.</p>
     *
     * @param line The line of the grid to be validated.
     * @return {@code true} if the line contains only valid characters; {@code false} otherwise.
     * @throws IllegalStateException if {@code line} is null.
     */
    public static boolean validateGridLine(String line) {
        if (line == null)
            throw new IllegalStateException("Line cannot be null.");


        for (char c : line.toCharArray()) {
            if (c != 'S' && c != 'F' && c != '*' && c != '#')
                return false;

        }
        return true;
    }

    /**
     * Validates that the grid has the expected number of rows and columns.
     *
     * <p>The method checks if the grid contains exactly {@value #EXPECTED_ROWS} rows and each row has exactly
     * {@value #EXPECTED_COLS} columns. Throws an {@link IllegalArgumentException} if the grid size does not match
     * the expected dimensions.</p>
     *
     * @param gridLines A list of strings representing the grid.
     * @throws IllegalArgumentException if the number of rows or columns does not match the expected dimensions.
     */
    public static void validateGridSize(List<String> gridLines) {
        if (gridLines == null)
            throw new IllegalArgumentException("Grid lines cannot be null.");


        if (gridLines.size() != EXPECTED_ROWS)
            throw new IllegalArgumentException("Grid must have exactly " + EXPECTED_ROWS + " rows.");


        if (gridLines.isEmpty())
            throw new IllegalArgumentException("Grid lines cannot be empty.");


        int cols = gridLines.getFirst().length();
        if (cols != EXPECTED_COLS)
            throw new IllegalArgumentException("Grid must have exactly " + EXPECTED_COLS + " columns.");


        // Ensure that all rows have the same number of columns
        for (String line : gridLines) {
            if (line.length() != cols)
                throw new IllegalArgumentException("All rows must have exactly " + EXPECTED_COLS + " columns.");

        }
    }
}