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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * A factory class for creating visual representations of grid cells.
 *
 * <p>This class provides methods to create {@link Pane} objects representing different types of cells
 * based on the character type of the cell. It supports cells represented by 'S', 'F', and other types.</p>
 */
public class CellFactory {

    /**
     * Creates a {@link Pane} representing a grid cell based on the cell type.
     *
     * <p>Different cell types are represented as follows:</p>
     * <ul>
     *     <li>'S': A cell with two triangles (white and black).</li>
     *     <li>'F': A cell with two triangles (black and white).</li>
     *     <li>Other types: A single rectangle filled with white color.</li>
     * </ul>
     *
     * @param cellType The character representing the cell type ('S', 'F', or other).
     * @param cellSize The size of the cell (width and height).
     * @return A {@link Pane} representing the cell.
     * @throws IllegalArgumentException If the cellType is not 'S', 'F', or another valid type.
     */
    public Pane createCellPane(char cellType, double cellSize) {
        if (cellSize < 0)
            throw new IllegalArgumentException("Cell size must be positive.");


        Pane cellPane = new Pane();
        cellPane.setPrefSize(cellSize, cellSize);

        switch (cellType) {
            case 'S':
                // Create two triangles for the 'S' cell
                Polygon whiteTriangleS = createTriangle(cellSize, Color.WHITE, true);
                Polygon blackTriangleS = createTriangle(cellSize, Color.BLACK, false);
                cellPane.getChildren().addAll(whiteTriangleS, blackTriangleS);
                break;
            case 'F':
                // Create two triangles for the 'F' cell
                Polygon blackTriangleF = createTriangle(cellSize, Color.BLACK, true);
                Polygon whiteTriangleF = createTriangle(cellSize, Color.WHITE, false);
                cellPane.getChildren().addAll(blackTriangleF, whiteTriangleF);
                break;
            default:
                // Create a single rectangle for other cell types
                Rectangle cellBackground = new Rectangle(cellSize, cellSize);
                cellBackground.setFill(Color.WHITE);
                cellPane.getChildren().add(cellBackground);
                break;
        }

        return cellPane;
    }


    /**
     * Creates a triangle shape for the cell.
     *
     * <p>The triangle can be either the upper or lower half of the cell, depending on the {@code isUpperTriangle} flag.</p>
     *
     * @param size The size of the triangle (width and height). Must be positive.
     * @param color The color of the triangle. Cannot be {@code null}.
     * @param isUpperTriangle {@code true} if the triangle is the upper half; {@code false} for the lower half.
     * @return A {@link Polygon} representing the triangle.
     * @throws IllegalArgumentException If the size is less than or equal to zero.
     * @throws NullPointerException If the color is {@code null}.
     */
    private Polygon createTriangle(double size, Color color, boolean isUpperTriangle) {
        // Validate size
        if (size < 0) {
            throw new IllegalArgumentException("Size must be positive.");
        }

        // Validate color
        if (color == null) {
            throw new NullPointerException("Color cannot be null.");
        }

        Polygon triangle = new Polygon();

        // Define the points for the triangle based on the flag
        if (isUpperTriangle) {
            triangle.getPoints().addAll(
                    0.0, 0.0,        // Top-left corner
                    size, 0.0,       // Top-right corner
                    0.0, size        // Bottom-left corner
            );
        } else {
            triangle.getPoints().addAll(
                    size, size,      // Bottom-right corner
                    size, 0.0,       // Top-right corner
                    0.0, size        // Bottom-left corner
            );
        }

        // Set the fill color for the triangle
        triangle.setFill(color);
        return triangle;
    }
}
