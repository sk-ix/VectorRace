package it.unicam.cs.khanshaz123384.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaceSimulator {
    private final TrackConfiguration trackConfiguration;
    private final List<Player> players;
    private final List<Player> finishedPlayers;
    private final PositionValidator positionValidator;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private boolean raceRunning;
    private PlayerChangeListener playerChangeListener;
    private final Random random;

    public RaceSimulator(TrackConfiguration trackConfiguration, PositionValidator positionValidator) {
        this.trackConfiguration = trackConfiguration;
        this.positionValidator = positionValidator;
        this.players = new ArrayList<>(trackConfiguration.players());
        this.finishedPlayers = new ArrayList<>();
        initializeStartingPositions();
        this.random = new Random();
        this.currentPlayerIndex = 0;
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
            System.out.println("" + currentPlayer.getName() + " Turn");
            processCurrentPlayer();
            checkPosition(currentPlayer);
            updateCurrentPlayerIndex();
        }
    }

    private void processCurrentPlayer() {
        if ("Human".equals(currentPlayer.getType())) {
            waitForPlayerInput();
        } else {
            moveNonHumanPlayer();
        }
    }

    private void moveNonHumanPlayer() {
        int deltaX = random.nextInt(3) - 1;
        int deltaY = random.nextInt(3) - 1;
        currentPlayer.updatePosition(deltaX, deltaY);
        notifyPlayerInput();
    }

    private synchronized void waitForPlayerInput() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void updateCurrentPlayerIndex() {
        if (!players.isEmpty()) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            raceRunning = false;
            System.out.println("Race finished!");
            printRankings();
        }
    }

    public boolean isRaceRunning() {
        return raceRunning;
    }

    private synchronized void checkPosition(Player currentPlayer) {
        int[] currentPosition = currentPlayer.getPosition();
        int[] previousPosition = currentPlayer.getPreviousPosition();

        if (!positionValidator.isPositionValid(currentPosition, previousPosition)) {
            eliminatePlayer(currentPlayer);
        } else if (positionValidator.isFinishLineCrossed(currentPosition, previousPosition)) {
            finishPlayerRace(currentPlayer);
        }

        if (players.isEmpty() && finishedPlayers.size() == trackConfiguration.players().size()) {
            raceRunning = false;
        }
    }

    private void eliminatePlayer(Player player) {
        System.out.println("Player " + player.getName() + " went off track and is eliminated.");
        players.remove(player);
    }

    private void finishPlayerRace(Player player) {
        System.out.println("Player " + player.getName() + " has finished the race.");
        finishedPlayers.add(player);
        player.setRank(finishedPlayers.size());
        players.remove(player);
    }

    public synchronized void setCurrentPlayer(int i) {
        currentPlayer = players.get(i);
        if (playerChangeListener != null) {
            playerChangeListener.onPlayerChange(currentPlayer);
        }
    }

    public void setPlayerChangeListener(PlayerChangeListener listener) {
        this.playerChangeListener = listener;
    }

    public synchronized void notifyPlayerInput() {
        this.notify();
    }

    private void printRankings() {
        System.out.println("Final Rankings:");
        for (Player player : finishedPlayers) {
            System.out.println((player.getRank()) + ". " + player.getName());
        }
    }

    public interface PlayerChangeListener {
        void onPlayerChange(Player newPlayer);
    }
}
