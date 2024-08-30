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
 * <p>This class implements the {@link IPositionValidator} interface and provides methods to
 * validate if a player's current position is valid based on their previous position
 * and the track configuration. It also checks if the player has crossed the finish line.</p>
 *
 * <p>The class relies on a {@link ITrackConfiguration} instance to determine the boundaries,
 * blocked positions, starting positions, and finish positions on the track.</p>
 */
public class PositionValidator implements IPositionValidator {
    private final ITrackConfiguration trackConfiguration;

    /**
     * Constructs a {@code PositionValidator} with the specified track configuration.
     *
     * @param trackConfiguration The track configuration to be used for validation.
     * @throws IllegalArgumentException if {@code trackConfiguration} is null.
     */
    public PositionValidator(ITrackConfiguration trackConfiguration) {
        if (trackConfiguration == null)
            throw new IllegalArgumentException("Track configuration cannot be null.");

        this.trackConfiguration = trackConfiguration;
    }

    /**
     * Checks if the current position is valid based on the previous position and the track configuration.
     *
     * <p>Validity is determined by ensuring the position is within bounds, not blocked, and by checking if
     * the movement adheres to the track's direction constraints.</p>
     *
     * @param currentPosition  The current position of the player, represented as an array {@code [x, y]}.
     * @param previousPosition The previous position of the player, represented as an array {@code [x, y]}.
     * @return {@code true} if the current position is valid; {@code false} otherwise.
     * @throws IllegalArgumentException if {@code currentPosition} or {@code previousPosition} is null or not properly formatted.
     * @throws IllegalStateException if no start positions are found in the track configuration.
     */
    @Override
    public boolean isPositionValid(int[] currentPosition, int[] previousPosition) {
        if (currentPosition == null || previousPosition == null)
            throw new IllegalArgumentException("Positions cannot be null.");

        if (currentPosition.length != 2 || previousPosition.length != 2)
            throw new IllegalArgumentException("Position arrays must contain exactly two elements: [x, y].");

        // Check if the current position is within the grid bounds and not blocked.
        if (!trackConfiguration.isPositionWithinBounds(currentPosition[0], currentPosition[1]) ||
                trackConfiguration.isPositionBlocked(currentPosition[0], currentPosition[1]))
            return false;

        // Retrieve the list of starting positions from the track configuration.
        List<int[]> startingPositions = trackConfiguration.getStartPositions();
        if (startingPositions.isEmpty())
            throw new IllegalStateException("No start positions found.");

        // Loop through each starting position to determine if the current movement violates the start line rules.
        for (int[] startingPosition : startingPositions) {

            // Check if the player is in the same row as a start position.
            if (previousPosition[1] == startingPosition[1]) {
                boolean isCurrentBehindStart;
                boolean isPreviousAheadStart;

                // Determine if the player has moved in the wrong direction based on track configuration.
                if ("right".equals(trackConfiguration.getDirection())) {
                    isPreviousAheadStart = previousPosition[0] >= startingPosition[0];
                    isCurrentBehindStart = currentPosition[0] < startingPosition[0];
                } else if ("left".equals(trackConfiguration.getDirection())) {
                    isPreviousAheadStart = previousPosition[0] <= startingPosition[0];
                    isCurrentBehindStart = currentPosition[0] > startingPosition[0];
                } else
                    throw new IllegalStateException("Direction in track configuration is not valid.");

                // If the player crosses the start line in the wrong direction, the move is invalid.
                if (isCurrentBehindStart && isPreviousAheadStart)
                    return false;

            }
        }

        // If all checks pass, the position is valid.
        return true;
    }

    /**
     * Checks if the finish line has been crossed between the previous and current positions.
     *
     * <p>The finish line is considered crossed if the current position is past the finish line
     * relative to the previous position, based on the track's direction.</p>
     *
     * @param currentPosition  The current position of the player, represented as an array {@code [x, y]}.
     * @param previousPosition The previous position of the player, represented as an array {@code [x, y]}.
     * @return {@code true} if the finish line has been crossed; {@code false} otherwise.
     * @throws IllegalArgumentException if {@code currentPosition} or {@code previousPosition} is null or not properly formatted.
     */
    @Override
    public boolean isFinishLineCrossed(int[] currentPosition, int[] previousPosition) {
        if (currentPosition == null || previousPosition == null)
            throw new IllegalArgumentException("Positions cannot be null.");

        if (currentPosition.length != 2 || previousPosition.length != 2)
            throw new IllegalArgumentException("Position arrays must contain exactly two elements: [x, y].");

        // Iterate through each finish position to check if the player has crossed the finish line.
        for (int[] finishPosition : trackConfiguration.getFinishPositions()) {

            // Check if the player is in the same row as a finish position.
            if (currentPosition[1] == finishPosition[1]) {

                // Determine if the finish line has been crossed based on the track's direction.
                if ("right".equals(trackConfiguration.getDirection())) {
                    if (previousPosition[0] < finishPosition[0] && currentPosition[0] >= finishPosition[0])
                        return true;

                } else if ("left".equals(trackConfiguration.getDirection())) {
                    if (previousPosition[0] > finishPosition[0] && currentPosition[0] <= finishPosition[0])
                        return true;

                } else
                    throw new IllegalStateException("Direction in track configuration is not valid.");

            }
        }

        // If no finish line crossing is detected, return false.
        return false;
    }
}