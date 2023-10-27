package Code;

import Code.Ball.Ball;
import Code.Ball.Balls;
import Code.bricks.Bricks;
import Code.level.Level;

import java.util.Iterator;
import java.util.Map;

public class ColllisionChecker {
   private Paddle player;
    private Bricks bricks ;
    private Balls balls ;
    private  int brick_size;
    private Level game;
    public ColllisionChecker(Paddle player, Balls balls, Bricks bricks, Level game) {
        this.player = player;
        this.bricks=bricks;
        this.balls = balls;
        this.brick_size = bricks.getBrick_size();
        this.game = game;

    }



    public  void checkCollsion(){

        Iterator   <Map.Entry<Ball, Ball>>  it = balls.getBalls().entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Ball, Ball> entry =  it.next();
            Ball ball = entry.getValue();
            if(ball.collidedOnScreen()){
                //the ball fell
                if(  ball.isOutside()  && balls.size() == 1){
                    balls.resetBalls();
                    game.reduceBall();
                    return;
                }else if(ball.isOutside()) it.remove();

                continue;
            }
            //  player.updatePostionMouse((int)ball.getPredicted_X()-5);
            boolean playerHit = ball.isHit(player.getPaddle_X(), player.getPaddle_Y(),player.getPaddle_width(),player.getPaddle_height());
            if(playerHit)continue;

            String ball_coordinateKey = null;

            for (String key:ball.coordinatesKey(brick_size)
            ) {
                if(bricks.findBrick(key)){
                    ball_coordinateKey = key;
                    break;
                }
            }
            if(ball_coordinateKey == null) continue;
            var brick =  bricks.getBrick(ball_coordinateKey);
            ball.isHit(brick.getX(), brick.getY(),brick.getWidth(),brick.getHeight());
            brick.changeColor();
            brick.setToughness(brick.getToughness() - ball.getStrength());
            //add points
            game.addScore(1000);
            //remove the brick from bricks if the toughness is zero
            if (brick.getToughness() <=0){
                game.addScore(1000);
                bricks.removeBrick(ball_coordinateKey);
                game.reduceBricks();
            }




        }





    }



}

