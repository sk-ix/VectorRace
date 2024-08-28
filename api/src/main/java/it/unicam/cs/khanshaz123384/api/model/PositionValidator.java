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

import java.util.List;

public class PositionValidator {
    private final TrackConfiguration trackConfiguration;

    public PositionValidator(TrackConfiguration trackConfiguration) {
        this.trackConfiguration = trackConfiguration;
    }

    public boolean isPositionValid(int[] currentPosition, int[] previousPosition) {
        if (!trackConfiguration.isPositionWithinBounds(currentPosition[0], currentPosition[1]) ||
                trackConfiguration.isPositionBlocked(currentPosition[0], currentPosition[1])) {
            return false;
        }

        List<int[]> startingPositions = trackConfiguration.getStartPositions();
        if (startingPositions.isEmpty()) {
            throw new IllegalStateException("No start positions found.");
        }

        for (int[] startingPosition : startingPositions) {
            if (previousPosition[1] == startingPosition[1]) {
                boolean isCurrentBehindStart = false;
                boolean isPreviousAheadStart = false;

                if ("right".equals(trackConfiguration.direction())) {
                    isPreviousAheadStart = previousPosition[0] >= startingPosition[0];
                    isCurrentBehindStart = currentPosition[0] < startingPosition[0];
                } else if ("left".equals(trackConfiguration.direction())) {
                    isPreviousAheadStart = previousPosition[0] <= startingPosition[0];
                    isCurrentBehindStart = currentPosition[0] > startingPosition[0];
                }

                if (isCurrentBehindStart && isPreviousAheadStart) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isFinishLineCrossed(int[] currentPosition, int[] previousPosition) {
        for (int[] finishPosition : trackConfiguration.getFinishPositions()) {
            if (currentPosition[1] == finishPosition[1]) {
                if ("right".equals(trackConfiguration.direction())) {
                    if (previousPosition[0] < finishPosition[0] && currentPosition[0] >= finishPosition[0]) {
                        return true;
                    }
                } else if ("left".equals(trackConfiguration.direction())) {
                    if (previousPosition[0] > finishPosition[0] && currentPosition[0] <= finishPosition[0]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
