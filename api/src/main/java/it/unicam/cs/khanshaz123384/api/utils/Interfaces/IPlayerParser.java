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

package it.unicam.cs.khanshaz123384.api.utils.Interfaces;

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;

import java.util.List;


/**
 * An interface for parsing player information from a list of strings.
 * <p>
 * This interface defines a method for converting a list of string representations of player data
 * into a list of {@link IPlayer} objects. Implementations of this interface are expected to
 * handle the specific format of player data and create corresponding player instances.
 * </p>
 */
public interface IPlayerParser {

    /**
     * Parses a list of strings representing player data and converts them into a list of {@link IPlayer} instances.
     * <p>
     * Each string in the input list represents information about a player. The format and content of these
     * strings are defined by the specific implementation of the parser. This method should handle any required
     * parsing logic to extract player details and create {@link IPlayer} objects accordingly.
     * </p>
     *
     * @param playerLines A list of strings, where each string contains data about a player.
     *                    The format of these strings must be defined and handled by the implementation.
     * @return A list of {@link IPlayer} objects created based on the provided player data.
     *         The returned list should contain one player object for each input string.
     * @throws IllegalArgumentException if any of the input strings are invalid or cannot be parsed
     *                                  into a player object.
     */
    List<IPlayer> parsePlayers(List<String> playerLines);
}