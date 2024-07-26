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

import it.unicam.cs.khanshaz123384.api.model.Player;
import it.unicam.cs.khanshaz123384.api.utils.IFileReaderService;
import it.unicam.cs.khanshaz123384.api.utils.TxtFileReaderService;
import it.unicam.cs.khanshaz123384.api.utils.TrackConfiguration;
import it.unicam.cs.khanshaz123384.api.utils.TrackConfigurationParser;
import it.unicam.cs.khanshaz123384.app.view.Track;
import it.unicam.cs.khanshaz123384.app.controllers.JoystickController;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    private static char[][] gridMap;
    private static List<Player> player = new ArrayList<>();

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
            player = trackConfiguration.getPlayers();

            launch(args);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error parsing configuration: " + e.getMessage());
        }
    }

    public void start(Stage primaryStage) {
        Track gridView = new Track(gridMap, player);
        // Crea e imposta il controller
        JoystickController controller = new JoystickController(gridView);
        gridView.setJoystickEventHandler(controller);

        gridView.start(primaryStage);

    }
}
