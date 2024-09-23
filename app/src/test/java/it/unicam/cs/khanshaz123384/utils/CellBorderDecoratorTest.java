/*
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

package it.unicam.cs.khanshaz123384.utils;
import it.unicam.cs.khanshaz123384.app.utils.CellBorderDecorator;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellBorderDecoratorTest {

    private final int ROWS = 5;
    private final int COLS = 5;
    private final CellBorderDecorator decorator = new CellBorderDecorator(ROWS, COLS);

    @Test
    void testAddBorders_CellSurroundedByWalls() {
        char[][] gridMap = {
                {'#', '#', '#', '#', '#'},
                {'#', ' ', '#', ' ', '#'},
                {'#', '#', ' ', '#', '#'},
                {'#', ' ', '#', ' ', '#'},
                {'#', '#', '#', '#', '#'}
        };

        Pane cellPane = new Pane();
        decorator.addBorders(cellPane, ' ', gridMap, 2, 2, 50);

        assertEquals(4, cellPane.getChildren().size());

        Rectangle topBorder = (Rectangle) cellPane.getChildren().get(0);
        Rectangle rightBorder = (Rectangle) cellPane.getChildren().get(1);
        Rectangle bottomBorder = (Rectangle) cellPane.getChildren().get(2);
        Rectangle leftBorder = (Rectangle) cellPane.getChildren().get(3);

        assertEquals(Color.BLACK, topBorder.getFill());
        assertEquals(Color.BLACK, rightBorder.getFill());
        assertEquals(Color.BLACK, bottomBorder.getFill());
        assertEquals(Color.BLACK, leftBorder.getFill());

        assertEquals(50, topBorder.getWidth());
        assertEquals(1, topBorder.getHeight());
        assertEquals(1, rightBorder.getWidth());
        assertEquals(50, rightBorder.getHeight());
        assertEquals(50, bottomBorder.getWidth());
        assertEquals(1, bottomBorder.getHeight());
        assertEquals(1, leftBorder.getWidth());
        assertEquals(50, leftBorder.getHeight());
    }

    @Test
    void testAddBorders_CellAtGridEdge() {
        char[][] gridMap = {
                {' ', '#', ' ', '#', ' '},
                {'#', '#', '#', '#', '#'},
                {' ', '#', ' ', '#', ' '},
                {'#', '#', '#', '#', '#'},
                {' ', '#', ' ', '#', ' '}
        };

        Pane cellPane = new Pane();
        decorator.addBorders(cellPane, ' ', gridMap, 0, 0, 50);

        assertEquals(4, cellPane.getChildren().size());

        Rectangle topBorder = (Rectangle) cellPane.getChildren().get(0);
        Rectangle rightBorder = (Rectangle) cellPane.getChildren().get(1);
        Rectangle bottomBorder = (Rectangle) cellPane.getChildren().get(2);
        Rectangle leftBorder = (Rectangle) cellPane.getChildren().get(3);

        assertEquals(Color.CYAN, topBorder.getFill());
        assertEquals(Color.BLACK, rightBorder.getFill());
        assertEquals(Color.BLACK, bottomBorder.getFill());
        assertEquals(Color.CYAN, leftBorder.getFill());

        assertEquals(50, topBorder.getWidth());
        assertEquals(1, topBorder.getHeight());
        assertEquals(1, rightBorder.getWidth());
        assertEquals(50, rightBorder.getHeight());
        assertEquals(50, bottomBorder.getWidth());
        assertEquals(1, bottomBorder.getHeight());
        assertEquals(1, leftBorder.getWidth());
        assertEquals(50, leftBorder.getHeight());
    }

    @Test
    void testAddBorders_CellAdjacentToWall() {
        char[][] gridMap = {
                {' ', '#', ' ', '#', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {'#', ' ', '#', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', '#', ' ', '#', ' '}
        };

        Pane cellPane = new Pane();
        decorator.addBorders(cellPane, ' ', gridMap, 2, 3, 50);

        assertEquals(4, cellPane.getChildren().size());

        Rectangle topBorder = (Rectangle) cellPane.getChildren().get(0);
        Rectangle rightBorder = (Rectangle) cellPane.getChildren().get(1);
        Rectangle bottomBorder = (Rectangle) cellPane.getChildren().get(2);
        Rectangle leftBorder = (Rectangle) cellPane.getChildren().get(3);

        assertEquals(Color.CYAN, topBorder.getFill());
        assertEquals(Color.CYAN, rightBorder.getFill());
        assertEquals(Color.CYAN, bottomBorder.getFill());
        assertEquals(Color.BLACK, leftBorder.getFill());

        assertEquals(50, topBorder.getWidth());
        assertEquals(1, topBorder.getHeight());
        assertEquals(1, rightBorder.getWidth());
        assertEquals(50, rightBorder.getHeight());
        assertEquals(50, bottomBorder.getWidth());
        assertEquals(1, bottomBorder.getHeight());
        assertEquals(1, leftBorder.getWidth());
        assertEquals(50, leftBorder.getHeight());
    }

    @Test
    void testAddBorders_CellIsWall() {
        char[][] gridMap = {
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', '#', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' ', ' '}
        };

        Pane cellPane = new Pane();
        decorator.addBorders(cellPane, '#', gridMap, 2, 2, 50);

        assertEquals(4, cellPane.getChildren().size());

        Rectangle topBorder = (Rectangle) cellPane.getChildren().get(0);
        Rectangle rightBorder = (Rectangle) cellPane.getChildren().get(1);
        Rectangle bottomBorder = (Rectangle) cellPane.getChildren().get(2);
        Rectangle leftBorder = (Rectangle) cellPane.getChildren().get(3);

        assertEquals(Color.CYAN, topBorder.getFill());
        assertEquals(Color.CYAN, rightBorder.getFill());
        assertEquals(Color.CYAN, bottomBorder.getFill());
        assertEquals(Color.CYAN, leftBorder.getFill());

        assertEquals(50, topBorder.getWidth());
        assertEquals(1, topBorder.getHeight());
        assertEquals(1, rightBorder.getWidth());
        assertEquals(50, rightBorder.getHeight());
        assertEquals(50, bottomBorder.getWidth());
        assertEquals(1, bottomBorder.getHeight());
        assertEquals(1, leftBorder.getWidth());
        assertEquals(50, leftBorder.getHeight());
    }

    @Test
    void testAddBorders_InvalidCellSize() {
        Pane cellPane = new Pane();
        char[][] gridMap = new char[ROWS][COLS];

        assertThrows(IllegalArgumentException.class, () -> decorator.addBorders(cellPane, ' ', gridMap, 0, 0, -10));
    }

    @Test
    void testAddBorders_InvalidRowColumnIndex() {
        Pane cellPane = new Pane();
        char[][] gridMap = new char[ROWS][COLS];

        assertThrows(IllegalArgumentException.class, () -> decorator.addBorders(cellPane, ' ', gridMap, -1, 0, 50));
        assertThrows(IllegalArgumentException.class, () -> decorator.addBorders(cellPane, ' ', gridMap, 0, -1, 50));
        assertThrows(IllegalArgumentException.class, () -> decorator.addBorders(cellPane, ' ', gridMap, ROWS, 0, 50));
        assertThrows(IllegalArgumentException.class, () -> decorator.addBorders(cellPane, ' ', gridMap, 0, COLS, 50));
    }

    @Test
    void testAddBorders_NullGridMap() {
        Pane cellPane = new Pane();

        assertThrows(IllegalArgumentException.class, () -> decorator.addBorders(cellPane, ' ', null, 0, 0, 50));
    }

    @Test
    void testAddBorders_NullCellPane() {
        char[][] gridMap = new char[ROWS][COLS];

        assertThrows(IllegalArgumentException.class, () -> decorator.addBorders(null, ' ', gridMap, 0, 0, 50));
    }
}
