package it.unicam.cs.khanshaz123384.api.model;

import it.unicam.cs.khanshaz123384.api.utils.TrackConfiguration;

import java.util.List;
import java.util.Random;

import java.util.ArrayList;


public class RaceSimulator {
    private final char[][] gridMap;
    private final List<Player> players;
    private final TrackConfiguration trackConfiguration;
    private final List<Player> finishedPlayers; // Lista per tracciare l'ordine di arrivo
    private Player currentPlayer;
    private int currentPlayerIndex;
    private boolean raceRunning;
    private PlayerChangeListener playerChangeListener;
    private final Random random;

    public RaceSimulator(TrackConfiguration trackConfiguration) {
        this.trackConfiguration = trackConfiguration;
        this.gridMap = trackConfiguration.getGrid();
        this.players = trackConfiguration.getPlayers();
        this.finishedPlayers = new ArrayList<>();
        initializeStartingPositions();
        random = new Random();
        currentPlayerIndex = 0;
    }

    private void initializeStartingPositions() {
        List<int[]> startingPositions = trackConfiguration.getStartPositions();

        if (startingPositions.isEmpty()) {
            throw new IllegalStateException("No start positions found.");
        }

        int numStartingPositions = startingPositions.size();
        int positionIndex = 0;

        for (Player player : players) {
            int[] position = startingPositions.get(positionIndex);
            player.setPosition(position[0], position[1]);
            player.setRank(0);
            positionIndex = (positionIndex + 1) % numStartingPositions;
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
                int deltaX = random.nextInt(3) - 1;
                int deltaY = random.nextInt(3) - 1;
                currentPlayer.updatePosition(deltaX, deltaY);
                notifyPlayerInput();
            }

            checkPosition(currentPlayer);

            if (!players.isEmpty()) {
                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            } else if (finishedPlayers.size() == trackConfiguration.getPlayers().size()) {
                raceRunning = false;
                System.out.println("Race finished!");
                printRankings();
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

    public boolean isRaceRunning() {
        return raceRunning;
    }

    public synchronized void checkPosition(Player currentPlayer) {
        if (!trackConfiguration.isPositionValid(currentPlayer.getPosition(), currentPlayer.getPreviousPosition())) {
            System.out.println("Player " + currentPlayer.getName() + " went off track and is eliminated.");
            players.remove(currentPlayer);
        } else if (isFinishLineCrossed(currentPlayer)) {
            System.out.println("Player " + currentPlayer.getName() + " has finished the race.");
            finishedPlayers.add(currentPlayer);
            currentPlayer.setRank(finishedPlayers.size());
            players.remove(currentPlayer);
        }

        // La gara continua finché ci sono giocatori attivi o finché tutti i giocatori hanno finito la gara.
        if (players.isEmpty() && finishedPlayers.size() == trackConfiguration.getPlayers().size()) {
            raceRunning = false;
        }
    }

    private boolean isFinishLineCrossed(Player player) {
        int[] currentPosition = player.getPosition();
        int[] previousPosition = player.getPreviousPosition();

        for (int[] finishPosition : trackConfiguration.getFinishPositions()) {
            if (currentPosition[1] == finishPosition[1]) {
                if ("right".equals(trackConfiguration.getDirection())) {
                    if (previousPosition[0] < finishPosition[0] && currentPosition[0] >= finishPosition[0]) {
                        return true;
                    }
                } else if ("left".equals(trackConfiguration.getDirection())) {
                    if (previousPosition[0] > finishPosition[0] && currentPosition[0] <= finishPosition[0]) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public synchronized void setCurrentPlayer(int i) {
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

    private void printRankings() {
        System.out.println("Final Rankings:");
        for (int i = 0; i < finishedPlayers.size(); i++) {
            Player player = finishedPlayers.get(i);
            System.out.println((i + 1) + ". " + player.getName() + " (Rank: " + player.getRank() + ")");
        }
    }

    public interface PlayerChangeListener {
        void onPlayerChange(Player newPlayer);
    }
}
