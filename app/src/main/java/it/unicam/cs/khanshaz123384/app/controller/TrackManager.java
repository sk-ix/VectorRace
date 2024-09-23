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

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IJoystickEventHandler;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IRaceSimulator;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.ITrackManager;
import it.unicam.cs.khanshaz123384.app.model.ITrackGrid;
import it.unicam.cs.khanshaz123384.app.model.TrackGrid;

import java.util.List;

/**
 * Manages the track grid, player list, and joystick event handling.
 *
 * <p>This class implements the {@link ITrackManager} interface and is responsible for
 * managing the track grid by updating player positions, handling joystick events
 * and delegating them to the appropriate controller.</p>
 */
public class TrackManager implements ITrackManager {

    private final ITrackGrid trackGrid;
    private final List<IPlayer> players;
    private final IJoystickEventHandler joystickEventHandler;

    /**
     * Constructs a TrackManager instance with the specified grid map, players, and race simulator.
     *
     * <p>This constructor initializes the track grid and joystick event handler using the provided
     * grid map, player list, and race simulator.</p>
     *
     * @param gridMap The 2D character array representing the track grid.
     * @param players The list of players participating in the race.
     * @param raceSimulator The race simulator to handle joystick events.
     * @throws IllegalArgumentException If any of the parameters are null or invalid.
     */
    public TrackManager(char[][] gridMap, List<IPlayer> players, IRaceSimulator raceSimulator) {
        if (gridMap == null)
            throw new IllegalArgumentException("Grid map cannot be null.");

        if (players == null)
            throw new IllegalArgumentException("Players list cannot be null.");

        if (raceSimulator == null)
            throw new IllegalArgumentException("Race simulator cannot be null.");


        this.players = List.copyOf(players);
        this.trackGrid = new TrackGrid(gridMap, players);
        this.joystickEventHandler = new JoystickController(raceSimulator);
    }

    /**
     * Returns the list of players managed by this TrackManager.
     *
     * @return A list of {@link IPlayer} objects.
     */
    @Override
    public List<IPlayer> getPlayers() {
        return players;
    }

    /**
     * Returns the track grid managed by this TrackManager.
     *
     * @return An instance of {@link ITrackGrid}.
     */
    @Override
    public ITrackGrid getTrackGrid() {
        return trackGrid;
    }

    /**
     * Returns the joystick event handler for handling joystick input.
     *
     * @return An instance of {@link IJoystickEventHandler}.
     */
    @Override
    public IJoystickEventHandler getJoystickEventHandler() {
        return joystickEventHandler;
    }

    /**
     * Updates the positions of players on the track grid.
     *
     * <p>This method delegates the player position update task to the track grid instance.</p>
     */
    @Override
    public void updatePlayerPositions() {
        trackGrid.updatePlayerPositions();
    }
}