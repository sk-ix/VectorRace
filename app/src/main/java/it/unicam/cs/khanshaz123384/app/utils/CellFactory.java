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

public class CellFactory {

    public Pane createCellPane(char cellType, double cellSize) {
        Pane cellPane = new Pane();
        cellPane.setPrefSize(cellSize, cellSize);

        if (cellType == 'S') {
            // Create two triangles for the 'S' cell
            Polygon whiteTriangle = createTriangle(cellSize, Color.WHITE, true);
            Polygon blackTriangle = createTriangle(cellSize, Color.BLACK, false);

            cellPane.getChildren().addAll(whiteTriangle, blackTriangle);
        } else if (cellType == 'F') {
            // Create two triangles for the 'F' cell
            Polygon blackTriangle = createTriangle(cellSize, Color.BLACK, true);
            Polygon whiteTriangle = createTriangle(cellSize, Color.WHITE, false);

            cellPane.getChildren().addAll(blackTriangle, whiteTriangle);
        } else {
            // Create a single rectangle for other cell types
            Rectangle cellBackground = new Rectangle(cellSize, cellSize);
            cellBackground.setFill(Color.WHITE);
            cellPane.getChildren().add(cellBackground);
        }

        return cellPane;
    }

    private Polygon createTriangle(double size, Color color, boolean isUpperTriangle) {
        Polygon triangle = new Polygon();

        // Define the points for the triangle
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

        triangle.setFill(color);
        return triangle;
    }

}
