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

package it.unicam.cs.khanshaz123384.app.controller;

import it.unicam.cs.khanshaz123384.api.model.Player;
import it.unicam.cs.khanshaz123384.api.model.RaceSimulator;
import it.unicam.cs.khanshaz123384.app.view.TrackGrid;

import java.util.List;

public class TrackManager implements ITrackManager, RaceSimulator.PlayerChangeListener {

    private final TrackGrid trackGrid;
    private final List<Player> players;
    private final JoystickEventHandler joystickEventHandler;
    private Player currentPlayer;

    public TrackManager(char[][] gridMap, List<Player> players, RaceSimulator raceSimulator) {
        this.players = players;
        this.trackGrid = new TrackGrid(gridMap, players);
        this.joystickEventHandler = new JoystickController(this, raceSimulator);
        raceSimulator.setPlayerChangeListener(this);
    }

    @Override
    public void updatePositions() {
        trackGrid.updatePlayerPositions();
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public TrackGrid getTrackGrid() {
        return trackGrid;
    }

    @Override
    public JoystickEventHandler getJoystickEventHandler() {
        return joystickEventHandler;
    }

    @Override
    public void onPlayerChange(Player newPlayer) {
        this.currentPlayer = newPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
