package Code;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.*;

public class Ball {
    ImageIcon icon = new ImageIcon("src/Images/ball.png");
    Image scaleImage;
    private double speed = 6;
    private int strength = 1;
    private int BOARD_HEIGHT;
    private int BOARD_WIDTH;
    private int diameter = 25;

    private double ball_X;
    private double ball_Y;
    private  double ball_direction_X,ball_direction_Y;
    private double predicted_X, predicted_Y;
    private boolean disabled = false;
    private boolean outside = false;
    public Ball(int BOARD_WIDTH, int BOARD_HEIGHT,int diameter,int strength) {
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.BOARD_HEIGHT = BOARD_HEIGHT;
        this.diameter = diameter;

        reset();

    }
    public Ball(int BOARD_WIDTH, int BOARD_HEIGHT,int diameter) {
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.BOARD_HEIGHT = BOARD_HEIGHT;
        this.diameter = diameter;
        reset();

    }
    public Ball(int BOARD_WIDTH, int BOARD_HEIGHT,double direction,int diameter) {
        this.BOARD_WIDTH = BOARD_WIDTH;
        this.BOARD_HEIGHT = BOARD_HEIGHT;
        this.diameter = diameter;
        reset();

    }
    public void reset(){
         scaleImage  = icon.getImage().getScaledInstance(this.diameter, this.diameter,Image.SCALE_DEFAULT);
        setOutside(false);
        setBallPosition( BOARD_WIDTH/2  , BOARD_HEIGHT - (diameter*6) );
        setDirection("top-right");

    }
    public void setDirection(String direction) {

        switch (direction){
            case"down-right":
                this.ball_direction_X = 1;
                this.ball_direction_Y = 1;
                break;
            case "down-left":
                this.ball_direction_X = -1;
                this.ball_direction_Y = 1;
                break;
            case "top-left":
                this.ball_direction_X = -1;
                this.ball_direction_Y = -1;
                break;
            case "top-right":
                this.ball_direction_X = 1;
                this.ball_direction_Y = -1;
                break;

        }



    }

//    public void drawPredicted(Graphics g2d) {
//             predictMove();
//            g2d.setColor(Color.red);
//
//            g2d.fillOval((int) predicted_X, (int) predicted_Y, (diameter), (diameter));
//            return;
//
//    }
        public void drawBall(Graphics g2d) {
     g2d.drawImage(scaleImage,(int)ball_X, (int) ball_Y,null);
    }

    public void  moveBall(){
        if (stopdrawing) return;
        ball_X =  (ball_X + ball_direction_X * speed);
        ball_Y =  (ball_Y + ball_direction_Y * speed);
    }
    public void predictMove(){
        predicted_X =  (ball_X + ball_direction_X * (speed));
        predicted_Y =  (ball_Y + ball_direction_Y * (speed));
    }
    public boolean collidedOnScreen() {
        predictMove();

        boolean collided = true;

        // ball hit the right side of the board
        if (predicted_X + (diameter) >= BOARD_WIDTH) {
            ball_direction_X = -ball_direction_X;
            ball_X = BOARD_WIDTH - (diameter);
            ball_Y = predicted_Y;
        }else if (predicted_X <= 0) {
            ball_direction_X = -ball_direction_X;
            ball_X = 0;
            ball_Y = predicted_Y;
        } else if (predicted_Y <= 0  ) {    // ball hit the top

            ball_direction_Y = -ball_direction_Y;

            ball_Y = 0;
            ball_X= predicted_X;
        } else  if (predicted_Y >= BOARD_HEIGHT ) {    // ball falls down at the bottom
            ball_direction_Y = -ball_direction_Y;
            ball_X = predicted_X;
            ball_Y = BOARD_HEIGHT - (diameter);
            setOutside(true);
        }else  {
            collided =false;
            moveBall();
        }
         return  collided;
    }



