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

package it.unicam.cs.khanshaz123384.app.controller.Interfaces;

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.app.model.ITrackGrid;

import java.util.List;

/**
 * Interface representing the management of the track and players in the simulation.
 *
 * <p>This interface provides methods for accessing player information, the track grid,
 * handling joystick events, and updating player positions.</p>
 */
public interface ITrackManager {

    /**
     * Retrieves the list of players currently on the track.
     *
     * @return A list of {@link IPlayer} objects representing the players.
     */
    List<IPlayer> getPlayers();

    /**
     * Retrieves the track grid that displays the layout of the track.
     *
     * @return An instance of {@link ITrackGrid} representing the track layout.
     */
    ITrackGrid getTrackGrid();

    /**
     * Retrieves the joystick event handler that manages joystick input.
     *
     * @return An instance of {@link IJoystickEventHandler} that handles joystick events.
     */
    IJoystickEventHandler getJoystickEventHandler();

    /**
     * Updates the positions of players on the track.
     *
     * <p>This method ensures that player positions are synchronized with their current
     * state and are reflected on the track grid.</p>
     */
    void updatePlayerPositions();
}
