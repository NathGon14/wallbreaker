package Code.Ball;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Balls {

    private HashMap<Ball, Ball> Balls;
    private int board_width;
    private int board_height;
    public Balls (int board_width,int board_height){
        this.board_height= board_height;
        this.board_width=board_width;

    }
        public void createBall(int diameter){
            Ball ball = new Ball(board_width,board_height,diameter);
            Balls.putIfAbsent(ball,ball);
        }

    public void resetBalls(){
        for (Ball ball:Balls.values()
             ) {
            ball.reset();
        }
    }
    public  int size(){
        return Balls.size();
    }
    public void removeBall(Ball ball){
        Balls.remove(ball);
    }
    public void destroy(){
        Balls =  new HashMap<>();
    }
    public void drawBalls(Graphics g2d){
        for (Ball ball:Balls.values()
        ) {
            ball.drawBall(g2d);
        }

    }
    public HashMap<Ball, Ball> getBalls() {
        return Balls;

    }

}
