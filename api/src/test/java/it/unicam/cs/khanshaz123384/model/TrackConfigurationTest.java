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
import it.unicam.cs.khanshaz123384.api.model.TrackConfiguration;
import it.unicam.cs.khanshaz123384.api.utils.ColorGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrackConfigurationTest {

    private char[][] validGrid;
    private List<IPlayer> players;
    private TrackConfiguration trackConfiguration;

    @BeforeEach
    void setUp() {
        validGrid = new char[38][60];
        players = new ArrayList<>();
        for (int i = 0; i < 38; i++) {
            for (int j = 0; j < 60; j++) {
                validGrid[i][j] = '.';
            }
        }
        players.add(new HumanPlayer("Player1", new int[]{0, 0}, new int[]{1, 0}, new ColorGenerator(), 1));
        players.add(new HumanPlayer("Player2", new int[]{1, 1}, new int[]{1, 0}, new ColorGenerator(), 2));

        trackConfiguration = new TrackConfiguration(validGrid, players, "right");
    }

    @Test
    void testValidTrackConfigurationCreation() {
        assertNotNull(trackConfiguration);
    }

    @Test
    void testInvalidGridSize() {
        char[][] invalidGrid = new char[37][60];
        assertThrows(IllegalArgumentException.class, () -> new TrackConfiguration(invalidGrid, players, "right"));
    }

    @Test
    void testNullGrid() {
        assertThrows(IllegalArgumentException.class, () -> new TrackConfiguration(null, players, "right"));
    }

    @Test
    void testNullPlayers() {
        assertThrows(IllegalArgumentException.class, () -> new TrackConfiguration(validGrid, null, "right"));
    }

    @Test
    void testEmptyPlayers() {
        List<IPlayer> emptyPlayers = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new TrackConfiguration(validGrid, emptyPlayers, "right"));
    }

    @Test
    void testInvalidDirection() {
        assertThrows(IllegalArgumentException.class, () -> new TrackConfiguration(validGrid, players, "up"));
    }

    @Test
    void testNullDirection() {
        assertThrows(IllegalArgumentException.class, () -> new TrackConfiguration(validGrid, players, null));
    }

    @Test
    void testIsPositionWithinBounds() {
        assertTrue(trackConfiguration.isPositionWithinBounds(0, 0));
        assertFalse(trackConfiguration.isPositionWithinBounds(60, 38));
    }

    @Test
    void testIsPositionBlocked() {
        validGrid[5][5] = '#';
        trackConfiguration = new TrackConfiguration(validGrid, players, "right");
        assertTrue(trackConfiguration.isPositionBlocked(5, 5));
        assertFalse(trackConfiguration.isPositionBlocked(0, 0));
    }

    @Test
    void testIsPositionBlockedWithInvalidCoordinates() {
        assertThrows(IndexOutOfBoundsException.class, () -> trackConfiguration.isPositionBlocked(-1, -1));
        assertThrows(IndexOutOfBoundsException.class, () -> trackConfiguration.isPositionBlocked(60, 38));
    }

    @Test
    void testGetStartPositions() {
        validGrid[0][0] = 'S';
        trackConfiguration = new TrackConfiguration(validGrid, players, "right");
        List<int[]> startPositions = trackConfiguration.getStartPositions();
        assertEquals(1, startPositions.size());
        assertArrayEquals(new int[]{0, 0}, startPositions.getFirst());
    }

    @Test
    void testGetStartPositionsWithNoStartLine() {
        assertThrows(IllegalStateException.class, () -> trackConfiguration.getStartPositions());
    }

    @Test
    void testGetFinishPositions() {
        validGrid[37][59] = 'F';
        trackConfiguration = new TrackConfiguration(validGrid, players, "right");
        List<int[]> finishPositions = trackConfiguration.getFinishPositions();
        assertEquals(1, finishPositions.size());
        assertArrayEquals(new int[]{59, 37}, finishPositions.getFirst());
    }

    @Test
    void testGetFinishPositionsWithNoFinishLine() {
        assertThrows(IllegalStateException.class, () -> trackConfiguration.getFinishPositions());
    }
}
