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

package it.unicam.cs.khanshaz123384.app.utils;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CellBorderDecorator {

    private final int ROWS;
    private final int COLS;

    public CellBorderDecorator(int rows, int cols) {
        this.ROWS = rows;
        this.COLS = cols;
    }

    public void addBorders(Pane cellPane, char cellType, char[][] gridMap, int row, int col, double cellSize) {
        Rectangle topBorder = createBorder(cellSize, 1, 0, 0);
        Rectangle rightBorder = createBorder(1, cellSize, cellSize - 1, 0);
        Rectangle bottomBorder = createBorder(cellSize, 1, 0, cellSize - 1);
        Rectangle leftBorder = createBorder(1, cellSize, 0, 0);

        topBorder.setFill(Color.CYAN);
        rightBorder.setFill(Color.CYAN);
        bottomBorder.setFill(Color.CYAN);
        leftBorder.setFill(Color.CYAN);

        if (cellType != '#') {
            if (row > 0 && gridMap[row - 1][col] == '#') {
                topBorder.setFill(Color.BLACK);
            }
            if (col < COLS - 1 && gridMap[row][col + 1] == '#') {
                rightBorder.setFill(Color.BLACK);
            }
            if (row < ROWS - 1 && gridMap[row + 1][col] == '#') {
                bottomBorder.setFill(Color.BLACK);
            }
            if (col > 0 && gridMap[row][col - 1] == '#') {
                leftBorder.setFill(Color.BLACK);
            }
        }

        cellPane.getChildren().addAll(topBorder, rightBorder, bottomBorder, leftBorder);
    }

    private Rectangle createBorder(double width, double height, double x, double y) {
        Rectangle border = new Rectangle(width, height);
        border.setTranslateX(x);
        border.setTranslateY(y);
        border.setFill(Color.TRANSPARENT);
        return border;
    }
}
