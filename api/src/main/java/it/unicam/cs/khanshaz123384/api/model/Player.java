package it.unicam.cs.khanshaz123384.api.model;

import it.unicam.cs.khanshaz123384.api.utils.IColorGenerator;

public abstract class Player {
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

    public void updatePosition(int deltaX, int deltaY) {

        int[] velocity = getVelocity();
        int[] position = getPosition();

        int newVelocityX = velocity[0] + deltaX;
        int newVelocityY = velocity[1] + deltaY;

        setVelocity(newVelocityX, newVelocityY);

        setPosition(position[0] + newVelocityX, position[1] + newVelocityY);


    }

    public void setRank(int newRank) {
        rank = newRank;
    }


}
