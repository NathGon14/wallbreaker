package Code.Ball;

import java.util.Collection;
import java.util.HashMap;

public class Balls {

    private boolean disabled = false;
    private HashMap<Ball, Ball> Balls = new HashMap<>();
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


    public Collection<Ball> getBalls() {
        return Balls.values();
    }



}
