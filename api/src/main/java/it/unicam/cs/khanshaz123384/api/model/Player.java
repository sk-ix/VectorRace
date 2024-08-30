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
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IColorGenerator;

/**
 * Represents a player in the game, including the player's name, position, velocity, color, and rank.
 * <p>
 * This class provides methods to get and set these attributes, and also to retrieve the player's type.
 * It implements the {@link IPlayer} interface, which defines the core functionalities of a player in the game.
 * </p>
 */
public abstract class Player implements IPlayer {

    private final String playerName;
    private final String playerColor;
    private final int[] currentPosition;
    private int[] previousPosition;
    private final int[] currentVelocity;
    private int rank;

    /**
     * Constructs a {@code Player} object with the specified name, initial position, velocity, color generator, and rank.
     * <p>
     * Initializes the player's attributes and assigns a color using the provided color generator. The position and velocity
     * arrays are cloned to ensure immutability of the original arrays.
     * </p>
     *
     * @param playerName      The name of the player.
     * @param currentPosition The initial position of the player, represented as an array [x, y].
     * @param currentVelocity The initial velocity of the player, represented as an array [vx, vy].
     * @param colorGenerator  The color generator used to assign a random color to the player.
     * @param rank            The initial rank of the player.
     * @throws IllegalArgumentException if any of the parameters are null, or if the position and velocity arrays
     *                                  do not contain exactly two elements.
     */
    public Player(String playerName, int[] currentPosition, int[] currentVelocity, IColorGenerator colorGenerator, int rank) {
        if (playerName == null || currentPosition == null || currentVelocity == null || colorGenerator == null)
            throw new IllegalArgumentException("Player name, position, velocity, and color generator cannot be null.");

        if (currentPosition.length != 2 || currentVelocity.length != 2)
            throw new IllegalArgumentException("Position and velocity arrays must contain exactly two elements: [x, y] or [vx, vy].");

        this.playerName = playerName;
        this.currentPosition = currentPosition.clone(); // Ensure the position array is immutable
        this.currentVelocity = currentVelocity.clone(); // Ensure the velocity array is immutable
        this.playerColor = colorGenerator.getRandomColor();
        this.previousPosition = currentPosition.clone();
        this.rank = rank;
    }

    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    @Override
    public String getName() {
        return playerName;
    }

    /**
     * Returns the color of the player.
     *
     * @return The color of the player.
     */
    @Override
    public String getColor() {
        return playerColor;
    }

    /**
     * Returns the current position of the player.
     * <p>
     * The position is represented as an array with two elements: [x, y].
     * </p>
     *
     * @return An array representing the current position of the player.
     */
    @Override
    public int[] getPosition() {
        return currentPosition.clone(); // Return a clone to ensure immutability
    }

    /**
     * Returns the current velocity of the player.
     * <p>
     * The velocity is represented as an array with two elements: [vx, vy].
     * </p>
     *
     * @return An array representing the current velocity of the player.
     */
    @Override
    public int[] getVelocity() {
        return currentVelocity.clone(); // Return a clone to ensure immutability
    }

    /**
     * Returns the previous position of the player.
     * <p>
     * The previous position is represented as an array with two elements: [x, y].
     * </p>
     *
     * @return An array representing the previous position of the player.
     */
    @Override
    public int[] getPreviousPosition() {
        return previousPosition.clone(); // Return a clone to ensure immutability
    }

    /**
     * Returns the current rank of the player.
     *
     * @return The rank of the player.
     */
    @Override
    public int getRank() {
        return rank;
    }

    /**
     * Returns the type of the player.
     * <p>
     * This method must be implemented by subclasses to provide the specific type of the player.
     * </p>
     *
     * @return The type of the player as a string.
     */
    public abstract String getType();

    /**
     * Updates the player's position with new coordinates and records the previous position.
     * <p>
     * The current position is updated to the new coordinates, and the previous position is set to the old current position.
     * </p>
     * <p>
     * If the new coordinates are negative, an {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * @param newPositionX The new x-coordinate of the player.
     * @param newPositionY The new y-coordinate of the player.
     * @throws IllegalArgumentException If either of the new coordinates is negative.
     */
    @Override
    public void setPosition(int newPositionX, int newPositionY) {
        if (newPositionX < 0 || newPositionY < 0)
            throw new IllegalArgumentException("Coordinates cannot be negative.");


        this.previousPosition = this.currentPosition.clone();
        this.currentPosition[0] = newPositionX;
        this.currentPosition[1] = newPositionY;
    }

    /**
     * Updates the player's velocity with new components.
     * <p>
     * The velocity array is updated to the new values provided.
     * </p>
     *
     * @param newVelocityX The new x-component of the player's velocity.
     * @param newVelocityY The new y-component of the player's velocity.
     * @throws IllegalArgumentException If either of the new velocity is negative.
     */
    @Override
    public void setVelocity(int newVelocityX, int newVelocityY) {
        if (newVelocityX < 0 || newVelocityY < 0)
            throw new IllegalArgumentException("Velocity cannot be negative.");

        this.currentVelocity[0] = newVelocityX;
        this.currentVelocity[1] = newVelocityY;
    }

    /**
     * Updates the player's rank.
     * <p>
     * The rank attribute is updated to the new value provided.
     * </p>
     *
     * @param newRank The new rank of the player.
     * @throws IllegalArgumentException If rank is negative.
     */
    @Override
    public void setRank(int newRank) {
        if (newRank < 0)
            throw new IllegalArgumentException("Rank cannot be negative.");
        this.rank = newRank;
    }
}
