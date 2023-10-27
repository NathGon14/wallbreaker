package Code;

import Code.Ball.Ball;
import Code.Ball.Balls;
import Code.bricks.Bricks;
import Code.level.Level;

import java.awt.*;


public class Painter {

    private  int BOARD_WIDTH;
    private  int BOARD_HEIGHT;
    Paddle player;
    Bricks bricks ;
    Balls balls ;
    Level game;

    public Painter(Paddle player,Balls balls, Bricks bricks, Level game) {
        this.player = player;
        BOARD_WIDTH = player.getSCREEN_WIDTH();
        BOARD_HEIGHT = player.getSCREEN_HEIGHT();
        this.bricks=bricks;
        this.balls = balls;
        this.game = game;
    }
    public  void  drawInGame(Graphics g2d){
        drawNessesary(g2d);
    }
    public  void  drawModal(Graphics g2d,String message){
        drawNessesary(g2d);
        game.drawIntermision(g2d,message);
    }
    private  void  drawNessesary(Graphics g2d){
        drawBackGround(g2d);
        bricks.drawBricks(g2d);
        game.drawScreenData(g2d);
        balls.drawBalls(g2d);
        player.drawPaddle(g2d);
    }


    public void drawBackGround(Graphics g2d){
       g2d.drawImage(game.getBackground(),0,0,null);
    }




}
