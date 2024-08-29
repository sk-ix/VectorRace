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

import it.unicam.cs.khanshaz123384.api.model.BotPlayer;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerActionHandler;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.IPlayerManager;
import it.unicam.cs.khanshaz123384.app.utils.IPlayerChangeListener;

public class PlayerActionHandler implements IPlayerActionHandler {
    private final IPlayerManager playerManager;
    private final IPlayerChangeListener playerChangeListener;

    public PlayerActionHandler(IPlayerManager playerManager, IPlayerChangeListener playerChangeListener) {
        this.playerManager = playerManager;
        this.playerChangeListener = playerChangeListener;
    }

    @Override
    public void processPlayer(IPlayer player) {
        if ("Human".equals(player.getType())) {
            waitForPlayerInput();
        } else if ("Bot".equals(player.getType())) {
            BotPlayer botPlayer = (BotPlayer) player;
            int[] move = botPlayer.getNextMove();
            notifyPlayerInput(player, move[0], move[1]);
        }
    }

    private synchronized void waitForPlayerInput() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void notifyPlayerInput(IPlayer player, int deltaX, int deltaY) {
        if (playerManager != null) {
            playerManager.updatePlayerPosition(player, deltaX, deltaY);
            this.notify();
        }
    }
}
