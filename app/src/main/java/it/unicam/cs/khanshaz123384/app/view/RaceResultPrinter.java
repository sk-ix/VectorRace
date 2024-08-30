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

package it.unicam.cs.khanshaz123384.app.view;

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IRaceResultPrinter;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerManager;

/**
 * Prints the final rankings of players after the race has concluded.
 *
 * <p>This class implements the {@link IRaceResultPrinter} interface and is responsible
 * for displaying the results of the race, specifically the final rankings of the players.</p>
 */
public class RaceResultPrinter implements IRaceResultPrinter {

    private final IPlayerManager playerManager;

    /**
     * Constructs a RaceResultPrinter instance with the specified player manager.
     *
     * <p>This constructor initializes the RaceResultPrinter with the player manager,
     * which provides access to the list of finished players and their rankings.</p>
     *
     * @param playerManager The manager that handles player information and rankings.
     * @throws IllegalArgumentException If playerManager is null.
     */
    public RaceResultPrinter(IPlayerManager playerManager) {
        if (playerManager == null)
            throw new IllegalArgumentException("PlayerManager cannot be null.");

        this.playerManager = playerManager;
    }

    /**
     * Prints the final rankings of players to the console.
     *
     * <p>This method retrieves the list of finished players from the player manager and
     * prints their rankings in the format: rank. playerName.</p>
     */
    @Override
    public void printRankings() {
        System.out.println("Final Rankings:");
        // Retrieve the list of finished players from the player manager
        for (IPlayer player : playerManager.getFinishedPlayers()) {
            // Print each player's rank and name
            System.out.println(player.getRank() + ". " + player.getName());
        }
    }
}