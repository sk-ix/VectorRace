package it.unicam.cs.khanshaz123384.api.model;

import it.unicam.cs.khanshaz123384.api.utils.IColorGenerator;

public class HumanPlayer extends Player {

    public HumanPlayer(String playerName, int[] currentPosition, int[] currentVelocity, IColorGenerator colorGenerator, int rank) {
        super(playerName, currentPosition, currentVelocity, colorGenerator, rank);
    }

    @Override
    public String getType() {
        return "Human";
    }
}
