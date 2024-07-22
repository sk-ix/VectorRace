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

package it.unicam.cs.khanshaz123384.api.utils;

import it.unicam.cs.khanshaz123384.api.model.BotPlayer;
import it.unicam.cs.khanshaz123384.api.model.HumanPlayer;
import it.unicam.cs.khanshaz123384.api.model.iPlayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling configuration files related to a racetrack game.
 * This class provides methods to read configuration files, validate their content,
 * and interpret them to produce a track grid and a list of players.
 */
public class FileIO {

    private static final int EXPECTED_ROWS = 33; // Number of rows expected in the grid
    private static final int EXPECTED_COLS = 61; // Number of columns expected in the grid

    /**
     * Reads the contents of a configuration file from the specified path.
     * Each line from the file is returned as a string in a list.
     *
     * @param filePath The path to the configuration file.
     * @return A list of strings, each representing a line from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws IllegalArgumentException If the file does not have a .txt extension.
     */
    public List<String> readFile(String filePath) throws IOException {
        if (!isTxtFile(filePath)) {
            throw new IllegalArgumentException("Invalid file type. Expected a .txt file.");
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Checks if the given file path corresponds to a text file (.txt).
     *
     * @param filePath The path to the file.
     * @return {@code true} if the file has a .txt extension, {@code false} otherwise.
     */
    private boolean isTxtFile(String filePath) {
        String extension = getFileExtension(filePath);
        return "txt".equalsIgnoreCase(extension);
    }

    /**
     * Retrieves the file extension from the given file path.
     *
     * @param filePath The path to the file.
     * @return The file extension in lowercase, or {@code null} if no extension is found.
     * @throws IllegalStateException If the file path is {@code null}.
     */
    private String getFileExtension(String filePath) {
        if (filePath == null) {
            throw new IllegalStateException("File path cannot be null.");
        }

        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

    /**
     * Interprets a list of lines read from the configuration file to produce a track grid and a list of players.
     * The lines are divided into grid lines and player lines based on empty lines in the input.
     *
     * @param lines The lines read from the configuration file.
     * @return A {@link TrackConfiguration} object containing the grid and players.
     * @throws IllegalArgumentException If the file format is invalid, the grid dimensions are incorrect,
     *                                  or no players are specified.
     * @throws IllegalStateException If the lines are {@code null}.
     */
    public TrackConfiguration interpretFile(List<String> lines) {
        List<String> gridLines = new ArrayList<>();
        List<String> playerLines = new ArrayList<>();

        if (lines == null) {
            throw new IllegalStateException("Lines cannot be null.");
        }

        boolean isGrid = true;

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                isGrid = false;
                continue;
            }

            if (isGrid) {
                if (!validateGridLine(line)) {
                    throw new IllegalArgumentException("Invalid grid line: " + line);
                }
                gridLines.add(line);
            } else {
                if (!validatePlayerLine(line)) {
                    throw new IllegalArgumentException("Invalid player line: " + line);
                }
                playerLines.add(line);
            }
        }

        char[][] grid = parseGrid(gridLines);
        List<iPlayer> players = parsePlayers(playerLines);

        if (players.isEmpty()) {
            throw new IllegalArgumentException("No players specified in the configuration file.");
        }

        return new TrackConfiguration(grid, players);
    }

    /**
     * Validates a line from the grid section of the configuration file.
     * A valid grid line must contain only the characters 'S', 'F', '*', and '#'.
     *
     * @param line The line to validate.
     * @return {@code true} if the line contains only valid grid characters, {@code false} otherwise.
     * @throws IllegalStateException If the line is {@code null}.
     */
    private boolean validateGridLine(String line) {
        if (line == null) {
            throw new IllegalStateException("Line cannot be null.");
        }

        for (char c : line.toCharArray()) {
            if (c != 'S' && c != 'F' && c != '*' && c != '#') {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates a line from the player section of the configuration file.
     * A valid player line must be formatted as "playerName, playerType", where playerType is either "human" or "bot".
     *
     * @param line The line to validate.
     * @return {@code true} if the line is correctly formatted, {@code false} otherwise.
     * @throws IllegalStateException If the line is {@code null}.
     */
    private boolean validatePlayerLine(String line) {
        if (line == null) {
            throw new IllegalStateException("Line cannot be null.");
        }

        String[] parts = line.split(",\\s*");
        return parts.length == 2 && ("human".equalsIgnoreCase(parts[1].trim()) || "bot".equalsIgnoreCase(parts[1].trim()));
    }

    /**
     * Converts the list of grid lines into a 2D character array representing the grid.
     * Ensures that the grid contains exactly one 'S' (start) and one 'F' (finish).
     *
     * @param gridLines The grid lines.
     * @return A 2D character array representing the grid.
     * @throws IllegalArgumentException If the grid format is invalid (e.g., inconsistent row lengths) or
     *                                  if there is not exactly one start ('S') and one finish ('F') in the grid.
     * @throws IllegalStateException If the grid lines are {@code null} or empty.
     */
    private char[][] parseGrid(List<String> gridLines) {
        if (gridLines == null || gridLines.isEmpty()) {
            throw new IllegalStateException("Grid lines cannot be null or empty.");
        }

        int rows = gridLines.size();
        if (rows != EXPECTED_ROWS) {
            throw new IllegalArgumentException("Grid must have exactly " + EXPECTED_ROWS + " rows.");
        }

        int cols = gridLines.getFirst().length();
        if (cols != EXPECTED_COLS) {
            throw new IllegalArgumentException("Grid must have exactly " + EXPECTED_COLS + " columns.");
        }

        char[][] grid = new char[rows][cols];
        int startCount = 0;
        int finishCount = 0;

        for (int i = 0; i < rows; i++) {
            String line = gridLines.get(i);
            if (line.length() != cols) {
                throw new IllegalArgumentException("Inconsistent row length in grid configuration.");
            }

            for (char c : line.toCharArray()) {
                if (c == 'S') {
                    startCount++;
                } else if (c == 'F') {
                    finishCount++;
                }
            }

            grid[i] = line.toCharArray();
        }

        if (startCount != 1) {
            throw new IllegalArgumentException("There must be exactly one 'S' in the grid.");
        }

        if (finishCount != 1) {
            throw new IllegalArgumentException("There must be exactly one 'F' in the grid.");
        }

        return grid;
    }

    /**
     * Converts the list of player lines into a list of {@link iPlayer} objects.
     * Each player type must be either "human" or "bot", and each player must have a name.
     *
     * @param playerLines The player lines.
     * @return A list of {@link iPlayer} objects.
     * @throws IllegalArgumentException If a player line is invalid or cannot be parsed.
     * @throws IllegalStateException If the player lines are {@code null} or empty.
     */
    private List<iPlayer> parsePlayers(List<String> playerLines) {
        if (playerLines == null || playerLines.isEmpty()) {
            throw new IllegalStateException("Player lines cannot be null or empty.");
        }

        List<iPlayer> players = new ArrayList<>();

        for (String line : playerLines) {
            String[] parts = line.split(",\\s*");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid player line format: " + line);
            }

            String playerName = parts[0].trim();
            String playerType = parts[1].trim().toLowerCase();

            switch (playerType) {
                case "human":
                    players.add(new HumanPlayer(playerName));
                    break;
                case "bot":
                    players.add(new BotPlayer(playerName));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown player type: " + playerType);
            }
        }

        return players;
    }

    /**
     * Class representing the track configuration.
     * Contains the grid and the list of players as read from the configuration file.
     */
    public static class TrackConfiguration {
        private final char[][] grid;
        private final List<iPlayer> players;

        /**
         * Constructs a {@link TrackConfiguration} object with the specified grid and players.
         *
         * @param grid The grid representing the track.
         * @param players The list of players on the track.
         */
        public TrackConfiguration(char[][] grid, List<iPlayer> players) {
            this.grid = grid;
            this.players = players;
        }

        /**
         * Retrieves the grid representing the track.
         *
         * @return The grid as a 2D character array.
         */
        public char[][] getGrid() {
            return grid;
        }

        /**
         * Retrieves the list of players on the track.
         *
         * @return The list of players.
         */
        public List<iPlayer> getPlayers() {
            return players;
        }
    }
}
