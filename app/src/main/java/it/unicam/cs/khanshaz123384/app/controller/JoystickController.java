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

import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IJoystickEventHandler;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IRaceSimulator;

/**
 * Handles joystick events by notifying the race simulator of player inputs.
 *
 * <p>This class implements the {@link IJoystickEventHandler} interface and is responsible
 * for processing joystick input events during a race simulation. It ensures that the race
 * simulation is running before passing the input to the simulator.</p>
 */
public class JoystickController implements IJoystickEventHandler {

    private final IRaceSimulator raceSimulator;

    /**
     * Constructs a JoystickController instance with the specified race simulator.
     *
     * <p>This constructor initializes the JoystickController with the race simulator
     * that will handle the joystick events.</p>
     *
     * @param raceSimulator The race simulator to be notified of joystick events.
     * @throws IllegalArgumentException If {@code raceSimulator} is {@code null}.
     */
    public JoystickController(IRaceSimulator raceSimulator) {
        if (raceSimulator == null)
            throw new IllegalArgumentException("RaceSimulator cannot be null.");

        this.raceSimulator = raceSimulator;
    }

    /**
     * Handles joystick events and notifies the race simulator of player input.
     *
     * <p>This method processes the joystick input values and, if the race is currently running,
     * it forwards these inputs to the race simulator for processing. The input values are expected
     * to be -1, 0, or 1 for both deltaX and deltaY.</p>
     *
     * @param deltaX The change in the X direction as reported by the joystick. Expected to be -1, 0, or 1.
     * @param deltaY The change in the Y direction as reported by the joystick. Expected to be -1, 0, or 1.
     * @throws IllegalArgumentException If {@code deltaX} or {@code deltaY} are not -1, 0, or 1.
     */
    @Override
    public void handleJoystickEvent(int deltaX, int deltaY) {
        // Validate that deltaX and deltaY are within the acceptable range
        if (deltaX < -1 || deltaX > 1 || deltaY < -1 || deltaY > 1)
            throw new IllegalArgumentException("Delta values must be -1, 0, or 1.");

        // Check if the race is currently running
        if (raceSimulator.isRaceRunning())
            // Notify the race simulator of the joystick input
            raceSimulator.notifyPlayerInput(deltaX, deltaY);
    }
}