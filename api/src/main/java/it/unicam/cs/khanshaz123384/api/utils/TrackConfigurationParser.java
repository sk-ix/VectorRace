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
import java.util.ArrayList;
import java.util.List;

public class TrackConfigurationParser implements iConfigurationParser {

    private static final int EXPECTED_ROWS = 33;
    private static final int EXPECTED_COLS = 61;

    @Override
    public TrackConfiguration parse(List<String> lines) {
        List<String> gridLines = new ArrayList<>();
        List<String> playerLines = new ArrayList<>();

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

    private boolean validatePlayerLine(String line) {
        if (line == null) {
            throw new IllegalStateException("Line cannot be null.");
        }

        String[] parts = line.split(",\\s*");
        return parts.length == 2 && ("human".equalsIgnoreCase(parts[1].trim()) || "bot".equalsIgnoreCase(parts[1].trim()));
    }

    private char[][] parseGrid(List<String> gridLines) {
        if (gridLines == null || gridLines.isEmpty()) {
            throw new IllegalStateException("Grid lines cannot be null or empty.");
        }

        int rows = gridLines.size();
        if (rows != EXPECTED_ROWS) {
            throw new IllegalArgumentException("Grid must have exactly " + EXPECTED_ROWS + " rows.");
        }

        int cols = gridLines.get(0).length();
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
}
