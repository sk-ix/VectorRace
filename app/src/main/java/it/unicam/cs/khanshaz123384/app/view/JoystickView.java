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

import it.unicam.cs.khanshaz123384.app.controller.JoystickEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class JoystickView extends VBox {

    private final JoystickEventHandler joystickEventHandler;

    public JoystickView(JoystickEventHandler joystickEventHandler) {
        this.joystickEventHandler = joystickEventHandler;
        setSpacing(10);
        setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 2px;");        setPadding(new Insets(10));
        setAlignment(Pos.CENTER);

        getChildren().addAll(
                createInstructionLabel(),
                createJoystickGrid()
        );
    }

    private Label createInstructionLabel() {
        Label instructionLabel = new Label("Joystick to control the player");
        instructionLabel.setTextAlignment(TextAlignment.CENTER);
        instructionLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        instructionLabel.setPadding(new Insets(0, 0, 10, 0));
        return instructionLabel;
    }

    private GridPane createJoystickGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);

        Button upLeftButton = createButton("↖", -1, -1);
        Button upButton = createButton("↑", 0, -1);
        Button upRightButton = createButton("↗", 1, -1);
        Button leftButton = createButton("←", -1, 0);
        Button centerButton = createButton("O", 0, 0);
        Button rightButton = createButton("→", 1, 0);
        Button downLeftButton = createButton("↙", -1, 1);
        Button downButton = createButton("↓", 0, 1);
        Button downRightButton = createButton("↘", 1, 1);

        grid.add(upLeftButton, 0, 0);
        grid.add(upButton, 1, 0);
        grid.add(upRightButton, 2, 0);
        grid.add(leftButton, 0, 1);
        grid.add(centerButton, 1, 1);
        grid.add(rightButton, 2, 1);
        grid.add(downLeftButton, 0, 2);
        grid.add(downButton, 1, 2);
        grid.add(downRightButton, 2, 2);

        return grid;
    }

    private Button createButton(String text, int deltaX, int deltaY) {
        Button button = new Button(text);
        button.setPrefSize(50, 50);
        button.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 1px; -fx-font-size: 20;");
        button.setTextAlignment(TextAlignment.CENTER);

        button.setOnAction(event -> joystickEventHandler.handleJoystickEvent(deltaX, deltaY));
        return button;
    }
}
