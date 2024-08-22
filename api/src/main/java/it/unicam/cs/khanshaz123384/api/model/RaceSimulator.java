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
        currentPlayerIndex = 0;
        random = new Random();
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
            updateRanking();
            if (!players.isEmpty()) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            } else {
                raceRunning = false;
            }
        }
    }

    private void waitForPlayerInput() {
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void updateRanking() {

        players.sort((p1, p2) -> {
            int p1Distance = calculateDistanceFromStart(p1);
            int p2Distance = calculateDistanceFromStart(p2);
            return Integer.compare(p2Distance, p1Distance);
        });

        // Aggiorna il ranking di ogni giocatore
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            player.setRank(i + 1); // Il rank parte da 1
        }

        // Stampa il ranking aggiornato
        System.out.println("Current Ranking:");
        for (Player player : players) {
            System.out.println(player.getRank() + ". " + player.getName());
        }
    }

    private int calculateDistanceFromStart(Player player) {
        int[] startPosition = trackConfiguration.getStartPosition();
        int[] playerPosition = player.getPosition();

        // Dimensioni del circuito
        int numCols = gridMap[0].length; // Numero di colonne
        int numRows = gridMap.length;    // Numero di righe

        // Converti la posizione in un valore unidimensionale
        int start1D = startPosition[1] * numCols + startPosition[0]; // Conversione in 1D
        int player1D = playerPosition[1] * numCols + playerPosition[0]; // Conversione in 1D

        // Calcola la distanza unidimensionale seguendo la direzione oraria

        return (player1D - start1D + numCols * numRows) % (numCols * numRows);
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

    public void notifyPlayerInput() {
        synchronized (this) {
            this.notify();
        }
    }

    public interface PlayerChangeListener {
        void onPlayerChange(Player newPlayer);
    }
}
