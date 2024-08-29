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
import it.unicam.cs.khanshaz123384.app.utils.PlayerChangeListener;
import it.unicam.cs.khanshaz123384.app.view.TrackUI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private ITrackConfiguration trackConfiguration;
    private IRaceSimulator raceSimulator;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        ConfigurationLoader loader = new ConfigurationLoader();
        try {
            this.trackConfiguration = loader.loadConfiguration("src/main/resources/trackPlayerConfig.txt");
            IPositionValidator positionValidator = new PositionValidator(trackConfiguration);
            IPlayerManager playerManager = new PlayerManager(trackConfiguration);
            IPlayerActionHandler playerActionHandler = new PlayerActionHandler(playerManager, new PlayerChangeListener());
            IPositionChecker positionChecker = new PositionChecker(positionValidator, playerManager, trackConfiguration);
            IRaceResultPrinter raceResultPrinter = new RaceResultPrinter(playerManager);

            this.raceSimulator = new RaceSimulator(
                    playerActionHandler,
                    positionChecker,
                    raceResultPrinter,
                    playerManager
            );
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error during configuration loading: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        ITrackManager trackManager = new TrackManager(trackConfiguration.getGrid(), trackConfiguration.getPlayers(), raceSimulator);
        TrackUI trackUI = new TrackUI(trackManager);
        trackUI.start(primaryStage);
        new Thread(raceSimulator::raceSimulation).start();
    }
}
