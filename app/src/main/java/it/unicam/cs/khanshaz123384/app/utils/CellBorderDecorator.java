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


/**
 * A class for decorating cells with borders based on their surrounding cells.
 *
 * <p>This class is responsible for adding borders to a cell based on its type and the types of
 * adjacent cells in the grid. Borders are colored differently based on whether adjacent cells are walls.</p>
 */
public class CellBorderDecorator {

    private final int ROWS;
    private final int COLS;

    /**
     * Constructs a {@link CellBorderDecorator} with specified grid dimensions.
     *
     * @param rows The number of rows in the grid.
     * @param cols The number of columns in the grid.
     * @throws IllegalArgumentException If rows or cols are non-positive.
     */
    public CellBorderDecorator(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows and columns must be positive integers.");
        }
        this.ROWS = rows;
        this.COLS = cols;
    }

    /**
     * Adds borders to the specified cell based on its type and surrounding cells.
     *
     * <p>Cells that are adjacent to walls ('#') will have their borders colored black. All other
     * borders will be colored cyan.</p>
     *
     * @param cellPane The {@link Pane} representing the cell to which borders will be added.
     * @param cellType The type of the cell (e.g., 'S', 'F', '#').
     * @param gridMap The 2D grid array representing the entire grid.
     * @param row The row index of the cell in the grid.
     * @param col The column index of the cell in the grid.
     * @param cellSize The size of each cell.
     * @throws IllegalArgumentException If cellPane is null or cellSize is non-positive.
     */
    public void addBorders(Pane cellPane, char cellType, char[][] gridMap, int row, int col, double cellSize) {
        if (cellPane == null)
            throw new IllegalArgumentException("CellPane cannot be null.");

        if (cellSize < 0)
            throw new IllegalArgumentException("Cell size must be positive.");

        if (gridMap == null)
            throw new IllegalArgumentException("GridMap cannot be null.");

        if (row < 0 || row >= ROWS || col < 0 || col >= COLS)
            throw new IllegalArgumentException("Row or column index is out of bounds.");


        Rectangle topBorder = createBorder(cellSize, 1, 0, 0);
        Rectangle rightBorder = createBorder(1, cellSize, cellSize - 1, 0);
        Rectangle bottomBorder = createBorder(cellSize, 1, 0, cellSize - 1);
        Rectangle leftBorder = createBorder(1, cellSize, 0, 0);

        // Set default border color to cyan
        topBorder.setFill(Color.CYAN);
        rightBorder.setFill(Color.CYAN);
        bottomBorder.setFill(Color.CYAN);
        leftBorder.setFill(Color.CYAN);

        // Adjust border color based on surrounding cells
        if (cellType != '#') {
            if (row > 0 && gridMap[row - 1][col] == '#')
                topBorder.setFill(Color.BLACK);

            if (col < COLS - 1 && gridMap[row][col + 1] == '#')
                rightBorder.setFill(Color.BLACK);

            if (row < ROWS - 1 && gridMap[row + 1][col] == '#')
                bottomBorder.setFill(Color.BLACK);

            if (col > 0 && gridMap[row][col - 1] == '#')
                leftBorder.setFill(Color.BLACK);
        }

        // Add borders to the cell pane
        cellPane.getChildren().addAll(topBorder, rightBorder, bottomBorder, leftBorder);
    }

    /**
     * Creates a border rectangle with specified dimensions and position.
     *
     * @param width The width of the border.
     * @param height The height of the border.
     * @param x The x position of the border.
     * @param y The y position of the border.
     * @return A {@link Rectangle} representing the border.
     * @throws IllegalArgumentException If width or height is non-positive.
     */
    private Rectangle createBorder(double width, double height, double x, double y) {

        if (width < 0 || height < 0)
            throw new IllegalArgumentException("Width and height must be positive.");

        Rectangle border = new Rectangle(width, height);
        border.setTranslateX(x);
        border.setTranslateY(y);
        border.setFill(Color.TRANSPARENT);
        return border;
    }
}
