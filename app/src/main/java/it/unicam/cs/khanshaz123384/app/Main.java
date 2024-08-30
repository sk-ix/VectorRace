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

package it.unicam.cs.khanshaz123384.app;

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPositionValidator;
import it.unicam.cs.khanshaz123384.api.model.Interfaces.ITrackConfiguration;
import it.unicam.cs.khanshaz123384.app.controller.*;
import it.unicam.cs.khanshaz123384.api.model.PositionValidator;
import it.unicam.cs.khanshaz123384.app.controller.Interfaces.*;
import it.unicam.cs.khanshaz123384.app.utils.ConfigurationLoader;

import it.unicam.cs.khanshaz123384.app.utils.IPlayerChangeListener;
import it.unicam.cs.khanshaz123384.app.utils.PlayerChangeListener;
import it.unicam.cs.khanshaz123384.app.view.RaceResultPrinter;
import it.unicam.cs.khanshaz123384.app.view.TrackUI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Main application class for starting the race simulation.
 *
 * <p>This class extends {@link Application} and sets up the necessary components
 * for the race simulation, including loading the track configuration, initializing
 * the race simulator, and starting the user interface.</p>
 */
public class Main extends Application {

    private ITrackConfiguration trackConfiguration;
    private IRaceSimulator raceSimulator;

    /**
     * Entry point of the application. Launches the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the application by loading the track configuration and setting up
     * the race simulator and related components.
     *
     * <p>This method is called before the {@link #start(Stage)} method. It handles
     * configuration loading, initialization of components, and exception handling.</p>
     *
     * @throws Exception if an error occurs during initialization, including IO errors
     *         or invalid configuration parameters.
     */
    @Override
    public void init() throws Exception {
        super.init();
        ConfigurationLoader loader = new ConfigurationLoader();
        try {
            // Load track configuration from file
            this.trackConfiguration = loader.loadConfiguration("src/main/resources/trackPlayerConfig.txt");

            // Initialize necessary components
            IPositionValidator positionValidator = new PositionValidator(trackConfiguration);
            IPlayerManager playerManager = new PlayerManager(trackConfiguration);
            IPlayerActionHandler playerActionHandler = new PlayerActionHandler(playerManager);
            IPositionChecker positionChecker = new PositionChecker(positionValidator, playerManager);
            IRaceResultPrinter raceResultPrinter = new RaceResultPrinter(playerManager);

            // Initialize the race simulator with the components
            this.raceSimulator = new RaceSimulator(
                    playerActionHandler,
                    positionChecker,
                    raceResultPrinter,
                    playerManager
            );
        } catch (IOException | IllegalArgumentException e) {
            // Handle errors during configuration loading
            System.err.println("Error during configuration loading: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the JavaFX application and sets up the user interface.
     *
     * <p>This method is called after the {@link #init()} method. It initializes the
     * track manager, sets up the track UI, and begins the race simulation in a new thread.</p>
     *
     * <p>A {@link PlayerChangeListener} is also created and set to listen for changes in player status.</p>
     *
     * @param primaryStage The primary stage for this application, onto which the
     *                     application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create a track manager with the configuration and the race simulator
        ITrackManager trackManager = new TrackManager(trackConfiguration.getGrid(), trackConfiguration.getPlayers(), raceSimulator);

        // Create and set a player change listener to the race simulator
        IPlayerChangeListener playerChangeListener = new PlayerChangeListener(trackManager);
        raceSimulator.setPlayerChangeListener(playerChangeListener);

        // Initialize and start the track UI
        TrackUI trackUI = new TrackUI(trackManager);
        trackUI.start(primaryStage);

        // Start the race simulation in a separate thread
        new Thread(raceSimulator::raceSimulation).start();
    }
}