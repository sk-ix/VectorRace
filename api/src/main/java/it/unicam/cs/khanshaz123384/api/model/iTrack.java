
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

import it.unicam.cs.khanshaz123384.api.utils.FileIO;

import java.util.List;

/**
 * Interface representing a race track for a Formula 1 game.
 * This interface defines methods for interacting with the track, including loading the track configuration,
 * validating positions, and retrieving track and player information.
 */
public interface iTrack {

    /**
     * Loads the track configuration from a specified {@link FileIO.TrackConfiguration} object.
     *
     * @param config The {@link FileIO.TrackConfiguration} object containing the track grid and player data.
     * @throws IllegalArgumentException If the configuration object or its grid is {@code null}.
     */
    void loadTrack(FileIO.TrackConfiguration config);

    /**
     * Checks if a given position is a valid track location (i.e., not a wall).
     *
     * @param x The x-coordinate of the position to check.
     * @param y The y-coordinate of the position to check.
     * @return {@code true} if the position is valid and not a wall; {@code false} otherwise.
     * @throws IllegalStateException If the track grid has not been initialized.
     */
    boolean isTrack(int x, int y);

    /**
     * Checks if a given position corresponds to the finish line of the track.
     *
     * @param x The x-coordinate of the position to check.
     * @param y The y-coordinate of the position to check.
     * @return {@code true} if the position is the finish line; {@code false} otherwise.
     * @throws IllegalStateException If the track grid has not been initialized.
     */
    boolean isFinish(int x, int y);

    /**
     * Checks if a given position corresponds to a wall on the track.
     *
     * @param x The x-coordinate of the position to check.
     * @param y The y-coordinate of the position to check.
     * @return {@code true} if the position is a wall; {@code false} otherwise.
     * @throws IllegalStateException If the track grid has not been initialized.
     */
    boolean isWall(int x, int y);

    /**
     * Retrieves the starting x-coordinate of the track.
     *
     * @return The starting x-coordinate.
     * @throws IllegalStateException If the starting coordinates have not been initialized.
     */
    int getStartX();

    /**
     * Retrieves the starting y-coordinate of the track.
     *
     * @return The starting y-coordinate.
     * @throws IllegalStateException If the starting coordinates have not been initialized.
     */
    int getStartY();

    /**
     * Checks if the given coordinates are valid within the track grid.
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return {@code true} if the coordinates are valid and within the track bounds; {@code false} otherwise.
     * @throws IllegalStateException If the track grid has not been initialized.
     */
    boolean isValid(int x, int y);

    /**
     * Retrieves the current grid of the track.
     *
     * @return A 2D array representing the track grid.
     * @throws IllegalStateException If the track grid has not been initialized.
     */
    char[][] getGrid();

    /**
     * Retrieves the list of players currently on the track.
     *
     * @return A list of {@link iPlayer} objects representing the players.
     * @throws IllegalStateException If the player list has not been initialized.
     */
    List<iPlayer> getPlayers();
}
