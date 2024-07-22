
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


/**
 * Represents a player in the vector racing game.
 * Provides methods to manage the player's position, velocity, and movement.
 */
public interface iPlayer {

    /**
     * Initializes the player at the specified starting coordinates with zero velocity.
     * This method should be called before the player starts moving.
     *
     * @param startX The starting x-coordinate of the player.
     * @param startY The starting y-coordinate of the player.
     */
    void initialize(int startX, int startY);

    /**
     * Retrieves the type of the player (e.g., "Human" or "Bot").
     *
     * @return The type of the player.
     */
    String getType();

    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    String getPlayerName();

    /**
     * Updates the player's velocity based on input or Bot logic.
     * The velocity components (vX, vY) can be adjusted by -1, 0, or +1.
     * For example, passing (1, -1) will increase the x-component of the velocity by 1 and decrease the y-component by 1.
     *
     * @param deltaVx The change in velocity along the x-axis. Must be -1, 0, or +1.
     * @param deltaVy The change in velocity along the y-axis. Must be -1, 0, or +1.
     */
    void updateVelocity(int deltaVx, int deltaVy);

    /**
     * Moves the player according to the current velocity.
     * The player's position is updated by adding the current velocity components to the current position.
     */
    void makeMove();

    /**
     * Retrieves the current x-coordinate of the player's position.
     *
     * @return The current x-coordinate of the player.
     */
    int getX();

    /**
     * Retrieves the current y-coordinate of the player's position.
     *
     * @return The current y-coordinate of the player.
     */
    int getY();

    /**
     * Retrieves the current velocity of the player.
     * The velocity is represented as an array where the first element is the x-component and the second element is the y-component.
     *
     * @return An array where the first element is the x-component of velocity and the second element is the y-component.
     */
    int[] getVelocity();

    /**
     * Sets the player's position directly. This can be useful for resetting the player's position or repositioning them.
     *
     * @param x The new x-coordinate of the player.
     * @param y The new y-coordinate of the player.
     */
    void setPosition(int x, int y);
}
