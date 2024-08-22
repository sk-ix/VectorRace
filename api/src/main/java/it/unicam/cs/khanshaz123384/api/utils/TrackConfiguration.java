package it.unicam.cs.khanshaz123384.api.utils;

import it.unicam.cs.khanshaz123384.api.model.Player;

import java.util.List;

public class TrackConfiguration {
    private static final int GRID_ROWS = 36;
    private static final int GRID_COLS = 60;
    private final char[][] grid;
    private final List<Player> players;

    public TrackConfiguration(char[][] grid, List<Player> players) {
        if (grid == null || players == null) {
            throw new IllegalArgumentException("Grid and players list cannot be null");
        }
        if (grid.length != GRID_ROWS || grid[0].length != GRID_COLS) {
            throw new IllegalArgumentException("Grid dimensions must be " + GRID_ROWS + "x" + GRID_COLS);
        }
        this.grid = grid;
        this.players = players;
    }

    public char[][] getGrid() {
        return grid;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isPositionWithinBounds(int x, int y) {
        return x >= 0 && x < GRID_COLS && y >= 0 && y < GRID_ROWS;
    }

    public boolean isPositionBlocked(int x, int y) {
        if (!isPositionWithinBounds(x, y)) {
            return false;
        }
        return grid[y][x] == '#';
    }

    public boolean isStartPosition(int x, int y) {
        if (!isPositionWithinBounds(x, y)) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        return grid[y][x] == 'S';
    }

    public boolean isEndPosition(int x, int y) {
        if (!isPositionWithinBounds(x, y)) {
            throw new IllegalArgumentException("Position out of bounds");
        }
        return grid[y][x] == 'F';
    }

    public int[] getFinishPosition() {
        for (int y = 0; y < GRID_ROWS; y++) {
            for (int x = 0; x < GRID_COLS; x++) {
                if (grid[y][x] == 'F') {
                    return new int[]{x, y};
                }
            }
        }
        throw new IllegalStateException("No finish position 'F' found on the grid.");
    }

    public int[] getStartPosition() {
        for (int y = 0; y < GRID_ROWS; y++) {
            for (int x = 0; x < GRID_COLS; x++) {
                if (grid[y][x] == 'S') {
                    return new int[]{x, y};
                }
            }
        }
        throw new IllegalStateException("No start position 'S' found on the grid.");
    }

    public boolean isPositionValid(int[] position) {
        int x = position[0];
        int y = position[1];
        return isPositionWithinBounds(x, y) &&
                !isPositionBlocked(x, y);
    }
}
