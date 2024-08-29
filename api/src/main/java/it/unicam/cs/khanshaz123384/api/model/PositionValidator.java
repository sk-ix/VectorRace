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

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPositionValidator;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.ITrackConfiguration;

import java.util.List;

/**
 * Validates positions on the track according to the track configuration.
 *
 * This class provides methods to validate if a position is valid and if a finish line has been crossed,
 * based on the current and previous positions of the player and the track configuration.
 */
public class PositionValidator implements IPositionValidator {
    private final ITrackConfiguration trackConfiguration;

    /**
     * Constructs a PositionValidator with the specified track configuration.
     *
     * @param trackConfiguration The track configuration to be used for validation.
     * @throws IllegalArgumentException if trackConfiguration is null.
     */
    public PositionValidator(ITrackConfiguration trackConfiguration) {
        if (trackConfiguration == null)
            throw new IllegalArgumentException("Track configuration cannot be null.");

        this.trackConfiguration = trackConfiguration;
    }

    /**
     * Checks if the current position is valid based on the previous position and track configuration.
     *
     * Validity is determined by ensuring the position is within bounds and not blocked,
     * and by checking if the movement adheres to the track's direction constraints.
     *
     * @param currentPosition The current position of the player, represented as an array [x, y].
     * @param previousPosition The previous position of the player, represented as an array [x, y].
     * @return {@code true} if the current position is valid; {@code false} otherwise.
     * @throws IllegalArgumentException if currentPosition or previousPosition is null or not properly formatted.
     * @throws IllegalStateException if no start positions are found in the track configuration.
     */
    public boolean isPositionValid(int[] currentPosition, int[] previousPosition) {
        if (currentPosition == null || previousPosition == null)
            throw new IllegalArgumentException("Positions cannot be null.");

        if (currentPosition.length != 2 || previousPosition.length != 2)
            throw new IllegalArgumentException("Position arrays must contain exactly two elements: [x, y].");


        if (!trackConfiguration.isPositionWithinBounds(currentPosition[0], currentPosition[1]) ||
                trackConfiguration.isPositionBlocked(currentPosition[0], currentPosition[1]))
            return false;


        List<int[]> startingPositions = trackConfiguration.getStartPositions();
        if (startingPositions.isEmpty())
            throw new IllegalStateException("No start positions found.");


        for (int[] startingPosition : startingPositions) {
            if (previousPosition[1] == startingPosition[1]) {
                boolean isCurrentBehindStart;
                boolean isPreviousAheadStart;

                if ("right".equals(trackConfiguration.direction())) {
                    isPreviousAheadStart = previousPosition[0] >= startingPosition[0];
                    isCurrentBehindStart = currentPosition[0] < startingPosition[0];
                } else if ("left".equals(trackConfiguration.direction())) {
                    isPreviousAheadStart = previousPosition[0] <= startingPosition[0];
                    isCurrentBehindStart = currentPosition[0] > startingPosition[0];
                } else
                    throw new IllegalStateException("Direction in track configuration is not valid.");

                if (isCurrentBehindStart && isPreviousAheadStart)
                    return false;

            }
        }

        return true;
    }

    /**
     * Checks if the finish line has been crossed between the previous and current positions.
     *
     * The finish line is considered crossed if the current position is past the finish line
     * relative to the previous position, based on the track's direction.
     *
     * @param currentPosition The current position of the player, represented as an array [x, y].
     * @param previousPosition The previous position of the player, represented as an array [x, y].
     * @return {@code true} if the finish line has been crossed; {@code false} otherwise.
     * @throws IllegalArgumentException if currentPosition or previousPosition is null or not properly formatted.
     */
    public boolean isFinishLineCrossed(int[] currentPosition, int[] previousPosition) {
        if (currentPosition == null || previousPosition == null)
            throw new IllegalArgumentException("Positions cannot be null.");

        if (currentPosition.length != 2 || previousPosition.length != 2)
            throw new IllegalArgumentException("Position arrays must contain exactly two elements: [x, y].");


        for (int[] finishPosition : trackConfiguration.getFinishPositions()) {
            if (currentPosition[1] == finishPosition[1]) {
                if ("right".equals(trackConfiguration.direction())) {
                    if (previousPosition[0] < finishPosition[0] && currentPosition[0] >= finishPosition[0])
                        return true;

                } else if ("left".equals(trackConfiguration.direction())) {
                    if (previousPosition[0] > finishPosition[0] && currentPosition[0] <= finishPosition[0])
                        return true;

                } else
                    throw new IllegalStateException("Direction in track configuration is not valid.");

            }
        }
        return false;
    }
}