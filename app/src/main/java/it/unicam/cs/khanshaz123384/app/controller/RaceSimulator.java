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
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerManager;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IRaceSimulator;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerActionHandler;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPositionChecker;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IRaceResultPrinter;
import it.unicam.cs.khanshaz123384.app.utils.IPlayerChangeListener;

/**
 * Handles the simulation of the race by managing player turns, position checks,
 * and race results.
 *
 * <p>This class implements the {@link IRaceSimulator} interface and coordinates
 * the race simulation process, including handling player actions, checking player
 * positions, and printing race results.</p>
 */
public class RaceSimulator implements IRaceSimulator {

    private final IPlayerActionHandler playerActionHandler;
    private final IPositionChecker positionChecker;
    private final IRaceResultPrinter raceResultPrinter;
    private final IPlayerManager playerManager;
    private IPlayerChangeListener playerChangeListener;
    private IPlayer currentPlayer;
    private int currentPlayerIndex;
    private boolean raceRunning;

    /**
     * Constructs a RaceSimulator instance with the specified action handler,
     * position checker, result printer, and player manager.
     *
     * <p>This constructor initializes the RaceSimulator with the necessary components
     * to manage the race simulation.</p>
     *
     * @param playerActionHandler The handler for processing player actions.
     * @param positionChecker The checker for validating player positions.
     * @param raceResultPrinter The printer for displaying race results.
     * @param playerManager The manager for player management.
     * @throws IllegalArgumentException If any of the parameters are null.
     */
    public RaceSimulator(IPlayerActionHandler playerActionHandler, IPositionChecker positionChecker,
                         IRaceResultPrinter raceResultPrinter, IPlayerManager playerManager) {
        if (playerActionHandler == null)
            throw new IllegalArgumentException("PlayerActionHandler cannot be null.");

        if (positionChecker == null)
            throw new IllegalArgumentException("PositionChecker cannot be null.");

        if (raceResultPrinter == null)
            throw new IllegalArgumentException("RaceResultPrinter cannot be null.");

        if (playerManager == null)
            throw new IllegalArgumentException("PlayerManager cannot be null.");


        this.playerActionHandler = playerActionHandler;
        this.positionChecker = positionChecker;
        this.raceResultPrinter = raceResultPrinter;
        this.playerManager = playerManager;
        this.currentPlayerIndex = 0;
    }

    /**
     * Starts the race simulation and manages player turns and actions.
     *
     * <p>This method runs a loop that processes each player's turn, checks their positions,
     * and updates the race status. The simulation continues until all players have finished
     * the race.</p>
     */
    @Override
    public void raceSimulation() {
        setRaceRunning(true);
        while (raceRunning) {
            // Set the current player based on the index and start their turn
            setCurrentPlayer(currentPlayerIndex);
            System.out.println(currentPlayer.getName() + " Turn");

            // Process the current player's actions
            playerActionHandler.processPlayer(currentPlayer);

            // Check the current player's position for race completion or other status
            positionChecker.checkPosition(currentPlayer);

            // Notify the player change listener
            playerChangeListener.onPlayerChange(currentPlayer);

            // Update the index to the next player
            updateCurrentPlayerIndex();
        }
    }

    /**
     * Updates the index of the current player to the next player in the list.
     *
     * <p>If all players have finished, the race is stopped and results are printed.</p>
     */
    private void updateCurrentPlayerIndex() {
        if (!playerManager.getPlayers().isEmpty()) {
            // Move to the next player in the list, looping back to the start if necessary
            currentPlayerIndex = (currentPlayerIndex + 1) % playerManager.getPlayers().size();
        } else {
            // No players left, end the race and print the results
            setRaceRunning(false);
            System.out.println("Race finished!");
            raceResultPrinter.printRankings();
        }
    }

    /**
     * Notifies the action handler of player input changes.
     *
     * <p>This method is called to notify the action handler of joystick inputs for the
     * current player.</p>
     *
     * @param deltaX The change in the X direction.
     * @param deltaY The change in the Y direction.
     */
    public void notifyPlayerInput(int deltaX, int deltaY) {
        if (currentPlayer != null)
            playerActionHandler.notifyPlayerInput(currentPlayer, deltaX, deltaY);
        else
            System.err.println("Current player is not set.");

    }

    /**
     * Sets the current player for the simulation based on the provided index.
     *
     * @param i The index of the current player.
     */
    private synchronized void setCurrentPlayer(int i) {

        if (i >= 0 && i < playerManager.getPlayers().size())
            currentPlayer = playerManager.getPlayers().get(i);
        else
            throw new IndexOutOfBoundsException("Player index out of bounds.");

    }

    /**
     * Checks if the race simulation is currently running.
     *
     * @return {@code true} if the race is running, {@code false} otherwise.
     */
    @Override
    public boolean isRaceRunning() { return raceRunning;}


    /**
     * Sets the status of the race simulation.
     *
     * @param raceStatus {@code true} to start or resume the race; {@code false} to stop or pause it.
     */
    public void setRaceRunning(boolean raceStatus) {
        this.raceRunning = raceStatus;
    }

    /**
     * Sets a listener to be notified when the current player changes.
     *
     * @param listener The listener to be set.
     */
    @Override
    public void setPlayerChangeListener(IPlayerChangeListener listener) {
        this.playerChangeListener = listener;
    }
}