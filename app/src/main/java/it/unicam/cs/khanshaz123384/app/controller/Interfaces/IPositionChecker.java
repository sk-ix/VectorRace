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
 * Interface for checking the position of a player in the game.
 *
 * <p>This interface defines a method for validating or verifying the position of a player
 * on the game grid. The implementation of this interface is responsible for ensuring that
 * the player's current position is valid according to the game's rules and constraints.</p>
 */
public interface IPositionChecker {

    /**
     * Checks the position of the specified player.
     *
     * <p>This method validates the current position of the given player on the game grid.
     * It ensures that the player’s position adheres to the game's rules, such as checking
     * for collisions, boundaries, or other position-related constraints. The implementation
     * should handle any necessary checks and take appropriate actions based on the result.</p>
     *
     * @param player The player whose position is to be checked.
     *               This parameter must not be {@code null}.
     * @throws IllegalArgumentException if the player is {@code null}.
     */
    void checkPosition(IPlayer player);
}