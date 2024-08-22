package it.unicam.cs.khanshaz123384.api.model;


import it.unicam.cs.khanshaz123384.api.utils.IColorGenerator;

public class BotPlayer extends Player {

    public BotPlayer(String playerName, int[] currentPosition, int[] currentVelocity, IColorGenerator colorGenerator, int rank) {
        super(playerName, currentPosition, currentVelocity, colorGenerator, rank);
    }

    @Override
    public String getType() {
        return "Bot";
    }
}