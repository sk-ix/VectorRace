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

/**
 * Parses track configuration from a list of strings.
 *
 * <p>This class implements the {@link IConfigurationParser} interface to convert a list of configuration
 * lines into a {@link TrackConfiguration} object. It separates grid lines, player lines, and direction
 * from the provided list of strings, performs validation, and delegates player parsing to an
 * {@link IPlayerParser} implementation.</p>
 */
public class TrackConfigurationParser implements IConfigurationParser {

    private final IPlayerParser playerParser;

    /**
     * Constructs a {@code TrackConfigurationParser} with the specified player parser.
     *
     * @param playerParser The parser used to parse player configurations from lines.
     * @throws IllegalArgumentException if {@code playerParser} is null.
     */
    public TrackConfigurationParser(IPlayerParser playerParser) {
        if (playerParser == null)
            throw new IllegalArgumentException("Player parser cannot be null.");

        this.playerParser = playerParser;
    }

    /**
     * Parses the provided lines into a {@link TrackConfiguration}.
     *
     * <p>The method separates the lines into grid lines, player lines, and a direction. It then validates
     * the grid lines and direction, parses the grid and players, and constructs a {@link TrackConfiguration}
     * object. Throws {@link IllegalArgumentException} if the configuration is invalid or incomplete.</p>
     *
     * @param lines A list of strings representing the configuration lines.
     * @return A {@link TrackConfiguration} object constructed from the provided lines.
     * @throws IllegalArgumentException if the configuration is invalid or incomplete.
     */
    @Override
    public TrackConfiguration parse(List<String> lines) {
        if (lines == null)
            throw new IllegalArgumentException("Configuration lines cannot be null.");


        List<String> gridLines = new ArrayList<>();
        List<String> playerLines = new ArrayList<>();
        String direction = null;

        boolean isGrid = true;

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                // Switch from grid to player section upon encountering an empty line.
                isGrid = false;
                continue;
            }

            if (isGrid) {
                // Validate and collect grid lines.
                if (!GridValidator.validateGridLine(line))
                    throw new IllegalArgumentException("Invalid grid line: " + line);

                gridLines.add(line);

            } else {
                // Collect direction and player lines.
                if (line.trim().equalsIgnoreCase("Right") || line.trim().equalsIgnoreCase("Left"))
                    direction = line.trim().toLowerCase();
                 else
                    playerLines.add(line);
            }
        }

        // Ensure that a direction has been specified.
        if (direction == null)
            throw new IllegalArgumentException("No direction specified in the configuration file.");


        // Parse the grid and players using dedicated parsers.
        char[][] grid = GridParser.parseGrid(gridLines); // Static method call for parsing grid
        List<IPlayer> players = playerParser.parsePlayers(playerLines);

        // Ensure that at least one player is specified.
        if (players.isEmpty())
            throw new IllegalArgumentException("No players specified in the configuration file.");

        // Return the constructed TrackConfiguration object.
        return new TrackConfiguration(grid, players, direction);
    }
}