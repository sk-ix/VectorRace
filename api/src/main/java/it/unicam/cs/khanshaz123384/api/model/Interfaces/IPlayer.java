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

/**
 * Represents a player in the game.
 * <p>
 * This interface defines the essential attributes and behaviors of a player, including
 * their name, color, position, velocity, rank, and type. Implementations of this interface
 * should provide concrete methods to access and modify these attributes.
 * </p>
 */
public interface IPlayer {

    /**
     * Retrieves the name of the player.
     *
     * @return A {@code String} representing the player's name.
     */
    String getName();

    /**
     * Retrieves the color associated with the player.
     *
     * @return A {@code String} representing the player's color.
     */
    String getColor();

    /**
     * Retrieves the current position of the player.
     * <p>
     * The position is represented as an array with two elements: the x and y coordinates.
     * </p>
     *
     * @return An array of two integers representing the player's position [x, y].
     */
    int[] getPosition();

    /**
     * Retrieves the current velocity of the player.
     * <p>
     * The velocity is represented as an array with two elements: the x and y components of the velocity.
     * </p>
     *
     * @return An array of two integers representing the player's velocity [vx, vy].
     */
    int[] getVelocity();

    /**
     * Retrieves the previous position of the player.
     * <p>
     * The previous position is represented as an array with two elements: the x and y coordinates.
     * </p>
     *
     * @return An array of two integers representing the player's previous position [x, y].
     */
    int[] getPreviousPosition();

    /**
     * Retrieves the rank of the player.
     * <p>
     * The rank represents the player's standing or position in the game or competition.
     * </p>
     *
     * @return An integer representing the player's rank.
     */
    int getRank();

    /**
     * Retrieves the type of the player.
     * <p>
     * The type is represented as a {@code String} and indicates the category or class of the player.
     * </p>
     *
     * @return A {@code String} representing the player's type.
     */
    String getType();

    /**
     * Updates the position of the player.
     * <p>
     * This method sets a new position for the player using the specified x and y coordinates.
     * </p>
     *
     * @param newPositionX The new x-coordinate of the player's position.
     * @param newPositionY The new y-coordinate of the player's position.
     * @throws IllegalArgumentException If either of the new coordinates is negative.
     */
    void setPosition(int newPositionX, int newPositionY);

    /**
     * Updates the velocity of the player.
     * <p>
     * This method sets a new velocity for the player using the specified x and y components of the velocity.
     * </p>
     *
     * @param newVelocityX The new x-component of the player's velocity.
     * @param newVelocityY The new y-component of the player's velocity.
     * @throws IllegalArgumentException If either of the new velocity is negative.
     */
    void setVelocity(int newVelocityX, int newVelocityY);

    /**
     * Updates the rank of the player.
     * <p>
     * This method sets a new rank for the player.
     * </p>
     *
     * @param newRank The new rank of the player.
     * @throws IllegalArgumentException If rank is negative.
     */
    void setRank(int newRank);
}
