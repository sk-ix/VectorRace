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

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.ITrackConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a configuration for a game track, including the grid layout, list of players, and movement direction.
 * <p>
 * This class implements the {@link ITrackConfiguration} interface and provides methods to access the grid, check
 * position validity, and retrieve start and finish positions on the track.
 * </p>
 */
public record TrackConfiguration
        (char[][] grid, List<IPlayer> players, String direction)
        implements ITrackConfiguration {

    private static final int GRID_ROWS = 38;  // Constant for the number of rows in the grid
    private static final int GRID_COLS = 60;  // Constant for the number of columns in the grid

    /**
     * Constructs a {@code TrackConfiguration} instance with validation.
     *
     * @param grid The 2D array representing the grid layout.
     * @param players The list of players in the game.
     * @param direction The direction of movement, either "right" or "left".
     * @throws IllegalArgumentException if grid or players is null, if grid dimensions are incorrect,
     *         or if direction is not "right" or "left".
     */
    public TrackConfiguration {
        if (grid == null || players == null)
            throw new IllegalArgumentException("Grid and players list cannot be null.");

        if (grid.length == 0 || players.isEmpty())
            throw new IllegalArgumentException("Grid and players list cannot be empty.");

        if (grid.length != GRID_ROWS || grid[0].length != GRID_COLS)
            throw new IllegalArgumentException("Grid dimensions must be " + GRID_ROWS + "x" + GRID_COLS + ".");

        if (direction == null || (!direction.equals("right") && !direction.equals("left")))
            throw new IllegalArgumentException("Direction must be 'right' or 'left'.");
    }

    /**
     * Returns the list of players.
     *
     * @return The list of players.
     */
    @Override
    public List<IPlayer> getPlayers() {
        return players;
    }

    /**
     * Returns the grid layout of the track.
     *
     * @return The 2D array representing the grid.
     */
    @Override
    public char[][] getGrid() {
        return grid;
    }

    /**
     * Checks if a position is within the bounds of the grid.
     * <p>
     * This method verifies that the provided coordinates are within the valid range
     * of the grid dimensions. If the grid is not initialized, an exception is thrown.
     * </p>
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return {@code true} if the position is within the bounds of the grid;
     *         {@code false} otherwise.
     * @throws IllegalStateException if the grid is not initialized.
     */
    @Override
    public boolean isPositionWithinBounds(int x, int y) {
        if (grid == null)
            throw new IllegalStateException("Grid is not initialized.");

        // Return true if the x and y coordinates are within the grid dimensions.
        return x >= 0 && x < GRID_COLS && y >= 0 && y < GRID_ROWS;
    }

    /**
     * Checks if a position is blocked by an obstacle ('#').
     * <p>
     * This method first verifies if the provided coordinates are within the bounds
     * of the grid. If the position is out of bounds, it returns {@code false} as
     * out-of-bounds positions cannot be blocked. If the position is within bounds,
     * it then checks if the grid cell at that position contains an obstacle ('#').
     * </p>
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return {@code true} if the position is within bounds and blocked by an obstacle;
     *         {@code false} otherwise.
     * @throws  IndexOutOfBoundsException if the position is out of bounds
     * @throws IllegalStateException if the grid is not initialized.
     */
    @Override
    public boolean isPositionBlocked(int x, int y) {
        if (grid == null)
            throw new IllegalStateException("Grid is not initialized.");

        // First check if the position is within bounds; if not, return false.
        if (!isPositionWithinBounds(x, y))
            throw new IndexOutOfBoundsException("Position is out of bounds.");

        // Return true if the specified grid cell contains an obstacle ('#').
        return grid[y][x] == '#';
    }

    /**
     * Retrieves the list of start positions on the grid.
     * <p>
     * This method scans the grid for cells marked as 'S' and collects their coordinates
     * into a list. If no start positions are found, an {@code IllegalStateException} is thrown.
     * </p>
     *
     * @return A list of coordinate pairs representing start positions.
     * @throws IllegalStateException if the grid is not initialized or if no start positions are found.
     */
    @Override
    public List<int[]> getStartPositions() {
        if (grid == null)
            throw new IllegalStateException("Grid is not initialized.");

        // Initialize a list to store the start positions.
        List<int[]> startPositions = new ArrayList<>();

        // Iterate through the grid to find cells marked with 'S'.
        for (int y = 0; y < GRID_ROWS; y++) {
            for (int x = 0; x < GRID_COLS; x++) {
                if (grid[y][x] == 'S')
                    startPositions.add(new int[]{x, y});
            }
        }

        if (startPositions.isEmpty())
            throw new IllegalStateException("No start positions 'S' found on the grid.");

        return startPositions;
    }

    /**
     * Retrieves the list of finish positions on the grid.
     * <p>
     * This method scans the grid for cells marked as 'F' and collects their coordinates
     * into a list. If no finish positions are found, an {@code IllegalStateException} is thrown.
     * </p>
     *
     * @return A list of coordinate pairs representing finish positions.
     * @throws IllegalStateException if the grid is not initialized or if no finish positions are found.
     */
    @Override
    public List<int[]> getFinishPositions() {
        if (grid == null)
            throw new IllegalStateException("Grid is not initialized.");

        // Initialize a list to store the finish positions.
        List<int[]> finishPositions = new ArrayList<>();

        // Iterate through the grid to find cells marked with 'F'.
        for (int y = 0; y < GRID_ROWS; y++) {
            for (int x = 0; x < GRID_COLS; x++) {
                if (grid[y][x] == 'F')
                    finishPositions.add(new int[]{x, y});
            }
        }

        if (finishPositions.isEmpty())
            throw new IllegalStateException("No finish positions 'F' found on the grid.");

        return finishPositions;
    }

    /**
     * Retrieves the direction of movement for the track.
     * <p>
     * This method returns the direction in which the track is oriented, typically
     * either "right" or "left". This direction affects how movement and crossing
     * of finish lines are interpreted.
     * </p>
     *
     * @return A {@code String} representing the direction of movement ("right" or "left").
     */
    public String getDirection() { return direction;}
}
