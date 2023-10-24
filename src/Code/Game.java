package Code;

import javax.swing.*;

public class Game extends JFrame {
    private GameLogic  game = new GameLogic();
    public Game(){
    add(new Board(game));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(game.getBOARD_WIDTH()+(game.brick_size/2)+5, game.getBOARD_HEIGHT()+game.brick_size+10);

        setLocationRelativeTo(null);

    }
}
