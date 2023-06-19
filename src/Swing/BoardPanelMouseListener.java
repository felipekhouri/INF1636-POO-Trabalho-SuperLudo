package Swing;
import Model.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Set;

import Controller.Controller;

import java.awt.Color;

// implementar o clique no tile ao invés do pawn
public class BoardPanelMouseListener extends MouseAdapter {
    private Controller controller;

    public BoardPanelMouseListener(Controller controller2) {
        this.controller = controller2;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Set<Rectangle2D.Double> tiles = controller.getTiles();
        int dice = controller.getDice();
        Color player = controller.getPlayer();
        Facade model = controller.getModel();

        if (tiles != null) {
            for (Rectangle2D.Double tile : tiles) {
                if (tile.contains(e.getX(), e.getY())) {
                    controller.play(e.getX(), e.getY());
                }
            }
        }
    }
}
