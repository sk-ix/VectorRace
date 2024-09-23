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

import it.unicam.cs.khanshaz123384.app.utils.CellFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class CellFactoryTest {

    private final CellFactory cellFactory = new CellFactory();

    @Test
    void testCreateCellPane_SCellType() {
        double cellSize = 50.0;
        Pane cellPane = cellFactory.createCellPane('S', cellSize);

        assertEquals(2, cellPane.getChildren().size());

        Polygon whiteTriangle = (Polygon) cellPane.getChildren().get(0);
        Polygon blackTriangle = (Polygon) cellPane.getChildren().get(1);

        assertEquals(Color.WHITE, whiteTriangle.getFill());
        assertEquals(Color.BLACK, blackTriangle.getFill());

        assertArrayEquals(new Double[]{0.0, 0.0, cellSize, 0.0, 0.0, cellSize}, whiteTriangle.getPoints().toArray());
        assertArrayEquals(new Double[]{cellSize, cellSize, cellSize, 0.0, 0.0, cellSize}, blackTriangle.getPoints().toArray());
    }

    @Test
    void testCreateCellPane_FCellType() {
        double cellSize = 50.0;
        Pane cellPane = cellFactory.createCellPane('F', cellSize);

        assertEquals(2, cellPane.getChildren().size());

        Polygon blackTriangle = (Polygon) cellPane.getChildren().get(0);
        Polygon whiteTriangle = (Polygon) cellPane.getChildren().get(1);

        assertEquals(Color.BLACK, blackTriangle.getFill());
        assertEquals(Color.WHITE, whiteTriangle.getFill());

        assertArrayEquals(new Double[]{0.0, 0.0, cellSize, 0.0, 0.0, cellSize}, blackTriangle.getPoints().toArray());
        assertArrayEquals(new Double[]{cellSize, cellSize, cellSize, 0.0, 0.0, cellSize}, whiteTriangle.getPoints().toArray());
    }

    @Test
    void testCreateCellPane_OtherCellType() {
        double cellSize = 50.0;
        Pane cellPane = cellFactory.createCellPane('A', cellSize);

        assertEquals(1, cellPane.getChildren().size());

        Rectangle cellBackground = (Rectangle) cellPane.getChildren().getFirst();
        assertEquals(Color.WHITE, cellBackground.getFill());
        assertEquals(cellSize, cellBackground.getWidth());
        assertEquals(cellSize, cellBackground.getHeight());
    }

    @Test
    void testCreateCellPane_SmallCellSize() {
        double cellSize = 5.0;
        Pane cellPane = cellFactory.createCellPane('S', cellSize);

        assertEquals(2, cellPane.getChildren().size());

        Polygon whiteTriangle = (Polygon) cellPane.getChildren().get(0);
        Polygon blackTriangle = (Polygon) cellPane.getChildren().get(1);

        assertEquals(Color.WHITE, whiteTriangle.getFill());
        assertEquals(Color.BLACK, blackTriangle.getFill());
    }

    @Test
    void testCreateCellPane_LargeCellSize() {
        double cellSize = 200.0;
        Pane cellPane = cellFactory.createCellPane('F', cellSize);

        assertEquals(2, cellPane.getChildren().size());

        Polygon blackTriangle = (Polygon) cellPane.getChildren().get(0);
        Polygon whiteTriangle = (Polygon) cellPane.getChildren().get(1);

        assertEquals(Color.BLACK, blackTriangle.getFill());
        assertEquals(Color.WHITE, whiteTriangle.getFill());
    }

    @Test
    void testCreateCellPane_EmptyCellType() {
        double cellSize = 50.0;
        Pane cellPane = cellFactory.createCellPane(' ', cellSize);

        assertEquals(1, cellPane.getChildren().size());

        Rectangle cellBackground = (Rectangle) cellPane.getChildren().getFirst();
        assertEquals(Color.WHITE, cellBackground.getFill());
    }

    @Test
    void testCreateCellPane_NonStandardCellType() {
        double cellSize = 50.0;
        Pane cellPane = cellFactory.createCellPane('#', cellSize);

        assertEquals(1, cellPane.getChildren().size());

        Rectangle cellBackground = (Rectangle) cellPane.getChildren().getFirst();
        assertEquals(Color.WHITE, cellBackground.getFill());
    }

    @Test
    void testCreateCellPane_MinimumValidCellSize() {
        double cellSize = 1.0;
        Pane cellPane = cellFactory.createCellPane('S', cellSize);

        assertEquals(2, cellPane.getChildren().size());

        Polygon whiteTriangle = (Polygon) cellPane.getChildren().get(0);
        Polygon blackTriangle = (Polygon) cellPane.getChildren().get(1);

        assertEquals(Color.WHITE, whiteTriangle.getFill());
        assertEquals(Color.BLACK, blackTriangle.getFill());
    }

    @Test
    void testCreateCellPane_MaximumValidCellSize() {
        double cellSize = Double.MAX_VALUE;
        Pane cellPane = cellFactory.createCellPane('F', cellSize);

        assertEquals(2, cellPane.getChildren().size());

        Polygon blackTriangle = (Polygon) cellPane.getChildren().get(0);
        Polygon whiteTriangle = (Polygon) cellPane.getChildren().get(1);

        assertEquals(Color.BLACK, blackTriangle.getFill());
        assertEquals(Color.WHITE, whiteTriangle.getFill());
    }

    @Test
    void testCreateCellPane_InvalidCellSize() {
        Executable executable = () -> cellFactory.createCellPane('S', -10.0);
        assertThrows(IllegalArgumentException.class, executable);
    }
}
