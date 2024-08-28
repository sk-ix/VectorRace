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

public class GridParser {

    private static final int EXPECTED_ROWS = 38;
    private static final int EXPECTED_COLS = 60;

    private GridParser() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static char[][] parseGrid(List<String> gridLines) {
        // Validazione delle dimensioni della griglia
        GridValidator.validateGridSize(gridLines);

        char[][] grid = new char[EXPECTED_ROWS][EXPECTED_COLS];
        int startCol = -1;
        int finishCol = -1;

        // Parsing della griglia e ricerca delle colonne 'S' e 'F'
        for (int i = 0; i < EXPECTED_ROWS; i++) {
            String line = gridLines.get(i);
            if (line.length() != EXPECTED_COLS) {
                throw new IllegalArgumentException("Inconsistent row length in grid configuration.");
            }

            grid[i] = line.toCharArray();

            // Verifica la posizione delle colonne di partenza e arrivo
            if (line.contains("S")) {
                int currentStartCol = line.indexOf('S');
                if (startCol == -1) {
                    startCol = currentStartCol;
                } else if (currentStartCol != startCol) {
                    throw new IllegalArgumentException("All 'S' characters must be in the same column.");
                }
            }

            if (line.contains("F")) {
                int currentFinishCol = line.indexOf('F');
                if (finishCol == -1) {
                    finishCol = currentFinishCol;
                } else if (currentFinishCol != finishCol) {
                    throw new IllegalArgumentException("All 'F' characters must be in the same column.");
                }
            }
        }

        // Verifica che ci sia esattamente una 'S'
        if (startCol == -1) {
            throw new IllegalArgumentException("There must be exactly one 'S' in the grid.");
        }

        // Verifica che ci sia esattamente una 'F'
        if (finishCol == -1) {
            throw new IllegalArgumentException("There must be exactly one 'F' in the grid.");
        }

        // Riempie le celle verticali tra le pareti superiori e inferiori
        for (int row = 0; row < EXPECTED_ROWS; row++) {
            for (int col = 0; col < EXPECTED_COLS; col++) {
                fillVerticalCells(grid, row, col);
            }
        }

        return grid;
    }

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

    private static int findTopWall(char[][] gridMap, int startRow, int col) {
        for (int row = startRow; row >= 0; row--) {
            if (gridMap[row][col] == '#') {
                return row;
            }
        }
        return 0;
    }

    private static int findBottomWall(char[][] gridMap, int startRow, int col) {
        for (int row = startRow; row < EXPECTED_ROWS; row++) {
            if (gridMap[row][col] == '#') {
                return row;
            }
        }
        return EXPECTED_ROWS - 1;
    }
}
