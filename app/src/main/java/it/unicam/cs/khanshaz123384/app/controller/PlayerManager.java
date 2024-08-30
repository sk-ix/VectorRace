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

/**
 * Manages players in the race, including their positions, velocities, and race status.
 *
 * <p>This class implements the {@link IPlayerManager} interface and handles player-related
 * operations such as updating positions, eliminating players, and tracking finished players.</p>
 */
public class PlayerManager implements IPlayerManager {
    private final List<IPlayer> players;
    private final List<IPlayer> finishedPlayers;

    /**
     * Constructs a PlayerManager instance with the given track configuration.
     *
     * <p>This constructor initializes the player manager with a list of players and their starting positions
     * based on the provided track configuration.</p>
     *
     * @param trackConfiguration The configuration that provides player and starting position information.
     * @throws IllegalArgumentException If {@code trackConfiguration} is {@code null}.
     */
    public PlayerManager(ITrackConfiguration trackConfiguration) {
        if (trackConfiguration == null)
            throw new IllegalArgumentException("TrackConfiguration cannot be null.");


        this.players = new ArrayList<>(trackConfiguration.getPlayers());
        this.finishedPlayers = new ArrayList<>();
        initializeStartingPositions(trackConfiguration);
    }

    /**
     * Initializes the starting positions of players based on the track configuration.
     *
     * <p>This method assigns starting positions to players from the track configuration. It throws an
     * {@link IllegalStateException} if no starting positions are provided.</p>
     *
     * @param trackConfiguration The configuration providing starting positions.
     * @throws IllegalStateException If no starting positions are found in the configuration.
     */
    private void initializeStartingPositions(ITrackConfiguration trackConfiguration) {
        List<int[]> startingPositions = trackConfiguration.getStartPositions();
        if (startingPositions.isEmpty())
            throw new IllegalStateException("No start positions found.");


        int numStartingPositions = startingPositions.size();
        int positionIndex = 0;

        for (IPlayer player : players) {
            int[] position = startingPositions.get(positionIndex);
            player.setPosition(position[0], position[1]);
            player.setRank(0); // Default rank
            positionIndex = (positionIndex + 1) % numStartingPositions;
        }
    }

    /**
     * Updates the position of the given player based on the provided deltas.
     *
     * <p>This method updates the player's velocity and position according to the
     * specified changes in the X and Y directions. The delta values are constrained
     * to be -1, 0, or 1 to ensure valid movement directions.</p>
     *
     * @param player The player whose position is to be updated.
     * @param deltaX The change in the X direction. Must be -1, 0, or 1.
     * @param deltaY The change in the Y direction. Must be -1, 0, or 1.
     * @throws IllegalArgumentException If {@code player} is {@code null}, or if {@code deltaX} or {@code deltaY} are not -1, 0, or 1.
     */
    public void updatePlayerPosition(IPlayer player, int deltaX, int deltaY) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null.");

        if ((deltaX < -1 || deltaX > 1) || (deltaY < -1 || deltaY > 1))
            throw new IllegalArgumentException("Delta values must be -1, 0, or 1.");

        int[] velocity = player.getVelocity();
        int[] position = player.getPosition();

        int newVelocityX = velocity[0] + deltaX;
        int newVelocityY = velocity[1] + deltaY;

        player.setVelocity(newVelocityX, newVelocityY);
        player.setPosition(position[0] + newVelocityX, position[1] + newVelocityY);
    }

    /**
     * Eliminates a player from the race and logs the elimination.
     *
     * <p>This method removes the player from the list of active players and logs a message indicating that
     * the player has gone off track and is eliminated.</p>
     *
     * @param player The player to be eliminated.
     * @throws IllegalArgumentException If {@code player} is {@code null}.
     */
    public void eliminatePlayer(IPlayer player) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null.");

        System.out.println("Player " + player.getName() + " went off track and is eliminated.");
        players.remove(player);
    }

    /**
     * Marks a player as finished and updates their rank.
     *
     * <p>This method adds the player to the list of finished players, updates their rank, and removes them
     * from the list of active players. It also logs a message indicating that the player has finished the race.</p>
     *
     * @param player The player who has finished the race.
     * @throws IllegalArgumentException If {@code player} is {@code null}.
     */
    public void finishPlayerRace(IPlayer player) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null.");


        System.out.println("Player " + player.getName() + " has finished the race.");
        finishedPlayers.add(player);
        player.setRank(finishedPlayers.size());
        players.remove(player);
    }

    /**
     * Returns the list of players currently in the race.
     *
     * @return The list of active players.
     */
    @Override
    public List<IPlayer> getPlayers() {return players;}

    /**
     * Returns the list of players who have finished the race.
     *
     * @return The list of finished players.
     */
    @Override
    public List<IPlayer> getFinishedPlayers() {return finishedPlayers;}
}