package it.unicam.cs.khanshaz123384.utils;/*
 * MIT License
 *
 * Copyright (c) [2024] [Khan Shaz]
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


import it.unicam.cs.khanshaz123384.api.utils.GridParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GridParserTest {

    private static final List<String> VALID_GRID = List.of(
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "##########***************************************###########",
            "#########******************************************#########",
            "#######****S************************************F****#######",
            "#####**************************************************#####",
            "####********####################################********####",
            "###********######################################********###",
            "##********########################################********##",
            "#********##########################################********#",
            "##********########################################********##",
            "###********######################################********###",
            "####********####################################********####",
            "#####**************************************************#####",
            "######************************************************######",
            "#######**********************************************#######",
            "########********************************************########",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################",
            "############################################################"
    );

    @Test
    public void testParseValidGrid() {
        char[][] grid = GridParser.parseGrid(VALID_GRID);

        assertEquals(38, grid.length, "Grid should have 38 rows.");
        assertEquals(60, grid[0].length, "Grid should have 60 columns.");

        assertEquals('S', grid[10][11], "Start 'S' should be at row 10, col 11.");
        assertEquals('F', grid[10][48], "Finish 'F' should be at row 10, col 48.");

        for (int i = 8; i <= 12; i++) {
            assertEquals('S', grid[i][11], "Cells between 'S' should be filled with 'S'.");
            assertEquals('F', grid[i][48], "Cells between 'F' should be filled with 'F'.");
        }
    }

    @Test
    public void testParseGridWithInconsistentRowLength() {
        List<String> invalidGrid = new ArrayList<>(VALID_GRID);
        invalidGrid.set(0, "#############################################################");

        assertThrows(IllegalArgumentException.class, () -> GridParser.parseGrid(invalidGrid));
    }

    @Test
    public void testParseGridWithNoStart() {
        List<String> noStartGrid = new ArrayList<>(VALID_GRID);
        noStartGrid.set(10, noStartGrid.get(10).replace('S', '#')); // Remove 'S'

        assertThrows(IllegalArgumentException.class, () -> GridParser.parseGrid(noStartGrid));
    }

    @Test
    public void testParseGridWithNoFinish() {
        List<String> noFinishGrid = new ArrayList<>(VALID_GRID);
        noFinishGrid.set(10, noFinishGrid.get(10).replace('F', '#')); // Remove 'F'

        assertThrows(IllegalArgumentException.class, () -> GridParser.parseGrid(noFinishGrid));
    }

    @Test
    public void testParseGridWithMultipleStarts() {
        List<String> multipleStartsGrid = new ArrayList<>(VALID_GRID);
        multipleStartsGrid.set(12, multipleStartsGrid.get(12).substring(0, 12) + 'S' + multipleStartsGrid.get(12).substring(13)); // Add another 'S' at row 12

        assertThrows(IllegalArgumentException.class, () -> GridParser.parseGrid(multipleStartsGrid));
    }

    @Test
    public void testParseGridWithMultipleFinishes() {
        List<String> multipleFinishesGrid = new ArrayList<>(VALID_GRID);
        multipleFinishesGrid.set(12, multipleFinishesGrid.get(12).substring(0, 44) + 'F' + multipleFinishesGrid.get(12).substring(45)); // Add another 'F' at row 12

        assertThrows(IllegalArgumentException.class, () -> GridParser.parseGrid(multipleFinishesGrid));
    }

    @Test
    public void testParseGridWithInvalidWalls() {
        List<String> invalidWallsGrid = new ArrayList<>(VALID_GRID);
        invalidWallsGrid.set(20, "#############################################################"); // Invalid wall length

        assertThrows(IllegalArgumentException.class, () -> GridParser.parseGrid(invalidWallsGrid));
    }
}
