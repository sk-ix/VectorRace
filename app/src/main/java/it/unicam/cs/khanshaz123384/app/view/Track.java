package it.unicam.cs.khanshaz123384.app.view;

import it.unicam.cs.khanshaz123384.app.controllers.JoystickEventHandler;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import it.unicam.cs.khanshaz123384.api.model.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Track extends Application {

    private static final int ROWS = 32;
    private static final int COLS = 60;

    private final char[][] gridMap;
    private final List<Player> players;
    private JoystickEventHandler joystickEventHandler; // Campo per gestire gli eventi del joystick
    private Stage primaryStage; // Campo per memorizzare il Stage

    public Track(char[][] gridMap, List<Player> players) {
        this.gridMap = gridMap;
        this.players = players;
    }

    public void setJoystickEventHandler(JoystickEventHandler handler) {
        this.joystickEventHandler = handler;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Memorizza il riferimento al Stage
        primaryStage.setTitle("Track Grid");

        BorderPane borderPane = new BorderPane();
        VBox leftPane = new VBox();
        leftPane.setPrefWidth(200);

        VBox topRegion = new VBox(10);
        topRegion.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 2px;");
        topRegion.setPadding(new Insets(10));

        Label titleLabel = new Label("Classifica Giocatori");
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        topRegion.getChildren().add(titleLabel);

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Label playerLabel = new Label((i + 1) + ". " + player.getPlayerName() + " (" + player.getColor() + ")");
            topRegion.getChildren().add(playerLabel);
        }

        GridPane joystickGrid = new GridPane();
        joystickGrid.setHgap(5);
        joystickGrid.setVgap(5);
        joystickGrid.setPadding(new Insets(10));

        Button topLeftButton = createJoystickButton("↖", -1, -1);
        Button leftButton = createJoystickButton("←", -1, 0);
        Button bottomLeftButton = createJoystickButton("↙", -1, 1);
        Button upButton = createJoystickButton("↑", 0, -1);
        Button centerButton = createJoystickButton("O", 0, 0);
        Button downButton = createJoystickButton("↓", 0, 1);
        Button topRightButton = createJoystickButton("↗", 1, -1);
        Button rightButton = createJoystickButton("→", 1, 0);
        Button bottomRightButton = createJoystickButton("↘", 1, 1);

        joystickGrid.add(topLeftButton, 0, 0);
        joystickGrid.add(upButton, 1, 0);
        joystickGrid.add(topRightButton, 2, 0);
        joystickGrid.add(leftButton, 0, 1);
        joystickGrid.add(centerButton, 1, 1);
        joystickGrid.add(rightButton, 2, 1);
        joystickGrid.add(bottomLeftButton, 0, 2);
        joystickGrid.add(downButton, 1, 2);
        joystickGrid.add(bottomRightButton, 2, 2);

        ScrollPane bottomRegion = new ScrollPane(joystickGrid);
        bottomRegion.setStyle("-fx-background-color: lightgreen; -fx-border-color: black; -fx-border-width: 2px;");
        bottomRegion.setFitToWidth(true);

        topRegion.setPrefHeight(300);
        bottomRegion.setPrefHeight(300);

        VBox.setVgrow(topRegion, Priority.ALWAYS);
        VBox.setVgrow(bottomRegion, Priority.ALWAYS);

        leftPane.getChildren().addAll(topRegion, bottomRegion);
        leftPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        borderPane.setLeft(leftPane);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0));
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        for (int i = 0; i < COLS; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHalignment(HPos.CENTER);
            colConstraints.setFillWidth(true);
            gridPane.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0; i < ROWS; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setValignment(VPos.CENTER);
            rowConstraints.setFillHeight(true);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Button cellButton = new Button();
                cellButton.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px;");
                GridPane.setHgrow(cellButton, Priority.ALWAYS);
                GridPane.setVgrow(cellButton, Priority.ALWAYS);
                gridPane.add(cellButton, col, row);
            }
        }

        borderPane.setCenter(gridPane);

        Scene scene = new Scene(borderPane, 1200, 600);
        primaryStage.setScene(scene);

        scene.widthProperty().addListener((obs, oldVal, newVal) -> updateGrid(gridPane, scene.getWidth(), scene.getHeight()));
        scene.heightProperty().addListener((obs, oldVal, newVal) -> updateGrid(gridPane, scene.getWidth(), scene.getHeight()));

        updateGrid(gridPane, scene.getWidth(), scene.getHeight());

        primaryStage.show();
    }

    private Button createJoystickButton(String label, int value1, int value2) {
        Button button = new Button(label);
        button.setMinSize(50, 50);
        button.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; -fx-border-width: 1px; -fx-font-size: 20;");
        button.setTextAlignment(TextAlignment.CENTER);

        // Aggiungi un'azione al pulsante
        button.setOnAction(event -> {
            // Stampa i valori per il debug
            System.out.println("Button clicked: " + label + " with values (" + value1 + ", " + value2 + ")");

            // Passa i valori al gestore degli eventi, se presente
            if (joystickEventHandler != null && !players.isEmpty()) {
                Player player = players.get(0); // Supponiamo che il primo giocatore sia quello che ha premuto il pulsante
                joystickEventHandler.handleJoystickEvent(player, value1, value2);
            } else {
                System.err.println("JoystickEventHandler not set or no players available."); // Debug
            }
        });

        return button;
    }

    private void updateGrid(GridPane gridPane, double width, double height) {
        gridPane.getChildren().clear();

        double cellWidth = (width - 250) / COLS;
        double cellHeight = height / ROWS;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Button cellButton = new Button();
                cellButton.setMinSize(cellWidth, cellHeight);
                cellButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                char cellValue = gridMap[row][col];
                String cellColor = switch (cellValue) {
                    case '*' -> "white";
                    case '#' -> "grey";
                    case 'F' -> "red";
                    case 'S' -> "green";
                    default -> "white";
                };
                cellButton.setStyle("-fx-background-color: " + cellColor + "; -fx-border-color: black; -fx-border-width: 1px;");

                gridPane.add(cellButton, col, row);
            }
        }

        for (Player player : players) {
            Circle playerCircle = new Circle(Math.min(cellWidth, cellHeight) / 2);
            playerCircle.setFill(Color.web(player.getColor()));
            // Verifica se il giocatore è già stato aggiunto
            boolean playerAdded = false;
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getColumnIndex(node) == player.getPositionX() &&
                        GridPane.getRowIndex(node) == player.getPositionY() &&
                        node instanceof Circle) {
                    playerAdded = true;
                    break;
                }
            }
            if (!playerAdded) {
                gridPane.add(playerCircle, player.getPositionX(), player.getPositionY());
            }
        }
    }

    public void updatePlayerPosition() {
        if (primaryStage != null) {
            GridPane gridPane = (GridPane) ((BorderPane) primaryStage.getScene().getRoot()).getCenter();
            updateGrid(gridPane, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());
        }
    }

}

