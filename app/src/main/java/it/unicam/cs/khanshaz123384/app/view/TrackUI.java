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

package it.unicam.cs.khanshaz123384.app.view;

import it.unicam.cs.khanshaz123384.app.controller.Interfaces.ITrackManager;
import it.unicam.cs.khanshaz123384.app.model.ITrackGrid;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import javafx.scene.control.ScrollPane;

/**
 * User Interface class for displaying the track grid and player controls.
 *
 * <p>This class extends {@link Application} and sets up the main UI components
 * for the race simulation, including a scrollable view of the track grid, a player
 * view, and a joystick view for controlling the players.</p>
 */
public class TrackUI extends Application {

    private final ITrackManager trackManager;

    /**
     * Constructs a {@code TrackUI} with the specified track manager.
     *
     * @param trackManager The track manager to be used for managing the track grid and player data.
     * @throws IllegalArgumentException if {@code trackManager} is null.
     */
    public TrackUI(ITrackManager trackManager) {
        if (trackManager == null)
            throw new IllegalArgumentException("TrackManager cannot be null.");

        this.trackManager = trackManager;
    }

    /**
     * Starts the JavaFX application and sets up the user interface.
     *
     * <p>This method is called after the application is initialized. It sets up
     * the main layout of the application, including the track grid, player view,
     * and joystick controls.</p>
     *
     * @param primaryStage The primary stage for this application, onto which the
     *                     application scene will be set.
     */
    @Override
    public void start(Stage primaryStage) {
        // Set the title of the primary stage
        primaryStage.setTitle("Track Grid");

        // Create the main layout container
        BorderPane borderPane = new BorderPane();

        // Create views for players and joystick, and get the track grid
        PlayerView playerView = new PlayerView(trackManager.getPlayers());
        JoystickView joystickView = new JoystickView(trackManager.getJoystickEventHandler());
        ITrackGrid trackGrid = trackManager.getTrackGrid();

        // Create a scroll pane for the track grid
        ScrollPane scrollPane = new ScrollPane((Node) trackGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Create a left pane with player and joystick views
        VBox leftPane = new VBox(playerView, joystickView);
        leftPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        VBox.setVgrow(playerView, Priority.ALWAYS);
        VBox.setVgrow(joystickView, Priority.ALWAYS);

        // Add components to the main layout
        borderPane.setLeft(leftPane);
        borderPane.setCenter(scrollPane);

        // Create and set the scene for the primary stage
        Scene scene = new Scene(borderPane, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
