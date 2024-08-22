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

import it.unicam.cs.khanshaz123384.app.controller.ITrackManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import javafx.scene.control.ScrollPane;

public class TrackUI extends Application {

    private final ITrackManager trackManager;

    public TrackUI(ITrackManager trackManager) {
        this.trackManager = trackManager;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Track Grid");

        BorderPane borderPane = new BorderPane();
        PlayerView playerView = new PlayerView(trackManager.getPlayers());
        JoystickView joystickView = new JoystickView(trackManager.getJoystickEventHandler());
        TrackGrid trackGrid = trackManager.getTrackGrid();

        ScrollPane scrollPane = new ScrollPane(trackGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        VBox leftPane = new VBox(playerView, joystickView);
        leftPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        VBox.setVgrow(playerView, Priority.ALWAYS);
        VBox.setVgrow(joystickView, Priority.ALWAYS);

        borderPane.setLeft(leftPane);
        borderPane.setCenter(scrollPane);

        Scene scene = new Scene(borderPane, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
