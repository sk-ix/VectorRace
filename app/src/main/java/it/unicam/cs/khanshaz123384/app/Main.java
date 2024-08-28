package it.unicam.cs.khanshaz123384.app;

import it.unicam.cs.khanshaz123384.api.model.PositionValidator;
import it.unicam.cs.khanshaz123384.api.model.RaceSimulator;
import it.unicam.cs.khanshaz123384.api.model.TrackConfiguration;
import it.unicam.cs.khanshaz123384.app.controller.ITrackManager;
import it.unicam.cs.khanshaz123384.app.controller.TrackManager;
import it.unicam.cs.khanshaz123384.app.view.TrackUI;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private TrackConfiguration trackConfiguration;
    private RaceSimulator raceSimulator;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        ConfigurationLoader loader = new ConfigurationLoader();
        try {
            this.trackConfiguration = loader.loadConfiguration("src/main/resources/trackPlayerConfig.txt");
            PositionValidator positionValidator = new PositionValidator(trackConfiguration);
            this.raceSimulator = new RaceSimulator(trackConfiguration, positionValidator);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error during configuration loading: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        ITrackManager trackManager = new TrackManager(trackConfiguration.grid(), trackConfiguration.players(), raceSimulator);
        TrackUI trackUI = new TrackUI(trackManager);
        trackUI.start(primaryStage);
        new Thread(raceSimulator::raceSimulation).start();
    }
}
