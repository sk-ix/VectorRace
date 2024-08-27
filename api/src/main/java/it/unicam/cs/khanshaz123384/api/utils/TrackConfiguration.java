package it.unicam.cs.khanshaz123384.api.utils;

import it.unicam.cs.khanshaz123384.api.model.Player;

import java.util.ArrayList;
import java.util.List;

public class TrackConfiguration {
    private static final int GRID_ROWS = 38;
    private static final int GRID_COLS = 60;
    private final char[][] grid;
    private final List<Player> players;
    private final String direction;

    public TrackConfiguration(char[][] grid, List<Player> players, String direction) {
        if (grid == null || players == null) {
            throw new IllegalArgumentException("Grid and players list cannot be null");
        }
        if (grid.length != GRID_ROWS || grid[0].length != GRID_COLS) {
            throw new IllegalArgumentException("Grid dimensions must be " + GRID_ROWS + "x" + GRID_COLS);
        }

        this.grid = grid;
        this.players = players;
        this.direction = direction;
    }

    public char[][] getGrid() {
        return grid;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getDirection() {
        return direction;
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

    public boolean isPositionValid(int[] currentPosition, int[] previousPosition) {
        if (!isPositionWithinBounds(currentPosition[0], currentPosition[1]) || isPositionBlocked(currentPosition[0], currentPosition[1])) {
            return false;
        }

        // Ottieni tutte le posizioni iniziali
        List<int[]> startingPositions = getStartPositions();

        if (startingPositions.isEmpty()) {
            throw new IllegalStateException("No start positions found.");
        }

        // Controlla per ogni posizione iniziale
        for (int[] startingPosition : startingPositions) {
            if (previousPosition[1] == startingPosition[1]) {  // Assicuriamoci che le posizioni siano sulla stessa riga
                boolean isCurrentBehindStart = false;
                boolean isPreviousAheadStart = false;

                if ("right".equals(direction)) {
                    // Controlla se la posizione precedente è davanti alla posizione iniziale
                    isPreviousAheadStart = previousPosition[0] >= startingPosition[0];
                    // Controlla se la posizione corrente è dietro alla posizione iniziale
                    isCurrentBehindStart = currentPosition[0] < startingPosition[0];
                } else if ("left".equals(direction)) {
                    // Controlla se la posizione precedente è davanti alla posizione iniziale
                    isPreviousAheadStart = previousPosition[0] <= startingPosition[0];
                    // Controlla se la posizione corrente è dietro alla posizione iniziale
                    isCurrentBehindStart = currentPosition[0] > startingPosition[0];
                }

                // Se la posizione corrente è dietro e la posizione precedente è davanti, la mossa non è valida
                if (isCurrentBehindStart && isPreviousAheadStart) {
                    return false;
                }
            }
        }

        // Se nessuna posizione iniziale rende la mossa non valida, allora è valida
        return true;
    }
}
