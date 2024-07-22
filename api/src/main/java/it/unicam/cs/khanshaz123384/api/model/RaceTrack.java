
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


import java.util.List;

/**
 * Represents a race track for a Formula 1 game.
 * Implements the {@link iTrack} interface to manage track configuration,
 * validate positions, and find starting and finish points.
 */
public class RaceTrack implements iTrack {

    private char[][] grid;
    private int startX = -1;
    private int startY = -1;

    private List<iPlayer> players;

    /**
     * Constructs a {@code RaceTrack} instance.
     * Use {@link #loadTrack(FileIO.TrackConfiguration)} to load the track configuration.
     */
    public RaceTrack() {
        // Constructor left empty; track configuration is loaded with loadTrack
    }

    /**
     * Loads the track configuration from the specified {@link FileIO.TrackConfiguration} object.
     *
     * @param config The {@link FileIO.TrackConfiguration} object containing the track grid and player data.
     * @throws IllegalArgumentException If the configuration object or its grid is {@code null}.
     */
    @Override
    public void loadTrack(FileIO.TrackConfiguration config) {
        if (config == null) {
            throw new IllegalArgumentException("Track configuration cannot be null.");
        }

        this.grid = config.getGrid();
        this.players = config.getPlayers();

        if (this.grid == null) {
            throw new IllegalArgumentException("Track grid cannot be null.");
        }

        if (this.players == null) {
            throw new IllegalArgumentException("Players list cannot be null.");
        }

        // Find and set the starting coordinates
        findStart();
    }

    /**
     * Finds and sets the starting coordinates marked as 'S' in the track grid.
     *
     * @throws IllegalStateException If the starting position 'S' is not found in the track grid.
     */
    private void findStart() {
        if (grid == null) {
            throw new IllegalStateException("Track grid is not initialized.");
        }

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == 'S') {
                    startX = x;
                    startY = y;
                    return; // Exit after finding the start position
                }
            }
        }

        throw new IllegalStateException("Starting position 'S' not found in the track grid.");
    }

    /**
     * Checks if the given coordinates are within the track and not a wall ('#').
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return {@code true} if the coordinates are not a wall; {@code false} otherwise.
     * @throws IllegalStateException If the track grid is not initialized or if the coordinates are out of bounds.
     */
    @Override
    public boolean isTrack(int x, int y) {
        if (!isValid(x, y)) {
            throw new IllegalStateException("Coordinates (x=" + x + ", y=" + y + ") are outside the track grid bounds.");
        }

        return grid[x][y] != '#';
    }

    /**
     * Checks if the given coordinates represent the finish line ('F').
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return {@code true} if the coordinates are the finish line; {@code false} otherwise.
     * @throws IllegalStateException If the track grid is not initialized or if the coordinates are out of bounds.
     */
    @Override
    public boolean isFinish(int x, int y) {
        if (!isValid(x, y)) {
            throw new IllegalStateException("Coordinates (x=" + x + ", y=" + y + ") are outside the track grid bounds.");
        }

        return grid[x][y] == 'F';
    }

    /**
     * Checks if the given coordinates are a wall ('#').
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return {@code true} if the coordinates are a wall; {@code false} otherwise.
     * @throws IllegalStateException If the track grid is not initialized or if the coordinates are out of bounds.
     */
    @Override
    public boolean isWall(int x, int y) {
        if (!isValid(x, y)) {
            throw new IllegalStateException("Coordinates (x=" + x + ", y=" + y + ") are outside the track grid bounds.");
        }

        return grid[x][y] == '#';
    }

    /**
     * Retrieves the starting x-coordinate of the track.
     *
     * @return The starting x-coordinate.
     * @throws IllegalStateException If the starting coordinates are not initialized.
     */
    @Override
    public int getStartX() {
        if (startX == -1 || startY == -1) {
            throw new IllegalStateException("Starting coordinates are not initialized.");
        }
        return startX;
    }

    /**
     * Retrieves the starting y-coordinate of the track.
     *
     * @return The starting y-coordinate.
     * @throws IllegalStateException If the starting coordinates are not initialized.
     */
    @Override
    public int getStartY() {
        if (startX == -1 || startY == -1) {
            throw new IllegalStateException("Starting coordinates are not initialized.");
        }
        return startY;
    }

    /**
     * Checks if the given coordinates are valid within the track grid bounds.
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return {@code true} if the coordinates are within the bounds of the track grid; {@code false} otherwise.
     * @throws IllegalStateException If the track grid is not initialized.
     */
    @Override
    public boolean isValid(int x, int y) {
        if (grid == null) {
            throw new IllegalStateException("Track grid is not initialized.");
        }

        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    /**
     * Retrieves the current grid of the track.
     *
     * @return A 2D array representing the track grid.
     * @throws IllegalStateException If the track grid is not initialized.
     */
    @Override
    public char[][] getGrid() {
        if (grid == null) {
            throw new IllegalStateException("Track grid is not initialized.");
        }
        return grid;
    }

    /**
     * Retrieves the list of players currently on the track.
     *
     * @return A list of {@link iPlayer} objects representing the players.
     * @throws IllegalStateException If the players list is not initialized.
     */
    @Override
    public List<iPlayer> getPlayers() {
        if (players == null) {
            throw new IllegalStateException("Players list is not initialized.");
        }
        return players;
    }
}
