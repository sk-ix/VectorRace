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
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IColorGenerator;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IPlayerParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses player configurations from a list of strings.
 *
 * <p>This class implements the {@link IPlayerParser} interface to convert a list of player configuration
 * lines into a list of {@link IPlayer} objects. It validates each player line and constructs the appropriate
 * player objects based on the type specified.</p>
 */
public class PlayerParser implements IPlayerParser {

    private final IColorGenerator colorGenerator;

    /**
     * Constructs a {@code PlayerParser} with the specified color generator.
     *
     * @param colorGenerator The generator used to create colors for players.
     * @throws IllegalArgumentException if {@code colorGenerator} is null.
     */
    public PlayerParser(IColorGenerator colorGenerator) {
        if (colorGenerator == null)
            throw new IllegalArgumentException("Color generator cannot be null.");

        this.colorGenerator = colorGenerator;
    }

    /**
     * Parses a list of player configuration lines into a list of {@link IPlayer} objects.
     *
     * <p>This method processes each line to create {@link HumanPlayer} or {@link BotPlayer} instances based
     * on the type specified in the line. Lines are expected to be in the format: "name, type", where type is
     * either "human" or "bot".</p>
     *
     * @param playerLines A list of strings, each representing a player configuration.
     * @return A list of {@link IPlayer} objects created from the provided lines.
     * @throws IllegalStateException if {@code playerLines} is null or empty.
     * @throws IllegalArgumentException if a line is invalid or the player type is unknown.
     */
    @Override
    public List<IPlayer> parsePlayers(List<String> playerLines) {
        if (playerLines == null || playerLines.isEmpty())
            throw new IllegalStateException("Player lines cannot be null or empty.");


        List<IPlayer> players = new ArrayList<>();

        for (String line : playerLines) {
            if (!validatePlayerLine(line))
                throw new IllegalArgumentException("Invalid player line format: " + line);


            String[] parts = line.split(",\\s*");
            String playerName = parts[0].trim();
            String playerType = parts[1].trim().toLowerCase();

            switch (playerType) {
                case "human":
                    players.add(new HumanPlayer(playerName, new int[]{0, 0}, new int[]{0, 0}, colorGenerator, 0));
                    break;
                case "bot":
                    players.add(new BotPlayer(playerName, new int[]{0, 0}, new int[]{0, 0}, colorGenerator, 0));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown player type: " + playerType);
            }
        }

        return players;
    }

    /**
     * Validates the format of a player configuration line.
     *
     * <p>The method checks if the line contains exactly two parts separated by a comma and if the second part
     * is either "human" or "bot".</p>
     *
     * @param line The player configuration line to be validated.
     * @return {@code true} if the line is valid; {@code false} otherwise.
     * @throws IllegalStateException if {@code line} is null.
     */
    private boolean validatePlayerLine(String line) {
        if (line == null)
            throw new IllegalStateException("Line cannot be null.");

        String[] parts = line.split(",\\s*");
        return parts.length == 2 && ("human".equalsIgnoreCase(parts[1].trim()) || "bot".equalsIgnoreCase(parts[1].trim()));
    }
}