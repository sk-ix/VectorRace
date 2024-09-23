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

import it.unicam.cs.khanshaz123384.api.model.HumanPlayer;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.ITrackConfiguration;
import it.unicam.cs.khanshaz123384.api.model.TrackConfiguration;
import it.unicam.cs.khanshaz123384.api.utils.ColorGenerator;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IColorGenerator;
import it.unicam.cs.khanshaz123384.app.controller.PlayerManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerManagerTest {

    private PlayerManager playerManager;

    @BeforeEach
    public void setUp() {
        ITrackConfiguration trackConfiguration = createTrackConfiguration();
        playerManager = new PlayerManager(trackConfiguration);
    }

    @Test
    public void testInitializePlayers() {
        List<IPlayer> players = playerManager.getPlayers();
        assertNotNull(players);
        assertEquals(3, players.size());

        IPlayer player1 = players.getFirst();
        assertEquals("Player1", player1.getName());
        assertArrayEquals(new int[]{0, 0}, player1.getPosition());
    }

    @Test
    public void testUpdatePlayerPosition_ValidMove() {
        IPlayer player = playerManager.getPlayers().getFirst();
        playerManager.updatePlayerPosition(player, 1, 0);
        assertArrayEquals(new int[]{1, 0}, player.getPosition());
    }

    @Test
    public void testUpdatePlayerPosition_InvalidDeltaX() {
        IPlayer player = playerManager.getPlayers().getFirst();
        assertThrows(IllegalArgumentException.class, () -> playerManager.updatePlayerPosition(player, 2, 0));
    }

    @Test
    public void testUpdatePlayerPosition_InvalidDeltaY() {
        IPlayer player = playerManager.getPlayers().getFirst();
        assertThrows(IllegalArgumentException.class, () -> playerManager.updatePlayerPosition(player, 0, -2));
    }

    @Test
    public void testUpdatePlayerPosition_NullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> playerManager.updatePlayerPosition(null, 1, 0));
    }

    @Test
    public void testEliminatePlayer_ValidPlayer() {
        IPlayer player = playerManager.getPlayers().getFirst();
        playerManager.eliminatePlayer(player);
        List<IPlayer> players = playerManager.getPlayers();
        assertFalse(players.contains(player));
    }

    @Test
    public void testEliminatePlayer_NullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> playerManager.eliminatePlayer(null));
    }

    @Test
    public void testFinishPlayerRace_ValidPlayer() {
        IPlayer player = playerManager.getPlayers().getFirst();
        playerManager.finishPlayerRace(player);
        List<IPlayer> finishedPlayers = playerManager.getFinishedPlayers();
        assertTrue(finishedPlayers.contains(player));
        assertEquals(1, player.getRank());
    }

    @Test
    public void testFinishPlayerRace_NullPlayer() {
        assertThrows(IllegalArgumentException.class, () -> playerManager.finishPlayerRace(null));
    }

    @Test
    public void testGetPlayers_NoPlayersFinished() {
        List<IPlayer> finishedPlayers = playerManager.getFinishedPlayers();
        assertTrue(finishedPlayers.isEmpty());
    }

    @Test
    public void testCreateTrackConfiguration_InvalidDirection() {
        assertThrows(IllegalArgumentException.class, this::createInvalidTrackConfiguration);
    }

    @Test
    public void testCreateTrackConfiguration_InvalidGridSize() {
        char[][] smallTrackMap = new char[10][10];
        assertThrows(IllegalArgumentException.class, () -> new TrackConfiguration(smallTrackMap, List.of(), "right"));
    }


    private ITrackConfiguration createTrackConfiguration() {
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

    private void createInvalidTrackConfiguration() {
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

        new TrackConfiguration(trackMap, players, "up");
    }

}
