
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

package it.unicam.cs.khanshaz123384.api.model;

import it.unicam.cs.khanshaz123384.api.model.iPlayer;

/**
 * Represents a human player in the vector racing game.
 * Implements the iPlayer interface to manage the player's position, velocity, and movement.
 * This class provides methods to control the player's movement based on user input.
 */
public class HumanPlayer implements iPlayer {

    private int x;
    private int y;
    private int velocityX;
    private int velocityY;
    private final String playerName;

    /**
     * Constructs a HumanPlayer instance with the specified player name.
     *
     * @param playerName The name of the human player.
     */
    public HumanPlayer(String playerName) {
        this.playerName = playerName;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    /**
     * Initializes the human player at the given starting coordinates with zero velocity.
     * This method should be called before the player starts moving.
     *
     * @param startX The starting x-coordinate of the player.
     * @param startY The starting y-coordinate of the player.
     */
    @Override
    public void initialize(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    /**
     * Retrieves the type of the player, which is "Human" in this case.
     *
     * @return The type of the player ("Human").
     */
    @Override
    public String getType() {
        return "Human";
    }

    /**
     * Retrieves the name of the player.
     *
     * @return The name of the player.
     */
    @Override
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Updates the human player's velocity based on user input.
     * The velocity components (vX, vY) can be adjusted by -1, 0, or +1.
     * For example, passing (1, -1) will increase the x-component of the velocity by 1 and decrease the y-component by 1.
     *
     * @param deltaVx The change in velocity along the x-axis. Must be -1, 0, or +1.
     * @param deltaVy The change in velocity along the y-axis. Must be -1, 0, or +1.
     */
    @Override
    public void updateVelocity(int deltaVx, int deltaVy) {
        this.velocityX += deltaVx;
        this.velocityY += deltaVy;
    }

    /**
     * Moves the human player according to the current velocity.
     * The player's position is updated by adding the current velocity components to the current position.
     */
    @Override
    public void makeMove() {
        this.x += velocityX;
        this.y += velocityY;
    }

    /**
     * Retrieves the current x-coordinate of the player's position.
     *
     * @return The current x-coordinate of the player.
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * Retrieves the current y-coordinate of the player's position.
     *
     * @return The current y-coordinate of the player.
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * Retrieves the current velocity of the player.
     * The velocity is represented as an array where the first element is the x-component and the second element is the y-component.
     *
     * @return An array where the first element is the x-component of velocity and the second element is the y-component.
     */
    @Override
    public int[] getVelocity() {
        return new int[]{velocityX, velocityY};
    }

    /**
     * Sets the player's position directly. This can be useful for resetting the player's position or repositioning them.
     *
     * @param x The new x-coordinate of the player.
     * @param y The new y-coordinate of the player.
     */
    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
