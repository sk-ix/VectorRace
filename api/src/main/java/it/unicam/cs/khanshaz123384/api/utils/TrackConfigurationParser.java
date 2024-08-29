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

import it.unicam.cs.khanshaz123384.api.model.*;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IConfigurationParser;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IPlayerParser;

import java.util.ArrayList;
import java.util.List;

public class TrackConfigurationParser implements IConfigurationParser {

    private final IPlayerParser playerParser;

    public TrackConfigurationParser(IPlayerParser playerParser) {
        this.playerParser = playerParser;
    }

    @Override
    public TrackConfiguration parse(List<String> lines) {
        List<String> gridLines = new ArrayList<>();
        List<String> playerLines = new ArrayList<>();
        String direction = null;

        boolean isGrid = true;

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                isGrid = false;
                continue;
            }

            if (isGrid) {
                if (!GridValidator.validateGridLine(line)) {
                    throw new IllegalArgumentException("Invalid grid line: " + line);
                }
                gridLines.add(line);
            } else {
                if (line.trim().equalsIgnoreCase("Right") || line.trim().equalsIgnoreCase("Left")) {
                    direction = line.trim().toLowerCase();
                } else {
                    playerLines.add(line);
                }
            }
        }

        if (direction == null) {
            throw new IllegalArgumentException("No direction specified in the configuration file.");
        }

        char[][] grid = GridParser.parseGrid(gridLines); // Call static method
        List<IPlayer> players = playerParser.parsePlayers(playerLines);

        if (players.isEmpty()) {
            throw new IllegalArgumentException("No players specified in the configuration file.");
        }

        return new TrackConfiguration(grid, players, direction);
    }
}
