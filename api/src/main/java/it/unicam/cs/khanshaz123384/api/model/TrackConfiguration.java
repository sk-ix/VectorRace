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

package it.unicam.cs.khanshaz123384.api.model;

import java.util.ArrayList;
import java.util.List;

public record TrackConfiguration(char[][] grid, List<Player> players, String direction) {
    private static final int GRID_ROWS = 38;
    private static final int GRID_COLS = 60;

    public TrackConfiguration {
        if (grid == null || players == null) {
            throw new IllegalArgumentException("Grid and players list cannot be null");
        }
        if (grid.length != GRID_ROWS || grid[0].length != GRID_COLS) {
            throw new IllegalArgumentException("Grid dimensions must be " + GRID_ROWS + "x" + GRID_COLS);
        }
        if (direction == null || (!direction.equals("right") && !direction.equals("left"))) {
            throw new IllegalArgumentException("Direction must be 'right' or 'left'");
        }

    }

    public boolean isPositionWithinBounds(int x, int y) {
        return x >= 0 && x < GRID_COLS && y >= 0 && y < GRID_ROWS;
    }

    public boolean isPositionBlocked(int x, int y) {
        return isPositionWithinBounds(x, y) && grid[y][x] == '#';
    }

    public List<int[]> getFinishPositions() {
        List<int[]> finishPositions = new ArrayList<>();
        for (int y = 0; y < GRID_ROWS; y++) {
            for (int x = 0; x < GRID_COLS; x++) {
                if (grid[y][x] == 'F') {
                    finishPositions.add(new int[]{x, y});
                }
            }
        }
        if (finishPositions.isEmpty()) {
            throw new IllegalStateException("No finish positions 'F' found on the grid.");
        }
        return finishPositions;
    }

    public List<int[]> getStartPositions() {
        List<int[]> startPositions = new ArrayList<>();
        for (int y = 0; y < GRID_ROWS; y++) {
            for (int x = 0; x < GRID_COLS; x++) {
                if (grid[y][x] == 'S') {
                    startPositions.add(new int[]{x, y});
                }
            }
        }
        if (startPositions.isEmpty()) {
            throw new IllegalStateException("No start positions 'S' found on the grid.");
        }
        return startPositions;
    }
}
