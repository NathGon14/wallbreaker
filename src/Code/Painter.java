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
    public  void  draw(Graphics g2d){
        drawBackGround(g2d);
        drawScreenData(g2d);
        paintBricks(g2d);
        drawPlayer( g2d);
        drawBall(g2d);

    }
    public  void drawScreenData(Graphics g2d){
           game.drawScreenData(g2d);

    }
    public  void drawIntermission(Graphics g2d,String title){
        game.drawIntermision(g2d,title);
    }
    public void paintBricks(Graphics g2d){
        bricks.drawBricks(g2d);
    }
    public void drawBackGround(Graphics g2d){
       g2d.drawImage(game.getBackground(),0,0,null);
    }

    public  void  drawPlayer(Graphics g2d){

        player.drawPaddle(g2d);

    }


    public  void  drawBall(Graphics g2d){
        for (Ball ball:balls.getBalls()
        ) {
           ball.drawBall(g2d);
        }

    }


}
