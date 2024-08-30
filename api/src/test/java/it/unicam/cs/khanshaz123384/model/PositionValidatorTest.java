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

package it.unicam.cs.khanshaz123384.model;

import it.unicam.cs.khanshaz123384.api.model.HumanPlayer;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.api.model.PositionValidator;
import it.unicam.cs.khanshaz123384.api.model.TrackConfiguration;
import it.unicam.cs.khanshaz123384.api.utils.ColorGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PositionValidatorTest {

    private PositionValidator positionValidator;
    private TrackConfiguration trackConfiguration;

    @BeforeEach
    void setUp() {
        char[][] grid = new char[38][60];
        List<IPlayer> players = new ArrayList<>();
        players.add(new HumanPlayer("Player1", new int[]{0, 0}, new int[]{1, 0}, new ColorGenerator(), 1));

        // Configurare la griglia e le posizioni di partenza e arrivo
        grid[0][0] = 'S';
        grid[37][59] = 'F';

        trackConfiguration = new TrackConfiguration(grid, players, "right");
        positionValidator = new PositionValidator(trackConfiguration);
    }

    @Test
    void testIsPositionValid() {
        int[] currentPosition = {1, 0};
        int[] previousPosition = {0, 0};
        assertTrue(positionValidator.isPositionValid(currentPosition, previousPosition));
    }

    @Test
    void testIsPositionValidOutOfBounds() {
        int[] currentPosition = {60, 0};
        int[] previousPosition = {59, 0};
        assertFalse(positionValidator.isPositionValid(currentPosition, previousPosition));
    }

    @Test
    void testIsPositionValidBlocked() {
        char[][] grid = trackConfiguration.getGrid();
        grid[5][5] = '#';
        int[] currentPosition = {5, 5};
        int[] previousPosition = {4, 5};
        assertFalse(positionValidator.isPositionValid(currentPosition, previousPosition));
    }

    @Test
    void testIsPositionValidNegativeCoordinates() {
        int[] currentPosition = {-1, -1};
        int[] previousPosition = {0, 0};
        assertFalse(positionValidator.isPositionValid(currentPosition, previousPosition));
    }

    @Test
    void testIsFinishLineCrossed() {
        int[] currentPosition = {60, 37};
        int[] previousPosition = {58, 37};
        assertTrue(positionValidator.isFinishLineCrossed(currentPosition, previousPosition));
    }

    @Test
    void testIsFinishLineNotCrossed() {
        int[] currentPosition = {58, 37};
        int[] previousPosition = {57, 37};
        assertFalse(positionValidator.isFinishLineCrossed(currentPosition, previousPosition));
    }

    @Test
    void testIsFinishLineCrossedWithOutOfBoundsCurrentPosition() {
        int[] currentPosition = {60, 38};
        int[] previousPosition = {59, 37};
        assertFalse(positionValidator.isFinishLineCrossed(currentPosition, previousPosition));
    }
}
