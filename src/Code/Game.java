package Code;

import Code.level.Level;

import javax.swing.*;

public class Game extends JFrame {
    private Level game = new Level();
    public Game(){
    add(new Board(game));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(game.getBOARD_WIDTH()+(game.getBrick_size()/2)+5, game.getBOARD_HEIGHT()+game.getBrick_size()+10);

        setLocationRelativeTo(null);

    }
}
