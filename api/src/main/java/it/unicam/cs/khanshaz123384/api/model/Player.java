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

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Player {
    private final String playerName;
    private final String color;
    private int positionX;
    private int positionY;
    private int velocityX;
    private int velocityY;
    private static final List<String> AVAILABLE_COLORS = Arrays.asList("Red", "Blue", "Green", "Yellow");
    public Player(String playerName, int positionX, int positionY, int velocityX, int velocityY){
        if (playerName == null || playerName.isEmpty())
            throw new IllegalArgumentException("Player name cannot be null or empty");

        if ((positionX < 0) || (positionY < 0)) {
            throw new IllegalArgumentException("Position must be a non-negative");
        }

        if (velocityX < 0 || velocityY < 0) {
            throw new IllegalArgumentException("Velocity must be a non-negative");
        }

        this.playerName = playerName;
        this.positionX = positionX;
        this.positionY = positionY;
        this.color = getRandomColor();
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    private static String getRandomColor() {
        Random random = new Random();
        return AVAILABLE_COLORS.get(random.nextInt(AVAILABLE_COLORS.size()));
    }

    // Getter per playerName
    public String getPlayerName() {
        return playerName;
    }

    // Getter per color
    public String getColor() {
        return color;
    }

    // Getter per position
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }


    // Setter per position
    public void setPosition(int newPositionX, int newPositionY) {
        this.positionX = newPositionX;
        this.positionY = newPositionY;
    }

    public int getVelocityX() { return velocityX; }

    public int getVelocityY() { return velocityY; }

    public void setVelocity(int newVelocityX, int newVelocityY){
        this.velocityX = newVelocityX;
        this.velocityY = newVelocityY;
    }

    public void updatePosition(int deltaX, int deltaY) {

        if (deltaX < -1 || deltaX > 1 || deltaY < -1 || deltaY > 1) {
            throw new IllegalArgumentException("Delta values must be -1, 0, or 1.");
        }
        System.out.println("Old Velocity"+ getVelocityX() + "" + getVelocityY());
        int newVelocityX = getVelocityX() + deltaX;
        int newVelocityY = getVelocityY() + deltaY;
        setVelocity(newVelocityX, newVelocityY);
        System.out.println("New Velocity"+ getVelocityX() + "" + getVelocityY());

        int newPositionX = getPositionX() + getVelocityX();
        int newPositionY = getPositionY() + getVelocityY();
        setPosition(newPositionX, newPositionY);
        System.out.println("New Position: " + getPositionX() + "" + getPositionY());
    }


    public abstract String getType();
}
