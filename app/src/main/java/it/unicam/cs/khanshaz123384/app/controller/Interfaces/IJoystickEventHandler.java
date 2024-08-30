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

/**
 * Interface for handling joystick events in the application.
 *
 * <p>This interface defines a method for processing joystick input events that
 * affect the state or behavior of game elements. Implementations of this interface
 * handle the changes in joystick input and apply the corresponding actions.</p>
 */
public interface IJoystickEventHandler {

    /**
     * Handles the joystick event by processing the changes in the x and y coordinates.
     *
     * <p>This method is called when joystick input is detected, and it should update
     * the relevant game state based on the provided changes in the x and y directions.</p>
     *
     * @param deltaX The change in the x-coordinate due to joystick input.
     * @param deltaY The change in the y-coordinate due to joystick input.
     * @throws IllegalArgumentException If {@code deltaX} or {@code deltaY} are not within
     *                                  the acceptable range of -1, 0, or 1.
     */
    void handleJoystickEvent(int deltaX, int deltaY);
}