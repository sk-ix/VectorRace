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

package it.unicam.cs.khanshaz123384.utils;


import it.unicam.cs.khanshaz123384.api.model.HumanPlayer;
import it.unicam.cs.khanshaz123384.api.model.BotPlayer;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.api.utils.ColorGenerator;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IColorGenerator;
import it.unicam.cs.khanshaz123384.api.utils.Interfaces.IPlayerParser;
import it.unicam.cs.khanshaz123384.api.utils.PlayerParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerParserTest {

    private final IColorGenerator colorGenerator = new ColorGenerator();
    private final IPlayerParser playerParser = new PlayerParser(colorGenerator);

    @Test
    public void testParsePlayers() {
        List<String> playerLines = List.of(
                "Player1, human",
                "Player2, bot"
        );

        List<IPlayer> players = playerParser.parsePlayers(playerLines);

        assertEquals(2, players.size());
        assertInstanceOf(HumanPlayer.class, players.get(0));
        assertInstanceOf(BotPlayer.class, players.get(1));
    }

    @Test
    public void testParsePlayersWithInvalidType() {
        List<String> playerLines = List.of(
                "Player1, unknownType"
        );

        assertThrows(IllegalArgumentException.class, () -> playerParser.parsePlayers(playerLines));
    }

    @Test
    public void testParsePlayersWithEmptyList() {
        List<String> playerLines = List.of();

        assertThrows(IllegalStateException.class, () -> playerParser.parsePlayers(playerLines));
    }

    @Test
    public void testParseMultiplePlayers() {
        List<String> playerLines = List.of(
                "Player1, human",
                "Player2, bot",
                "Player3, human",
                "Player4, bot"
        );

        List<IPlayer> players = playerParser.parsePlayers(playerLines);

        assertEquals(4, players.size());
        assertInstanceOf(HumanPlayer.class, players.get(0));
        assertInstanceOf(BotPlayer.class, players.get(1));
        assertInstanceOf(HumanPlayer.class, players.get(2));
        assertInstanceOf(BotPlayer.class, players.get(3));
    }
}
