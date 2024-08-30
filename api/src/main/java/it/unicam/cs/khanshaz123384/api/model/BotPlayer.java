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


import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IColorGenerator;
import java.util.Random;

/**
 * Represents a bot player in the game.
 * <p>
 * This class extends the {@link Player} class and introduces behaviors specific to a bot-controlled player.
 * A bot player is an automated entity controlled by a random movement algorithm. It uses the player attributes
 * provided by the {@code Player} class and adds additional functionality for movement generation.
 * </p>
 */
public class BotPlayer extends Player {
    private final Random random;  // Random number generator for determining movement

    /**
     * Constructs a {@code BotPlayer} with the specified attributes.
     * <p>
     * This constructor initializes a bot player with a name, position, velocity, color, and rank.
     * A {@link Random} instance is also created to enable random movement behavior.
     * </p>
     *
     * @param playerName      The name of the player.
     * @param currentPosition The initial position of the player on the grid, represented as an array [x, y].
     * @param currentVelocity The initial velocity of the player, represented as an array [vx, vy].
     * @param colorGenerator  An instance of {@link IColorGenerator} used to assign a color to the player.
     * @param rank            The rank of the player, indicating their position in the game's leaderboard or standings.
     * @throws IllegalArgumentException if any of the parameters are null, or if the position and velocity arrays
     *                                  do not contain exactly two elements.
     */
    public BotPlayer(String playerName, int[] currentPosition, int[] currentVelocity, IColorGenerator colorGenerator, int rank) {
        super(playerName, currentPosition, currentVelocity, colorGenerator, rank);
        this.random = new Random();  // Initialize the random number generator
    }

    /**
     * Returns the type of player, which is "Bot" for instances of this class.
     * <p>
     * This method overrides the abstract {@link Player#getType()} method to specify that this player is a bot.
     * </p>
     *
     * @return A string representing the player type, specifically "Bot".
     */
    @Override
    public String getType() {
        return "Bot";
    }

    /**
     * Generates the next move for the bot player.
     * <p>
     * This method calculates a random movement delta for the bot. The bot moves in a direction
     * determined by random values for x and y coordinates, ranging from -1 to 1. The resulting move
     * is represented as an array [deltaX, deltaY].
     * </p>
     *
     * @return An array representing the bot's movement deltas [deltaX, deltaY].
     */
    public int[] getNextMove() {
        int deltaX = random.nextInt(3) - 1;  // Random value from -1 to 1 for x-axis movement
        int deltaY = random.nextInt(3) - 1;  // Random value from -1 to 1 for y-axis movement
        return new int[]{deltaX, deltaY};    // Return the movement as an array
    }
}
