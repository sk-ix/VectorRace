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

public class PlayerView extends VBox {

    public PlayerView(List<IPlayer> players) {
        setPrefWidth(200);
        setSpacing(10);
        setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 2px;");
        getChildren().add(createTitleLabel());

        for (IPlayer player : players) {
            getChildren().add(createPlayerHBox(player));
        }
        setAlignment(Pos.CENTER);
    }

    private Label createTitleLabel() {
        Label titleLabel = new Label("Player List");
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        return titleLabel;
    }

    private HBox createPlayerHBox(IPlayer player) {
        Circle colorCircle = new Circle(8);
        colorCircle.setFill(Color.web(player.getColor()));

        Label playerLabel = new Label(player.getName());
        playerLabel.setStyle("-fx-font-size: 14;");

        HBox playerHBox = new HBox(5);
        playerHBox.setAlignment(Pos.CENTER);
        playerHBox.getChildren().addAll(colorCircle, playerLabel);

        return playerHBox;
    }
}
