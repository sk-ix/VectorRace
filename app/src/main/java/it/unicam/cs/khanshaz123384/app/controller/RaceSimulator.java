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
import it.unicam.cs.khanshaz123384.api.model.Interfaces.ITrackConfiguration;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerManager;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IRaceSimulator;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerActionHandler;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPositionChecker;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IRaceResultPrinter;
import it.unicam.cs.khanshaz123384.app.utils.IPlayerChangeListener;

public class RaceSimulator implements IRaceSimulator {
    private final IPlayerActionHandler playerActionHandler;
    private final IPositionChecker positionChecker;
    private final IRaceResultPrinter raceResultPrinter;
    private final IPlayerManager playerManager;
    private IPlayerChangeListener playerChangeListener; // Add this field
    private IPlayer currentPlayer;
    private int currentPlayerIndex;
    private boolean raceRunning;

    public RaceSimulator(IPlayerActionHandler playerActionHandler, IPositionChecker positionChecker, IRaceResultPrinter raceResultPrinter, IPlayerManager playerManager) {
        this.playerActionHandler = playerActionHandler;
        this.positionChecker = positionChecker;
        this.raceResultPrinter = raceResultPrinter;
        this.playerManager = playerManager;
        this.currentPlayerIndex = 0;
    }

    @Override
    public void raceSimulation() {
        raceRunning = true;
        while (raceRunning) {
            setCurrentPlayer(currentPlayerIndex);
            System.out.println(currentPlayer.getName() + " Turn");
            playerActionHandler.processPlayer(currentPlayer);
            positionChecker.checkPosition(currentPlayer);
            if (playerChangeListener != null) {
                playerChangeListener.onPlayerChange(currentPlayer);
            }
            updateCurrentPlayerIndex();
        }
    }

    private void updateCurrentPlayerIndex() {
        if (!playerManager.getPlayers().isEmpty()) {
            currentPlayerIndex = (currentPlayerIndex + 1) % playerManager.getPlayers().size();
        } else {
            raceRunning = false;
            System.out.println("Race finished!");
            raceResultPrinter.printRankings();
        }
    }


    public void notifyPlayerInput(int deltaX,int deltaY) {
        playerActionHandler.notifyPlayerInput(currentPlayer,deltaX,deltaY);
    }

    private synchronized void setCurrentPlayer(int i) {
        currentPlayer = playerManager.getPlayers().get(i);
    }

    @Override
    public boolean isRaceRunning() {
        return raceRunning;
    }

    @Override
    public void setPlayerChangeListener(IPlayerChangeListener listener) {
        this.playerChangeListener = listener;
    }
}
