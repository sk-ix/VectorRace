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
 * Parses and validates grid configurations from a list of strings.
 *
 * <p>This class provides methods to parse a grid configuration from a list of strings, validate its size,
 * and ensure it meets specific requirements. It also processes the grid to fill in vertical cells between
 * start ('S') and finish ('F') points.</p>
 */
public class GridParser {

    private static final int EXPECTED_ROWS = 38; // Constant for the number of rows in the grid
    private static final int EXPECTED_COLS = 60; // Constant for the number of columns in the grid

    // Private constructor to prevent instantiation of this utility class
    private GridParser() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Parses a list of grid lines into a 2D character array.
     *
     * <p>The method validates the grid size and content, ensures that there is exactly one start ('S') and
     * one finish ('F') column, and fills vertical cells between the top and bottom walls.</p>
     *
     * @param gridLines A list of strings representing the grid, where each string is a row of the grid.
     * @return A 2D character array representing the parsed grid.
     * @throws IllegalArgumentException if the grid lines do not meet the expected format or size.
     */
    public static char[][] parseGrid(List<String> gridLines) {
        // Validate the grid size and content
        GridValidator.validateGridSize(gridLines);

        char[][] grid = new char[EXPECTED_ROWS][EXPECTED_COLS];
        int startCol = -1;
        int finishCol = -1;

        // Parse the grid lines and locate start ('S') and finish ('F') columns
        for (int i = 0; i < EXPECTED_ROWS; i++) {
            String line = gridLines.get(i);
            if (line.length() != EXPECTED_COLS)
                throw new IllegalArgumentException("Inconsistent row length in grid configuration.");


            grid[i] = line.toCharArray();

            // Locate 'S' characters
            if (line.contains("S")) {
                int currentStartCol = line.indexOf('S');
                if (startCol == -1)
                    startCol = currentStartCol;
                else if (currentStartCol != startCol)
                    throw new IllegalArgumentException("All 'S' characters must be in the same column.");

            }

            // Locate 'F' characters
            if (line.contains("F")) {
                int currentFinishCol = line.indexOf('F');
                if (finishCol == -1)
                    finishCol = currentFinishCol;
                else if (currentFinishCol != finishCol)
                    throw new IllegalArgumentException("All 'F' characters must be in the same column.");
            }
        }

        // Ensure there is exactly one 'S' in the grid
        if (startCol == -1)
            throw new IllegalArgumentException("There must be exactly one 'S' in the grid.");


        // Ensure there is exactly one 'F' in the grid
        if (finishCol == -1)
            throw new IllegalArgumentException("There must be exactly one 'F' in the grid.");


        // Fill vertical cells between walls
        for (int row = 0; row < EXPECTED_ROWS; row++) {
            for (int col = 0; col < EXPECTED_COLS; col++) {
                fillVerticalCells(grid, row, col);
            }
        }

        return grid;
    }

    /**
     * Fills vertical cells between the top and bottom walls for columns containing start ('S') or finish ('F').
     *
     * <p>It determines the vertical extent of the filling based on the nearest top and bottom walls.</p>
     *
     * @param grid The 2D character array representing the grid.
     * @param row  The current row index.
     * @param col  The current column index.
     */
    private static void fillVerticalCells(char[][] grid, int row, int col) {
        if (grid[row][col] == 'S' || grid[row][col] == 'F') {
            int topRow = findTopWall(grid, row, col);
            int bottomRow = findBottomWall(grid, row, col);

            char fillChar = (grid[row][col] == 'S') ? 'S' : 'F';

            for (int r = topRow + 1; r < bottomRow; r++) {
                grid[r][col] = fillChar;
            }
        }
    }

    /**
     * Finds the row index of the nearest top wall ('#') above a given cell.
     *
     * @param gridMap The 2D character array representing the grid.
     * @param startRow The starting row index to search from.
     * @param col The column index to search in.
     * @return The row index of the nearest top wall.
     */
    private static int findTopWall(char[][] gridMap, int startRow, int col) {
        for (int row = startRow; row >= 0; row--) {
            if (gridMap[row][col] == '#')
                return row;
        }
        return 0;
    }

    /**
     * Finds the row index of the nearest bottom wall ('#') below a given cell.
     *
     * @param gridMap The 2D character array representing the grid.
     * @param startRow The starting row index to search from.
     * @param col The column index to search in.
     * @return The row index of the nearest bottom wall.
     */
    private static int findBottomWall(char[][] gridMap, int startRow, int col) {
        for (int row = startRow; row < EXPECTED_ROWS; row++) {
            if (gridMap[row][col] == '#')
                return row;
        }
        return EXPECTED_ROWS - 1;
    }
}