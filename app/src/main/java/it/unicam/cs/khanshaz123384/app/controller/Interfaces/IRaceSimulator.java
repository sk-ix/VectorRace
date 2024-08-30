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

import it.unicam.cs.khanshaz123384.app.utils.IPlayerChangeListener;

/**
 * Interface for simulating a race within the track simulation application.
 *
 * <p>This interface defines methods for managing and controlling the race simulation.
 * It includes starting the simulation, checking its status, handling player inputs,
 * and setting listeners for player status changes.</p>
 */
public interface IRaceSimulator {

    /**
     * Starts the race simulation.
     *
     * <p>This method initiates the race simulation process, managing the state of the race
     * and updating player positions based on simulation logic. It runs in a separate thread
     * to allow the UI to remain responsive during the simulation.</p>
     */
    void raceSimulation();

    /**
     * Checks if the race is currently running.
     *
     * <p>This method provides the current status of the race simulation, indicating whether
     * the race is ongoing or has been completed.</p>
     *
     * @return {@code true} if the race is running, {@code false} otherwise.
     */
    boolean isRaceRunning();

    /**
     * Sets the status of the race simulation.
     *
     * <p>This method allows external components to control whether the race is running or not.
     * It can be used to pause or resume the race simulation.</p>
     *
     * @param raceStatus {@code true} to set the race as running, {@code false} to stop it.
     */
    void setRaceRunning(boolean raceStatus);

    /**
     * Sets a listener to be notified of changes in the players' status.
     *
     * <p>This method allows an external component to register an {@link IPlayerChangeListener}
     * that will receive notifications whenever there is a change in the status of players during
     * the simulation.</p>
     *
     * @param listener An instance of {@link IPlayerChangeListener} to receive player change notifications.
     */
    void setPlayerChangeListener(IPlayerChangeListener listener);

    /**
     * Notifies the simulator of player input from the joystick.
     *
     * <p>This method receives input from the joystick, indicating changes in the X and Y directions,
     * and updates the corresponding player's state in the simulation.</p>
     *
     * @param deltaX The change in the X direction as reported by the joystick.
     * @param deltaY The change in the Y direction as reported by the joystick.
     */
    void notifyPlayerInput(int deltaX, int deltaY);
}