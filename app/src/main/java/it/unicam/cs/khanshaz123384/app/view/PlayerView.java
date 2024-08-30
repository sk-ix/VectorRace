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

import it.unicam.cs.khanshaz123384.api.model.Interfaces.IPlayer;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * A {@code VBox} that displays a list of players with their names and colors.
 *
 * <p>This class extends {@link VBox} to create a vertical layout that contains a list of
 * {@link IPlayer} objects. Each player is represented by a {@link Circle} indicating their
 * color and a {@link Label} displaying their name.</p>
 */
public class PlayerView extends VBox {

    /**
     * Constructs a {@code PlayerView} with the specified list of players.
     *
     * @param players The list of players to be displayed in this view.
     * @throws IllegalArgumentException if {@code players} is null.
     */
    public PlayerView(List<IPlayer> players) {
        if (players == null)
            throw new IllegalArgumentException("Players list cannot be null.");


        // Configure the VBox
        setPrefWidth(200);
        setSpacing(10);
        setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 2px;");
        getChildren().add(createTitleLabel());

        // Add player information to the VBox
        for (IPlayer player : players)
            getChildren().add(createPlayerHBox(player));

        setAlignment(Pos.CENTER);
    }

    /**
     * Creates a title label for the player list.
     *
     * @return A {@code Label} with the title "Player List".
     */
    private Label createTitleLabel() {
        Label titleLabel = new Label("Player List");
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        return titleLabel;
    }

    /**
     * Creates an {@code HBox} containing the visual representation of a player.
     *
     * <p>The {@code HBox} includes a {@code Circle} for the player's color and a {@code Label}
     * for the player's name.</p>
     *
     * @param player The player to be displayed in the {@code HBox}.
     * @return An {@code HBox} containing the player's color and name.
     * @throws IllegalArgumentException if {@code player} is null.
     */
    private HBox createPlayerHBox(IPlayer player) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null.");

        // Create a circle to display the player's color
        Circle colorCircle = new Circle(8);
        colorCircle.setFill(Color.web(player.getColor()));

        // Create a label to display the player's name
        Label playerLabel = new Label(player.getName());
        playerLabel.setStyle("-fx-font-size: 14;");

        // Create an HBox to hold the color circle and player label
        HBox playerHBox = new HBox(5);
        playerHBox.setAlignment(Pos.CENTER);
        playerHBox.getChildren().addAll(colorCircle, playerLabel);

        return playerHBox;
    }
}