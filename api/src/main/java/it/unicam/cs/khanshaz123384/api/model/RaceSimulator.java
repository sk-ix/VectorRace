package it.unicam.cs.khanshaz123384.api.model;

import it.unicam.cs.khanshaz123384.api.utils.TrackConfiguration;

import java.util.List;
import java.util.Random;

public class RaceSimulator {
    private final char[][] gridMap;
    private final List<Player> players;
    private final TrackConfiguration trackConfiguration;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private boolean raceRunning;
    private PlayerChangeListener playerChangeListener;
    private final Random random;

    public RaceSimulator(TrackConfiguration trackConfiguration) {
        this.trackConfiguration = trackConfiguration;
        this.gridMap = trackConfiguration.getGrid();
        this.players = trackConfiguration.getPlayers();
        initializeStartingPositions();
        random = new Random();
        currentPlayerIndex = 0;
    }

    private void initializeStartingPositions() {
        int[] startingPosition = trackConfiguration.getStartPosition();
        for (Player player : players) {
            player.setPosition(startingPosition[0], startingPosition[1]);
            player.setRank(0);
        }
    }

    public void raceSimulation() {
        raceRunning = true;

        while (raceRunning) {
            setCurrentPlayer(currentPlayerIndex);
            System.out.println("Current Player: " + currentPlayer.getName());
            if (currentPlayer.getType().equals("Human")) {
                waitForPlayerInput();
            } else {
                // Simula una mossa per il bot
                int deltaX = random.nextInt(3) - 1;
                int deltaY = random.nextInt(3) - 1;
                currentPlayer.updatePosition(deltaX, deltaY);
                notifyPlayerInput(); // Avanza al prossimo giocatore
            }
            checkPosition(currentPlayer);
            if (!players.isEmpty()) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            } else {
                raceRunning = false;
            }
        }
    }

    private synchronized void waitForPlayerInput() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }



    public void checkPosition(Player currentPlayer) {
        if(!trackConfiguration.isPositionValid(currentPlayer.getPosition())){
            System.out.println("Player " + currentPlayer.getName() + " went off track and is eliminated.");
            players.remove(currentPlayer);

        }
        if (players.isEmpty())
            raceRunning = false;

    }

    public void setCurrentPlayer(int i) {
        currentPlayer = players.get(i);
        if (playerChangeListener != null) {
            playerChangeListener.onPlayerChange(currentPlayer);
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setPlayerChangeListener(PlayerChangeListener listener) {
        this.playerChangeListener = listener;
    }

    public synchronized void notifyPlayerInput() {
        this.notify();
    }

    public interface PlayerChangeListener {
        void onPlayerChange(Player newPlayer);
    }
}
