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

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IColorGenerator;


public abstract class Player implements IPlayer {
    private final String playerName;
    private final String playerColor;
    private final int[] currentPosition;
    private int[] previousPosition;
    private final int[] currentVelocity;
    private int rank;

    public Player(String playerName, int[] currentPosition, int[] currentVelocity, IColorGenerator colorGenerator,int rank) {
        this.playerName = playerName;
        this.currentPosition = currentPosition;
        this.currentVelocity = currentVelocity;
        this.playerColor = colorGenerator.getRandomColor();
        this.previousPosition = currentPosition.clone();
        this.rank = rank;
    }

    public String getName () {
        return playerName;
    }

    public String getColor () {
        return playerColor;
    }

    public int[] getPosition() {
        return currentPosition;
    }

    public int[] getVelocity() {
        return currentVelocity;
    }

    public int[] getPreviousPosition() {
        return previousPosition;
    }

    public int getRank() {
        return rank;
    }

    public abstract String getType();

    public void setPosition (int newPositionX, int newPositionY) {
        this.previousPosition = this.currentPosition.clone();
        this.currentPosition[0] = newPositionX;
        this.currentPosition[1] = newPositionY;
    }

    public void setVelocity (int newVelocityX, int newVelocityY) {
        this.currentVelocity[0] = newVelocityX;
        this.currentVelocity[1] = newVelocityY;
    }

    public void setRank(int newRank) {
        rank = newRank;
    }

}