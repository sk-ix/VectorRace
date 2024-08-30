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
 * Interface for validating the position of a player on a track.
 *
 * <p>This interface provides methods to validate whether a player's current position
 * is valid based on their previous position, and to check if the player has crossed
 * the finish line during their movement.</p>
 *
 * <p>Implementations of this interface are expected to enforce the rules and constraints
 * defined by the specific track configuration.</p>
 */
public interface IPositionValidator {

    /**
     * Validates if the current position is valid based on the previous position.
     *
     * <p>This method checks the validity of the player's movement, ensuring that the
     * current position adheres to the rules of the track, such as being within bounds,
     * not blocked, and complying with any direction constraints.</p>
     *
     * @param currentPosition  The current position of the player, represented as an array {@code [x, y]}.
     * @param previousPosition The previous position of the player, represented as an array {@code [x, y]}.
     * @return {@code true} if the current position is valid; {@code false} otherwise.
     * @throws IllegalArgumentException if {@code currentPosition} or {@code previousPosition} is null or not properly formatted.
     */
    boolean isPositionValid(int[] currentPosition, int[] previousPosition);

    /**
     * Checks if the finish line has been crossed between the previous and current positions.
     *
     * <p>The finish line is considered crossed if the player's current position is beyond
     * the finish line relative to their previous position, according to the direction
     * specified by the track configuration.</p>
     *
     * @param currentPosition  The current position of the player, represented as an array {@code [x, y]}.
     * @param previousPosition The previous position of the player, represented as an array {@code [x, y]}.
     * @return {@code true} if the finish line has been crossed; {@code false} otherwise.
     * @throws IllegalArgumentException if {@code currentPosition} or {@code previousPosition} is null or not properly formatted.
     */
    boolean isFinishLineCrossed(int[] currentPosition, int[] previousPosition);
}