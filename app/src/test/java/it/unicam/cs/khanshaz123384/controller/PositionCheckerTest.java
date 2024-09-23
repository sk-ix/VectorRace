/*
 * MIT License
 *
 * Copyright (c) [2024] [Khan Shaz]
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

package it.unicam.cs.khanshaz123384.controller;

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPositionValidator;
import it.unicam.cs.khanshaz123384.api.model.TrackConfiguration;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IColorGenerator;
import it.unicam.cs.khanshaz123384.app.controller.PlayerManager;
import it.unicam.cs.khanshaz123384.app.controller.PositionChecker;
import it.unicam.cs.khanshaz123384.api.model.HumanPlayer;
import it.unicam.cs.khanshaz123384.api.utils.ColorGenerator;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class PositionCheckerTest {

    private PositionChecker positionChecker;
    private IPlayerManager playerManager;
    private IPlayer player1;
    private IPlayer player2;

    @BeforeEach
    public void setUp() {

        IPositionValidator positionValidator = new IPositionValidator() {
            @Override
            public boolean isPositionValid(int[] currentPosition, int[] previousPosition) {
                return currentPosition[0] >= 0 && currentPosition[0] < 38 &&
                        currentPosition[1] >= 0 && currentPosition[1] < 60;
            }

            @Override
            public boolean isFinishLineCrossed(int[] currentPosition, int[] previousPosition) {
                return currentPosition[0] == 37 && currentPosition[1] == 59;
            }
        };

        playerManager = new PlayerManager(createTrackConfiguration());

        positionChecker = new PositionChecker(positionValidator, playerManager);

        player1 = playerManager.getPlayers().get(0);
        player2 = playerManager.getPlayers().get(1);
    }

    @Test
    public void testValidPlayerPosition() {
        player1.setPosition(10, 10);
        positionChecker.checkPosition(player1);
        assertTrue(playerManager.getPlayers().contains(player1));
    }

    @Test
    public void testInvalidPlayerPosition() {
        player1.setPosition(50, 10);
        positionChecker.checkPosition(player1);
        assertFalse(playerManager.getPlayers().contains(player1));
    }

    @Test
    public void testFinishLineCrossed() {
        player1.setPosition(36, 58);
        positionChecker.checkPosition(player1);
        assertFalse(playerManager.getFinishedPlayers().contains(player1));

        player1.setPosition(37, 59);
        positionChecker.checkPosition(player1);
        assertTrue(playerManager.getFinishedPlayers().contains(player1));
        assertFalse(playerManager.getPlayers().contains(player1));
    }

    @Test
    public void testNullPlayerThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> positionChecker.checkPosition(null));
    }

    @Test
    public void testMultiplePlayersFinishingRace() {
        player1.setPosition(37, 59);
        positionChecker.checkPosition(player1);
        assertTrue(playerManager.getFinishedPlayers().contains(player1));

        player2.setPosition(37, 59);
        positionChecker.checkPosition(player2);
        assertTrue(playerManager.getFinishedPlayers().contains(player2));

        assertEquals(1, player1.getRank());
        assertEquals(2, player2.getRank());
    }

    private TrackConfiguration createTrackConfiguration() {
        IColorGenerator colorGenerator = new ColorGenerator();

        IPlayer player1 = new HumanPlayer("Player1", new int[]{0, 0}, new int[]{0, 0}, colorGenerator, 0);
        IPlayer player2 = new HumanPlayer("Player2", new int[]{1, 1}, new int[]{0, 0}, colorGenerator, 0);
        IPlayer player3 = new HumanPlayer("Player3", new int[]{2, 2}, new int[]{0, 0}, colorGenerator, 0);

        List<IPlayer> players = List.of(player1, player2, player3);

        char[][] trackMap = new char[38][60];
        for (int i = 0; i < 38; i++) {
            for (int j = 0; j < 60; j++) {
                if (i == 0 || i == 37 || j == 0 || j == 59) {
                    trackMap[i][j] = '#';
                } else {
                    trackMap[i][j] = '*';
                }
            }
        }

        trackMap[0][0] = 'S';
        trackMap[37][59] = 'F';

        String direction = "right";
        return new TrackConfiguration(trackMap, players, direction);
    }
}
