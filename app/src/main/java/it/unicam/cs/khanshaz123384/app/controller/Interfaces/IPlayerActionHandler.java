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

/**
 * Interface for handling player actions within the game.
 *
 * <p>This interface defines methods for processing player actions and handling
 * input events that affect player behavior. Implementations of this interface
 * manage how player actions are executed and how player input is processed.</p>
 */
public interface IPlayerActionHandler {

    /**
     * Processes the actions for the specified player.
     *
     * <p>This method is responsible for executing the actions of the given player.
     * It could involve updating the player's state, interacting with other game
     * components, or performing any actions associated with the player.</p>
     *
     * @param player The player whose actions are to be processed.
     *               This parameter must not be {@code null}.
     * @throws IllegalArgumentException if the player is {@code null}.
     */
    void processPlayer(IPlayer player);

    /**
     * Notifies the player of input changes.
     *
     * <p>This method is called when player input is received, and it updates the
     * player's state based on the input deltas. The deltas represent changes in
     * the x and y coordinates and should be applied to the player's current position.</p>
     *
     * @param player The player receiving the input.
     *               This parameter must not be {@code null}.
     * @param deltaX The change in the x-coordinate due to the input.
     * @param deltaY The change in the y-coordinate due to the input.
     * @throws IllegalArgumentException if the player is {@code null}.
     */
    void notifyPlayerInput(IPlayer player, int deltaX, int deltaY);
}