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

/**
 * An interface for listening to player change events.
 *
 * <p>This interface provides a callback method that is invoked when a player change event occurs.
 * Implementations of this interface can handle the change in player information by implementing
 * the {@link #onPlayerChange(IPlayer)} method.</p>
 */
public interface IPlayerChangeListener {

    /**
     * Called when the player has changed.
     *
     * @param newPlayer The new player instance. Must not be {@code null}.
     * @throws IllegalArgumentException if {@code newPlayer} is {@code null}.
     */
    void onPlayerChange(IPlayer newPlayer);
}