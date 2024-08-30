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

/**
 * Represents a human player in the game.
 * <p>
 * This class extends the {@link Player} class, adding specific details or behaviors
 * relevant to a human-controlled player. It utilizes attributes such as the player's name,
 * current position, velocity, color, and rank, which are inherited from the {@code Player} class.
 * </p>
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a {@code HumanPlayer} with the specified attributes.
     * <p>
     * This constructor initializes a human player with a name, position, velocity, color, and rank.
     * The color is generated using an instance of {@link IColorGenerator}, and the attributes are passed
     * to the superclass constructor for initialization.
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
    public HumanPlayer(String playerName, int[] currentPosition, int[] currentVelocity, IColorGenerator colorGenerator, int rank) {
        super(playerName, currentPosition, currentVelocity, colorGenerator, rank);
    }

    /**
     * Returns the type of player, which is "Human" for instances of this class.
     * <p>
     * This method overrides the abstract {@link Player#getType()} method to return a specific type
     * that identifies this player as a human player.
     * </p>
     *
     * @return A string representing the player type, specifically "Human".
     */
    @Override
    public String getType() {
        return "Human";
    }
}