    public boolean isHit(int x_position, int y_position , int width, int height) {
        predictMove();
        int collided_height= height;
        var collided_width= width;

        var collided_left= x_position;
        var collided_top=y_position;
        var collided_right= collided_left +collided_width;
        var collided_bottom= collided_top +collided_height;
        boolean hit =false;

        String collided_side = "none";

        //pythogre theory
        double radius = diameter/2;
        double ball_middle_point_x =  radius + predicted_X ; // a
        double ball_middle_point_y =  radius + predicted_Y ; // b
        double closestX = ball_middle_point_x;
        double closestY = ball_middle_point_y;
     //check which point of the rectangle x and y of the edge of the rectangle is the closest to the point of the circle
        if (ball_middle_point_x < collided_left) {
            closestX = collided_left;      // test left edge
        }
        else if (ball_middle_point_x >collided_right){
            closestX =collided_right;  // right edge
        }

        if (ball_middle_point_y < collided_top)     {
            closestY = collided_top;      // top edge
        }
        else if (ball_middle_point_y >collided_bottom) {
            closestY =collided_bottom;// bottom edge
        }
        //pytheogorem theory
        double a = ball_middle_point_x-closestX;
        double b = ball_middle_point_y-closestY;
        double distance = sqrt( (a*a) + (b*b) );


     //collision checking
        if (distance <=radius) {
            hit = true;
             //check if the ball is inside the box
            // remove it from inside send it to the neareast area without the bricks
                if(isInsideCollsion(collided_top,collided_left,collided_right,collided_bottom)){
                    double collision_outside_x = ball_X - diameter * toNearestOne(ball_direction_X);
                    double collision_outside_y = ball_Y - diameter * toNearestOne(ball_direction_Y);
                    setBallPosition(collision_outside_x, collision_outside_y);
                    isHit(collided_left,collided_top , collided_width, collided_height);
                }

            //collision
            //check collision base on their direction
            if(ball_direction_Y >0  && ball_direction_X>0) {// top going to right
                collided_side =  predicted_Y <= collided_top? "top":"left";

            }
            else  if (ball_direction_Y <0  && ball_direction_X>0){ //bottom to right
                collided_side =  predicted_Y+diameter >= collided_bottom? "bottom":"left";

            }     else  if (ball_direction_Y >0  && ball_direction_X<0){ //top  to left
                collided_side =  predicted_Y <= collided_top? "top":"right";

            }else if (ball_direction_Y <0  && ball_direction_X<0) { //bottom  to left
                collided_side =  predicted_Y+diameter >= collided_bottom? "bottom":"right";

            }

            // apply ball physics
            //bounce the ball
            ballPhysics(collided_side ,ball_middle_point_x,ball_middle_point_y,collided_left,collided_right,collided_bottom,collided_top);
            bounce(collided_side ,collided_left,collided_right,collided_bottom,collided_top);

        }
        return  hit;
    }
    private boolean isInsideCollsion(int collided_top,int  collided_left,int collided_right, int collided_bottom){
        if (
                predicted_X>= collided_left &&
                        predicted_X + diameter <= collided_right &&
                        predicted_Y >= collided_top &&
                        predicted_Y + diameter <= collided_bottom
        ) return  true;
         else
                 return false;

    }
private boolean stopdrawing = false;
    private void bounce(String ballSideCollided,double collidedLeft, double collidedRight, double collidedBottom, double collidedTop) {


    switch (ballSideCollided){
        case "top":
            setBallPosition(predicted_X, collidedTop - (diameter));
            inverseY();
            break;
        case "bottom":
            setBallPosition(predicted_X, collidedBottom);
            inverseY();

            break;
        case "right":
            setBallPosition(collidedRight, predicted_Y);
            inverseX();

            break;
        case "left":
            setBallPosition(collidedLeft-diameter, predicted_Y);
            inverseX();
            break;
    }

    }

    public void ballPhysics(String collisionSide, double ball_middle_point_x, double ball_middle_point_y, double collided_left, double collided_right, double collided_bottom, int collided_top) {

        //find how
        //check how far is the ball from the tip of the paddle width or height
        //
        double width_of_rectangle = collided_right - collided_left;
        double extra_space_x = collided_left;
        double ball_point_x = ball_middle_point_x - extra_space_x;
        double decimal_percentage_x = (ball_point_x / width_of_rectangle) ; // percentage in decimal form

        double height_of_rectangle = collided_bottom - collided_top;
        double extra_space_y = collided_top;
        double ball_point_y = ball_middle_point_y - extra_space_y;
        double decimal_percentage_y = (ball_point_y / height_of_rectangle) ;
        ;

        //ball direction is to the left then convert the percentage
        //negative means the direction of the ball is to the left
        if (ball_direction_X < 0) {
            decimal_percentage_x = 1 - decimal_percentage_x;
        }

        resetDirection();
        //reduce the speed of either axis
        //base on the percentage where the ball hit on the paddle
        if (collisionSide.equals("top") || collisionSide.equals("bottom")) {
            ball_direction_X = ball_direction_X - decimal_percentage_x * (ball_direction_X / Math.abs(ball_direction_X));
            ball_direction_Y = ball_direction_Y + decimal_percentage_x * (ball_direction_Y / Math.abs(ball_direction_Y));
        } else {
            ball_direction_X = ball_direction_X + decimal_percentage_y * (ball_direction_X / Math.abs(ball_direction_X));
            ball_direction_Y = ball_direction_Y - decimal_percentage_y * (ball_direction_Y / Math.abs(ball_direction_Y));
        }

    }

  public String coordinatesKey(int  size){
        predictMove();
        int radius = diameter/2;
      int x  = (int)(predicted_X+radius)/size;
      int y = (int)(predicted_Y+radius)/size;


      return  x+"/"+y;

    }
    public boolean isOutside() {
        return outside;
    }

    public void setOutside(boolean outside) {
        this.outside = outside;
    }
    public boolean isDisabled() {
        return disabled;
    }

    public double toNearestOne(double direction){
        if(direction <0){
            return -1;
        }
        return 1;

}




public void resetDirection(){
      this.ball_direction_X = toNearestOne(     this.ball_direction_X );
    this.ball_direction_Y = toNearestOne(     this.ball_direction_Y );
}

    public void  setBallPosition(double ball_X,double ball_Y){
        this.ball_X = ball_X;
        this.ball_Y = ball_Y;

    }

    public  void inverseY(){
    ball_direction_Y = -ball_direction_Y;

}
    public  void inverseX(){
        ball_direction_X = -ball_direction_X;

    }

    public double getPredicted_X() {
        return predicted_X;
    }

    public int getStrength() {
        return strength;
    }
}

