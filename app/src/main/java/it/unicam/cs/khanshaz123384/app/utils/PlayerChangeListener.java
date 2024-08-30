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

package it.unicam.cs.khanshaz123384.app.utils;

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.ITrackManager;

import javafx.application.Platform;

/**
 * Listens for player changes and updates the track manager accordingly.
 *
 * <p>This class implements {@link IPlayerChangeListener} to handle player updates
 * and ensure that the user interface reflects these changes. It uses JavaFX's
 * {@link Platform#runLater(Runnable)} to ensure thread safety when updating the UI.</p>
 */
public class PlayerChangeListener implements IPlayerChangeListener {

    private final ITrackManager trackManager;

    /**
     * Constructs a {@code PlayerChangeListener} with the specified track manager.
     *
     * @param trackManager The track manager to be updated when a player changes.
     */
    public PlayerChangeListener(ITrackManager trackManager) {
        if (trackManager == null)
            throw new IllegalArgumentException("Track manager cannot be null.");

        this.trackManager = trackManager;
    }

    /**
     * Called when a player change is detected.
     *
     * <p>This method updates the track manager's player positions to reflect the
     * changes. The update operation is scheduled on the JavaFX application thread
     * to ensure it is thread-safe.</p>
     *
     * @param newPlayer The player who has changed. This parameter is not used
     *                  directly in this implementation, but is required by the
     *                  {@link IPlayerChangeListener} interface.
     */
    @Override
    public void onPlayerChange(IPlayer newPlayer) {
        // Schedule the track manager update on the JavaFX application thread
        Platform.runLater(trackManager::updatePlayerPositions);
    }
}