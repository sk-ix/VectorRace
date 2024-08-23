package it.unicam.cs.khanshaz123384.app;

import it.unicam.cs.khanshaz123384.api.model.Player;
import it.unicam.cs.khanshaz123384.api.model.RaceSimulator;
import it.unicam.cs.khanshaz123384.api.utils.IFileReaderService;
import it.unicam.cs.khanshaz123384.api.utils.TxtFileReaderService;
import it.unicam.cs.khanshaz123384.api.utils.TrackConfiguration;
import it.unicam.cs.khanshaz123384.api.utils.TrackConfigurationParser;
import it.unicam.cs.khanshaz123384.app.controller.ITrackManager;
import it.unicam.cs.khanshaz123384.app.controller.TrackManager;

import it.unicam.cs.khanshaz123384.app.view.TrackUI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    private static char[][] gridMap;
    private static List<Player> players;

    public static void main(String[] args) {
        // Percorso fisso del file di configurazione
        String filePath = "src/main/resources/trackPlayerConfig.txt";

        // Servizio di lettura file e parser
        IFileReaderService fileReaderService = new TxtFileReaderService();
        TrackConfigurationParser parser = new TrackConfigurationParser();

        try {
            // Leggi il file di configurazione
            List<String> lines = fileReaderService.readFile(filePath);
            TrackConfiguration trackConfiguration = parser.parse(lines);

            gridMap = trackConfiguration.getGrid();
            players = trackConfiguration.getPlayers();

            // Lancia l'applicazione JavaFX
            launch(args);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing configuration: " + e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Crea il simulatore di gara
        TrackConfiguration trackConfiguration = new TrackConfiguration(gridMap, players);
        RaceSimulator raceSimulator = new RaceSimulator(trackConfiguration);

        // Crea il TrackManager e passagli il RaceSimulator
        ITrackManager trackManager = new TrackManager(gridMap, players, raceSimulator);

        // Crea la vista della traccia con il TrackManager
        TrackUI trackUI = new TrackUI(trackManager);

        // Configura e mostra la finestra principale
        trackUI.start(primaryStage);

        // Avvia la simulazione della gara in un thread separato
        new Thread(raceSimulator::raceSimulation).start();
    }
}
