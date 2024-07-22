package it.unicam.cs.khanshaz123384.app;

import it.unicam.cs.khanshaz123384.api.utils.FileIO;
import it.unicam.cs.khanshaz123384.api.model.RaceTrack;
import it.unicam.cs.khanshaz123384.api.model.iPlayer;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class to run the racetrack game.
 * This class reads the configuration file, interprets the contents to configure the track and players,
 * and then prints out details about the track and players.
 */
public class Main extends Application {

    private static char[][] gridMap;
    private static final List<String> playerDetails = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // Leggere la configurazione del tracciato e dei giocatori
            loadTrackConfiguration();

            // Avviare l'applicazione JavaFX
            launch(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadTrackConfiguration() throws IOException {
        FileIO fileIO = new FileIO();
        String filePath = "src/main/resources/trackPlayerConfig.txt";

        // Leggere il file di configurazione
        List<String> lines = fileIO.readFile(filePath);

        // Interpretare il file per ottenere la configurazione del tracciato
        FileIO.TrackConfiguration config = fileIO.interpretFile(lines);

        // Creare e caricare il tracciato con la configurazione
        RaceTrack raceTrack = new RaceTrack();
        raceTrack.loadTrack(config);

        // Caricare la griglia
        gridMap = raceTrack.getGrid();

        // Caricare i dettagli dei giocatori
        for (iPlayer player : raceTrack.getPlayers()) {
            playerDetails.add("Player: " + player.getPlayerName() + ", Type: " + player.getType());
        }

    }

    @Override
    public void start(Stage primaryStage) {
        // Passare il tracciato caricato e i dettagli dei giocatori alla classe di visualizzazione della griglia
        Track gridView = new Track(gridMap, playerDetails);
        gridView.start(primaryStage);
    }
}
