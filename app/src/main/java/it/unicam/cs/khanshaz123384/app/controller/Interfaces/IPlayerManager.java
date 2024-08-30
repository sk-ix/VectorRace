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

package it.unicam.cs.khanshaz123384.app.controller.Interfaces;

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;

import java.util.List;

/**
 * Interface for managing players in the game.
 *
 * <p>This interface defines methods for handling player-related operations,
 * such as retrieving players, updating their positions, and managing their
 * race status. Implementations of this interface will manage the state of
 * players throughout the race and ensure their status is correctly updated.</p>
 */
public interface IPlayerManager {

    /**
     * Retrieves the list of all players currently in the game.
     *
     * <p>This method returns a list of players who are actively participating
     * in the race. The returned list should not be modified by the caller.</p>
     *
     * @return A list of {@link IPlayer} objects representing all players.
     *         The list is never {@code null}, but may be empty if no players are present.
     */
    List<IPlayer> getPlayers();

    /**
     * Retrieves the list of players who have finished the race.
     *
     * <p>This method returns a list of players who have completed the race and
     * reached the finish line. The returned list should not be modified by the caller.</p>
     *
     * @return A list of {@link IPlayer} objects representing players who have finished the race.
     *         The list is never {@code null}, but may be empty if no players have finished.
     */
    List<IPlayer> getFinishedPlayers();

    /**
     * Updates the position of a player based on the specified movement.
     *
     * <p>This method updates the position of the given player by applying the specified
     * changes in coordinates. The movement is typically represented as a delta in the x
     * and y directions. The method ensures that the player's new position is valid and
     * updates the player's state accordingly.</p>
     *
     * @param player The player whose position is to be updated.
     *               This parameter must not be {@code null}.
     * @param deltaX The change in the x-coordinate.
     * @param deltaY The change in the y-coordinate.
     * @throws IllegalArgumentException if the player is {@code null}.or if {@code deltaX} or {@code deltaY} are not -1, 0, or 1.
     */
    void updatePlayerPosition(IPlayer player, int deltaX, int deltaY);

    /**
     * Eliminates a player from the game.
     *
     * <p>This method removes the specified player from the game, indicating that they
     * are no longer active or eligible to participate. The player's state should be
     * updated to reflect their elimination.</p>
     *
     * @param player The player to be eliminated.
     *               This parameter must not be {@code null}.
     * @throws IllegalArgumentException if the player is {@code null}.
     */
    void eliminatePlayer(IPlayer player);

    /**
     * Marks a player as having finished the race.
     *
     * <p>This method updates the state of the specified player to indicate that they
     * have completed the race. The player's position and race status should be updated
     * accordingly.</p>
     *
     * @param player The player who has finished the race.
     *               This parameter must not be {@code null}.
     * @throws IllegalArgumentException if the player is {@code null}.
     */
    void finishPlayerRace(IPlayer player);
}