package it.unicam.cs.khanshaz123384.app.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import it.unicam.cs.khanshaz123384.app.view.Track;
import it.unicam.cs.khanshaz123384.api.model.Player;

public class JoystickController implements JoystickEventHandler {
    private final Track track;

    public JoystickController(Track track) {
        this.track = track;
    }
    @Override
    public void handleJoystickEvent(Player player, int deltaX, int deltaY) {
        System.out.println("Player: " + player + " with values (" + deltaX + ", " + deltaY + ")");
        player.updatePosition(deltaX,deltaY);
        track.updatePlayerPosition();
    }
}
