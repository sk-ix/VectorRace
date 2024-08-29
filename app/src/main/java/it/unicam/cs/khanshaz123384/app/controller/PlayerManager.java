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

import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements IPlayerManager {
    private final List<IPlayer> players;
    private final List<IPlayer> finishedPlayers;

    public PlayerManager(ITrackConfiguration trackConfiguration) {
        this.players = new ArrayList<>(trackConfiguration.getPlayers());
        this.finishedPlayers = new ArrayList<>();
        initializeStartingPositions(trackConfiguration);
    }

    private void initializeStartingPositions(ITrackConfiguration trackConfiguration) {
        List<int[]> startingPositions = trackConfiguration.getStartPositions();
        if (startingPositions.isEmpty()) {
            throw new IllegalStateException("No start positions found.");
        }

        int numStartingPositions = startingPositions.size();
        int positionIndex = 0;

        for (IPlayer player : players) {
            int[] position = startingPositions.get(positionIndex);
            player.setPosition(position[0], position[1]);
            player.setRank(0);
            positionIndex = (positionIndex + 1) % numStartingPositions;
        }
    }

    public void updatePlayerPosition(IPlayer player, int deltaX, int deltaY) {
        int[] velocity = player.getVelocity();
        int[] position = player.getPosition();

        int newVelocityX = velocity[0] + deltaX;
        int newVelocityY = velocity[1] + deltaY;

        player.setVelocity(newVelocityX, newVelocityY);
        player.setPosition(position[0] + newVelocityX, position[1] + newVelocityY);
    }

    public void eliminatePlayer(IPlayer player) {
        System.out.println("Player " + player.getName() + " went off track and is eliminated.");
        players.remove(player);
    }

    public void finishPlayerRace(IPlayer player) {
        System.out.println("Player " + player.getName() + " has finished the race.");
        finishedPlayers.add(player);
        player.setRank(finishedPlayers.size());
        players.remove(player);
    }

    public List<IPlayer> getPlayers() {
        return players;
    }

    public List<IPlayer> getFinishedPlayers() {
        return finishedPlayers;
    }

}
