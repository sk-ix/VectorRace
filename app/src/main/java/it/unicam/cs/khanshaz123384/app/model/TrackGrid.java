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

/**
 * Represents the grid for the track in the simulation, extending {@link GridPane}
 * and implementing {@link ITrackGrid}.
 *
 * <p>This class is responsible for rendering the grid and updating the positions of players
 * based on the current state of the grid and player data. It handles grid layout, cell creation,
 * and border decoration.</p>
 */
public class TrackGrid extends GridPane implements ITrackGrid {

    private final char[][] gridMap;
    private final List<IPlayer> players;
    private static final int ROWS = 38;
    private static final int COLS = 60;
    private final CellFactory cellFactory;
    private final CellBorderDecorator borderDecorator;

    /**
     * Constructs a TrackGrid instance with the specified grid map and player list.
     *
     * @param gridMap 2D array representing the grid layout with cell types.
     * @param players List of players to be displayed on the grid.
     * @throws IllegalArgumentException if gridMap is null or has invalid dimensions.
     */
    public TrackGrid(char[][] gridMap, List<IPlayer> players) {
        if (gridMap == null)
            throw new IllegalArgumentException("Grid map cannot be null.");

        if (gridMap.length != ROWS || gridMap[0].length != COLS)
            throw new IllegalArgumentException("Grid map dimensions are incorrect.");

        if (players == null)
            throw new IllegalArgumentException("Players list cannot be null.");


        this.gridMap = gridMap;
        this.players = players;
        this.cellFactory = new CellFactory();
        this.borderDecorator = new CellBorderDecorator(ROWS, COLS);
        initializeGrid();

        // Listener to update the grid when its size changes
        widthProperty().addListener((obs, oldWidth, newWidth) -> updateGrid());
        heightProperty().addListener((obs, oldHeight, newHeight) -> updateGrid());
    }

    /**
     * Initializes the grid layout and constraints.
     *
     * <p>This method sets up the padding, horizontal and vertical gaps, and column
     * and row constraints for the GridPane.</p>
     */
    private void initializeGrid() {
        setPadding(new Insets(0));
        setHgap(0);
        setVgap(0);

        createGridStructure();
    }

    /**
     * Creates column and row constraints for the GridPane.
     *
     * <p>This method defines the number of columns and rows, and sets their alignment
     * and fill properties to ensure proper grid rendering.</p>
     */
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

    /**
     * Updates the grid by refreshing the cell panes and adding player positions.
     *
     * <p>This method clears existing children, recalculates cell sizes, creates new
     * cell panes, and applies border decorations. It also updates player positions.</p>
     */
    @Override
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

    /**
     * Updates the positions of players on the grid.
     *
     * <p>This method removes existing player indicators and adds new ones based on the
     * current player positions. It ensures that player circles are properly positioned
     * on the grid.</p>
     */
    @Override
    public void updatePlayerPositions() {
        double cellSize = Math.min(getWidth() / COLS, getHeight() / ROWS);
        getChildren().removeIf(node -> node instanceof Circle);

        for (IPlayer player : players) {
            addPlayerToGrid(player, cellSize);
        }
    }

    /**
     * Adds a player to the grid by creating and positioning a circle for the player.
     *
     * @param player The player to be added.
     * @param cellSize The size of each cell in the grid.
     * @throws IllegalArgumentException if player or cellSize is invalid.
     */
    private void addPlayerToGrid(IPlayer player, double cellSize) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null.");

        if (cellSize < 0)
            throw new IllegalArgumentException("Cell size must be positive.");


        int[] currentPosition = player.getPosition();
        if (currentPosition.length != 2)
            throw new IllegalArgumentException("Player position array must have exactly two elements.");


        double currentXPos = currentPosition[0] * cellSize;
        double currentYPos = currentPosition[1] * cellSize;

        Circle playerCircle = new Circle(cellSize / 3);
        playerCircle.setFill(Color.web(player.getColor()));

        playerCircle.setTranslateX(currentXPos - playerCircle.getRadius());
        playerCircle.setTranslateY(currentYPos - playerCircle.getRadius());

        getChildren().add(playerCircle);
    }
}