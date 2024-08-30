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

package it.unicam.cs.khanshaz123384.app.model;

/**
 * An interface for managing and updating the track grid in the simulation.
 *
 * <p>This interface defines methods for updating the entire grid as well as updating
 * the positions of players within the grid. Implementations of this interface should
 * provide concrete behavior for rendering and managing grid updates.</p>
 */
public interface ITrackGrid {

    /**
     * Updates the entire grid.
     *
     * <p>This method is responsible for refreshing or re-rendering the grid. It should
     * be called whenever there are changes to the grid that need to be reflected in
     * the visual representation.</p>
     *
     * <p>Implementations should ensure that the grid is updated in a way that reflects
     * any changes in the grid data structure or its visualization.</p>
     */
    void updateGrid();

    /**
     * Updates the positions of players within the grid.
     *
     * <p>This method is responsible for refreshing the positions of players on the grid.
     * It should be called whenever there is a change in player positions that needs to be
     * reflected visually on the grid.</p>
     *
     * <p>Implementations should update the display to show the new positions of the players
     * and ensure that any changes are accurately reflected.</p>
     */
    void updatePlayerPositions();
}