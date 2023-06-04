package Swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Set;
import java.awt.Color;

// implementar o clique no tile ao invés do pawn
public class BoardPanelMouseListener extends MouseAdapter {
    private Controller controller;

    public BoardPanelMouseListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Set<Rectangle2D.Double> tiles = controller.getTiles();
        int dice = controller.getDice();
        Color player = controller.getPlayer();
        Model model = controller.getModel();

        if (tiles != null) {
            for (Rectangle2D.Double tile : tiles) {
                if (tile.contains(e.getX(), e.getY())) {
                    if (controller.isOnPlay()) {
                        System.out.println("is PLAY");
                        // se o tile tiver piao da cor do jogador então significa
                        if (model.checkTile(e.getX(), e.getY(), dice, player)) {
                            controller.setOnPlay(false);
                        }
                    }
                }
            }
        }
    }
}
