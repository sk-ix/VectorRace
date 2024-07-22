package it.unicam.cs.khanshaz123384.app;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class Track extends Application {

    private static final int ROWS = 33; // Numero di righe della griglia
    private static final int COLS = 61; // Numero di colonne della griglia

    private final char[][] gridMap;
    private final List<String> playerDetails;

    public Track(char[][] gridMap, List<String> playerDetails) {
        this.gridMap = gridMap;
        this.playerDetails = playerDetails;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Track Grid");

        // Creare un GridPane per la griglia del tracciato
        GridPane gridPane = new GridPane();

        // Creare una VBox per la classifica dei giocatori
        VBox playerListBox = new VBox(10);
        playerListBox.setStyle("-fx-padding: 10; -fx-background-color: lightblue;");

        // Aggiungere le etichette della classifica dei giocatori alla VBox
        Label titleLabel = new Label("Classifica Giocatori");
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        playerListBox.getChildren().add(titleLabel);

        // Aggiungere le informazioni dei giocatori alla VBox
        for (int i = 0; i < playerDetails.size(); i++) {
            String playerDetail = playerDetails.get(i);
            Label playerLabel = new Label((i + 1) + ". " + playerDetail);
            playerListBox.getChildren().add(playerLabel);
        }

        // Creare un HBox per contenere la VBox e il GridPane
        HBox root = new HBox(10);
        root.getChildren().addAll(playerListBox, gridPane);

        // Creare una scena con l'HBox
        Scene scene = new Scene(root, 1200, 600); // Dimensioni iniziali della finestra
        primaryStage.setScene(scene);

        // Aggiornare le dimensioni delle celle quando la finestra viene ridimensionata
        scene.widthProperty().addListener((obs, oldVal, newVal) -> updateGrid(gridPane, scene.getWidth(), scene.getHeight()));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> updateGrid(gridPane, scene.getWidth(), scene.getHeight()));

        // Impostare la dimensione iniziale della griglia
        updateGrid(gridPane, scene.getWidth(), scene.getHeight());

        primaryStage.show();
    }

    private void updateGrid(GridPane gridPane, double width, double height) {
        gridPane.getChildren().clear(); // Pulisce la griglia esistente

        double cellWidth = (width - 250) / COLS; // Ridurre la larghezza per far spazio alla VBox
        double cellHeight = height / ROWS;

        // Creare e aggiungere pulsanti alla griglia basati sulla mappa
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Button btn = new Button();
                btn.setPrefSize(cellWidth, cellHeight);

                char cell = gridMap[row][col];
                switch (cell) {
                    case '#':
                        btn.setStyle("-fx-background-color: red; -fx-border-color: black;");
                        break;
                    case '*':
                        btn.setStyle("-fx-background-color: white; -fx-border-color: black;");
                        break;
                    case 'S':
                        btn.setStyle("-fx-background-color: green; -fx-border-color: black;");
                        break;
                    case 'F':
                        btn.setStyle("-fx-background-color: black; -fx-border-color: black;");
                        break;
                }

                GridPane.setRowIndex(btn, row);
                GridPane.setColumnIndex(btn, col);
                gridPane.getChildren().add(btn);
            }
        }

        // Impostare le dimensioni delle righe e delle colonne
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < COLS; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints(cellWidth);
            colConstraints.setHalignment(HPos.CENTER); // Centrato orizzontalmente
            gridPane.getColumnConstraints().add(colConstraints);
        }
        for (int i = 0; i < ROWS; i++) {
            RowConstraints rowConstraints = new RowConstraints(cellHeight);
            rowConstraints.setValignment(VPos.CENTER); // Centrato verticalmente
            gridPane.getRowConstraints().add(rowConstraints);
        }

        // Aggiungere un bordo spesso attorno al GridPane
        gridPane.setBorder(new Border(new BorderStroke(
                Color.BLACK, // Colore del bordo
                BorderStrokeStyle.SOLID, // Stile del bordo
                null, // Arco del bordo (null indica angoli retti)
                new BorderWidths(10) // Larghezza del bordo
        )));
    }
}
