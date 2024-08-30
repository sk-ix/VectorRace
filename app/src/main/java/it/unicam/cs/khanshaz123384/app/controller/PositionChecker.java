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

package it.unicam.cs.khanshaz123384.app.controller;

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPositionValidator;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerManager;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPositionChecker;

/**
 * Checks the position of players to determine their status in the race.
 *
 * <p>This class implements the {@link IPositionChecker} interface and uses position validation
 * and player management components to handle player statuses based on their positions.</p>
 */
public class PositionChecker implements IPositionChecker {

    private final IPositionValidator positionValidator;
    private final IPlayerManager playerManager;

    /**
     * Constructs a PositionChecker instance with the specified position validator and player manager.
     *
     * <p>This constructor initializes the PositionChecker with the necessary components to validate
     * player positions and manage player statuses.</p>
     *
     * @param positionValidator The validator used to check player positions.
     * @param playerManager The manager responsible for handling player statuses.
     * @throws IllegalArgumentException If any parameter is {@code null}.
     */
    public PositionChecker(IPositionValidator positionValidator, IPlayerManager playerManager) {
        if (positionValidator == null)
            throw new IllegalArgumentException("PositionValidator cannot be null.");

        if (playerManager == null)
            throw new IllegalArgumentException("PlayerManager cannot be null.");


        this.positionValidator = positionValidator;
        this.playerManager = playerManager;
    }

    /**
     * Checks the position of the given player to determine if they need to be eliminated or if they have finished the race.
     *
     * <p>This method compares the player's current position with their previous position to validate their status.
     * If the player's position is invalid, they are eliminated. If the player has crossed the finish line,
     * their race is marked as finished.</p>
     *
     * @param player The player whose position is to be checked.
     * @throws IllegalArgumentException If {@code player} is {@code null}.
     */
    @Override
    public void checkPosition(IPlayer player) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null.");


        // Retrieve current and previous positions of the player
        int[] currentPosition = player.getPosition();
        int[] previousPosition = player.getPreviousPosition();

        // Ensure that positions are valid before processing
        if (currentPosition == null || previousPosition == null)
            throw new IllegalStateException("Player's current or previous position is null.");


        // Check if the current position is valid according to the position validator
        if (!positionValidator.isPositionValid(currentPosition, previousPosition))
            // If the position is not valid, eliminate the player
            playerManager.eliminatePlayer(player);
        else if (positionValidator.isFinishLineCrossed(currentPosition, previousPosition))
            // If the finish line is crossed, mark the player as finished
            playerManager.finishPlayerRace(player);
    }
}