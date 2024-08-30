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

package it.unicam.cs.khanshaz123384.api.model.Interfaces;

import java.util.List;

/**
 * Represents the configuration of a game track.
 * <p>
 * This interface defines methods to retrieve information about the track, including
 * the grid layout, player list, movement direction, and validation of positions.
 * Implementations of this interface should provide concrete methods to access these
 * aspects of the track configuration.
 * </p>
 */
public interface ITrackConfiguration {

    /**
     * Retrieves the list of players participating in the game.
     *
     * @return A list of {@link IPlayer} instances representing the players.
     */
    List<IPlayer> getPlayers();

    /**
     * Retrieves the 2D array representing the grid layout of the track.
     *
     * @return A 2D array of characters where each cell represents a part of the track.
     */
    char[][] getGrid();

    /**
     * Checks if a given position is within the bounds of the grid.
     * <p>
     * This method ensures that the provided coordinates are within the valid range
     * of the grid dimensions. It returns {@code true} if the position is valid;
     * {@code false} otherwise.
     * </p>
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return {@code true} if the position is within the bounds of the grid;
     *         {@code false} otherwise.
     * @throws IllegalStateException if the grid is not initialized.
     */
    boolean isPositionWithinBounds(int x, int y);

    /**
     * Checks if a given position is blocked by an obstacle.
     * <p>
     * This method verifies whether the position is blocked by an obstacle, such as
     * a wall or barrier, in the grid. It returns {@code true} if the position is blocked;
     * {@code false} otherwise. The position must be within bounds to be checked for blockage.
     * </p>
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return {@code true} if the position is within bounds and blocked by an obstacle;
     *         {@code false} otherwise.
     * @throws  IndexOutOfBoundsException if the position is out of bounds
     * @throws IllegalStateException if the grid is not initialized.
     */
    boolean isPositionBlocked(int x, int y);

    /**
     * Retrieves a list of finish positions on the grid.
     * <p>
     * This method scans the grid for cells marked as finish positions and collects their
     * coordinates into a list. These positions indicate where the game is considered finished.
     * </p>
     *
     * @return A list of coordinate pairs representing finish positions on the grid.
     * @throws IllegalStateException if the grid is not initialized or if no finish positions are found.
     */
    List<int[]> getFinishPositions();

    /**
     * Retrieves a list of start positions on the grid.
     * <p>
     * This method scans the grid for cells marked as start positions and collects their
     * coordinates into a list. These positions indicate where the game starts.
     * </p>
     *
     * @return A list of coordinate pairs representing start positions on the grid.
     * @throws IllegalStateException if the grid is not initialized or if no start positions are found.
     */
    List<int[]> getStartPositions();

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
    String getDirection();
}