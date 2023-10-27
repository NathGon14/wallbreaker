package Code.level;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameData {
    protected final int BRICK_SIZE = 30;
    protected final int DEFAULT_REMAINING_TIME = 120 ; //seconds
    protected final int DEFAULT_REMAINING_BALL = 2; //
    protected final int DELAY = 5;
    protected final int BALL_DIAMETER = 20;
    protected final int NUMBER_OF_BRICK_WIDTH = 12;
    protected  final int NUMBER_OF_BRICK_HEIGHT = 22;
    protected  final  int HEIGHT_OFFSET = 7;//how far the bricks from the top
    protected  final int NUMBER_OF_BRICK_ROW = 2;//how rows of bricks
    protected  int STARTING_LEVEL;
    protected  int maxLevel = 5;
    protected int BOARD_HEIGHT = BRICK_SIZE * NUMBER_OF_BRICK_HEIGHT;
    protected int BOARD_WIDTH = BRICK_SIZE * NUMBER_OF_BRICK_WIDTH;

    protected Image ballImage = new ImageIcon(   new File("src/Images/ball.png").getAbsolutePath()).getImage().getScaledInstance(BALL_DIAMETER, BALL_DIAMETER,Image.SCALE_DEFAULT);;
    protected Image background = new ImageIcon( new File("src/Images/background.png").getAbsolutePath()).getImage().getScaledInstance(BOARD_WIDTH,BOARD_HEIGHT,Image.SCALE_DEFAULT);;





    public int getDelay() {
        return DELAY;
    }


    public int getBrick_size() {
        return BRICK_SIZE;
    }

    public int getBOARD_HEIGHT() {
        return BOARD_HEIGHT;
    }

    public int getBOARD_WIDTH() {
        return BOARD_WIDTH;
    }

    public Image getBackground() {
        return background;
    }


}
