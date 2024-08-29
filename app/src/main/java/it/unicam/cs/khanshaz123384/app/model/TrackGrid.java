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

package it.unicam.cs.khanshaz123384.app.model;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;
import it.unicam.cs.khanshaz123384.app.utils.CellBorderDecorator;
import it.unicam.cs.khanshaz123384.app.utils.CellFactory;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public class TrackGrid extends GridPane implements ITrackGrid {

    private final char[][] gridMap;
    private final List<IPlayer> players;
    private final int ROWS = 38;
    private final int COLS = 60;
    private final CellFactory cellFactory;
    private final CellBorderDecorator borderDecorator;

    public TrackGrid(char[][] gridMap, List<IPlayer> players) {
        this.gridMap = gridMap;
        this.players = players;
        this.cellFactory = new CellFactory();
        this.borderDecorator = new CellBorderDecorator(ROWS, COLS);
        initializeGrid();

        // Listener per aggiornare le celle quando le dimensioni della griglia cambiano
        widthProperty().addListener((obs, oldWidth, newWidth) -> updateGrid());
        heightProperty().addListener((obs, oldHeight, newHeight) -> updateGrid());
    }

    private void initializeGrid() {
        setPadding(new Insets(0));
        setHgap(0);
        setVgap(0);

        createGridStructure();
    }

    private void createGridStructure() {
        for (int i = 0; i < COLS; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHalignment(HPos.CENTER);
            colConstraints.setFillWidth(true);
            getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < ROWS; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.CENTER);
            rowConstraints.setFillHeight(true);
            getRowConstraints().add(rowConstraints);
        }
    }

    public void updateGrid() {
        getChildren().clear();

        double cellWidth = getWidth() / COLS;
        double cellHeight = getHeight() / ROWS;
        double cellSize = Math.min(cellWidth, cellHeight);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Pane cellPane = cellFactory.createCellPane(gridMap[row][col], cellSize);
                borderDecorator.addBorders(cellPane, gridMap[row][col], gridMap, row, col, cellSize);
                GridPane.setConstraints(cellPane, col, row);
                add(cellPane, col, row);
            }
        }

        updatePlayerPositions();
    }

    public void updatePlayerPositions() {
        double cellSize = Math.min(getWidth() / COLS, getHeight() / ROWS);
        getChildren().removeIf(node -> node instanceof Circle);

        for (IPlayer player : players) {
            addPlayerToGrid(player, cellSize);
        }
    }

    private void addPlayerToGrid(IPlayer player, double cellSize) {

        int[] currentPosition = player.getPosition();

        double currentXPos = currentPosition[0] * cellSize;
        double currentYPos = currentPosition[1] * cellSize;

        Circle playerCircle = new Circle(cellSize / 3);
        playerCircle.setFill(Color.web(player.getColor()));

        playerCircle.setTranslateX(currentXPos - playerCircle.getRadius());
        playerCircle.setTranslateY(currentYPos - playerCircle.getRadius());

        getChildren().add(playerCircle);
    }
}

