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

import it.unicam.cs.khanshaz123384.api.model.BotPlayer;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerActionHandler;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerManager;

/**
 * Handles player actions during the race simulation, including processing player moves.
 *
 * <p>This class implements the {@link IPlayerActionHandler} interface and manages actions
 * for both human and bot players. It processes player input and updates player positions
 * accordingly.</p>
 */
public class PlayerActionHandler implements IPlayerActionHandler {
    private final IPlayerManager playerManager;

    /**
     * Constructs a PlayerActionHandler instance with the specified player manager.
     *
     * <p>This constructor initializes the handler with a player manager to update player positions.</p>
     *
     * @param playerManager The manager responsible for player operations.
     * @throws IllegalArgumentException If {@code playerManager} is {@code null}.
     */
    public PlayerActionHandler(IPlayerManager playerManager) {
        if (playerManager == null)
            throw new IllegalArgumentException("PlayerManager cannot be null.");

        this.playerManager = playerManager;
    }

    /**
     * Processes actions for the specified player based on their type.
     *
     * <p>If the player is human, it waits for player input. If the player is a bot, it retrieves
     * the next move from the bot and updates the player's position accordingly.</p>
     *
     * @param player The player whose action is to be processed.
     * @throws IllegalArgumentException If {@code player} is {@code null}.
     */
    @Override
    public void processPlayer(IPlayer player) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null.");


        if ("Human".equals(player.getType()))
            waitForPlayerInput();
        else if ("Bot".equals(player.getType())) {
            if (player instanceof BotPlayer botPlayer) {
                int[] move = botPlayer.getNextMove();
                notifyPlayerInput(player, move[0], move[1]);
            } else
                throw new IllegalArgumentException("Expected instance of BotPlayer.");

        } else
            throw new IllegalArgumentException("Unknown player type: " + player.getType());

    }

    /**
     * Waits for player input in a synchronized manner.
     *
     * <p>This method pauses the thread until a player input is received or notified.</p>
     */
    private synchronized void waitForPlayerInput() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Preserve interrupt status
        }
    }

    /**
     * Notifies the handler of player input changes and updates the player position.
     *
     * <p>This method updates the player’s position based on the provided deltas and
     * notifies any waiting threads that the input has been processed.</p>
     *
     * @param player The player whose position is to be updated.
     * @param deltaX The change in the X direction.
     * @param deltaY The change in the Y direction.
     * @throws IllegalArgumentException If {@code player} is {@code null} or if {@code deltaX} or {@code deltaY} are not -1, 0, or 1.
     */
    public synchronized void notifyPlayerInput(IPlayer player, int deltaX, int deltaY) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null.");

        if ((deltaX < -1 || deltaX > 1) || (deltaY < -1 || deltaY > 1))
            throw new IllegalArgumentException("Delta values must be -1, 0, or 1.");

        playerManager.updatePlayerPosition(player, deltaX, deltaY);
        this.notify(); // Notify any threads waiting for player input
    }
}