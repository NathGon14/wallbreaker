package Code.level;

import Code.bricks.Bricks;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;

public class Level extends  MapGenerator {

    private  double score;
    private  int remaining_balls;
    private TimerCountDown  remainingTime=new TimerCountDown();
    private TimerCountDown  countDown =new TimerCountDown();
    private boolean ball_dropped = false;
    int fontSize = 20;
    String fontStyle = "Helvetica";
    Font font = new Font(fontStyle, Font.BOLD, fontSize);
    int modalSize = (NUMBER_OF_BRICK_WIDTH -1)*BRICK_SIZE;
    DecimalFormat formatter = new DecimalFormat("#,###");
    protected Image modalImage = new ImageIcon( new File("src/Images/modal.png").getAbsolutePath()).getImage().getScaledInstance(modalSize,modalSize,Image.SCALE_DEFAULT);


    //this function will give what the status of the game
    //before drawing
    public String status(){

        if(!countDown.isStopped()){
            remainingTime.stop();
            return  "countdown";
        }
        if(remainingTime.getDuration() <=0){
            return  "time";
        }
        if(remaining_bricks <=0){
            return  "next level";
        }
        if(remaining_balls <=0){
            remainingTime.stop();
            return  "ball";
        }
        if(ball_dropped){
            ball_dropped =false;
            countDown.setTime(3);
            return  "countdown";
        }

        // if no issue then start the remaining time counter
        if(remainingTime.isStopped())
            remainingTime.start();

        return  "in game";

    }

    public  void drawScreenData(Graphics g2d){
        g2d.setColor(Color.RED);
        g2d.setFont(font);
        g2d.drawString(formatter.format(score),5,getBOARD_HEIGHT()-30);
        int margin = 5;
        for (int i = 0; i< remaining_balls; i++){
            g2d.drawImage(ballImage,margin+(margin*i)+(i* BALL_DIAMETER),getBOARD_HEIGHT()-(BALL_DIAMETER +margin),null);
        }
        //draw remaining time upper part
        g2d.setColor(Color.white);
        g2d.drawString("Level "+ STARTING_LEVEL,5,fontSize+10);
        drawRemainingTime(g2d);

    }
        public void drawRemainingTime(Graphics g2d){
            long minutes = (remainingTime.getDuration()/1000) /60 ;
            long seconds = (remainingTime.getDuration()/1000) % 60;
            String minutesString =minutes <10 ? "0"+minutes: minutes+"";
            String secondsString = seconds <10 ? "0"+seconds: seconds+"";
            g2d.setColor(Color.RED);
            String remainingTime = "Time: "+minutesString+":"+secondsString;
            g2d.drawString(remainingTime,getBOARD_WIDTH()  -  g2d.getFontMetrics().stringWidth(remainingTime)-10,getBOARD_HEIGHT()-10);
        }

    public void drawIntermision(Graphics g2d,String status){
        int startingX = (BOARD_WIDTH - modalSize)/2;
        int startingY=  (BOARD_WIDTH - (modalSize)/2)/2;
        g2d.drawImage(modalImage,startingX,startingY,null);
        g2d.setFont(font);
        g2d.setColor(Color.red);
        String message = status;
        int newY;
        newY = drawString(g2d,message,3,startingX,startingY,modalSize);
        if(!countDown.isStopped()){
            g2d.setColor(Color.red);
            newY = drawString(g2d,(countDown.getDuration()+1000)/1000+"",1,startingX,newY,modalSize);
        }
        g2d.setColor(Color.green);
        message = "SCORE";
        newY =   drawString(g2d,message,1,startingX,newY,modalSize);

        g2d.setFont(new Font(fontStyle,Font.BOLD,30));
        g2d.setColor(Color.GREEN);
        message = formatter.format(score);
        newY =  drawString(g2d,message,2,startingX,newY,modalSize);
        g2d.setFont(font);
        g2d.setColor(Color.RED);
        message = "Press Enter to reset";
        newY =  drawString(g2d,message,2,startingX,newY,modalSize);
        int margin = 10;
          int balls_width = (modalSize - remaining_balls*(BALL_DIAMETER +margin))/2;
        for (int i = 0; i<remaining_balls; i++){
            g2d.drawImage(ballImage,startingX + balls_width + (BALL_DIAMETER +margin)*i,newY+30,null);
        }

    }
    public int drawString(Graphics g2d, String message , int margin ,int startingX,int startingY,int size ){
        int viewX , viewY;
        viewX = getMiddleIndex(message,startingX,size,g2d);
        viewY = startingY + (fontSize*margin)+10;
        g2d.drawString(message,viewX,viewY);
        return viewY;
    }
    public int getMiddleIndex(String message,int x,int size,Graphics g2d){
        x = x +(size -g2d.getFontMetrics().stringWidth(message))/2;
        return x;
    }

    public void addBall() {
        if(this.remaining_balls >5)return;
        this.remaining_balls++;
    }

    public  void  setCountDown(int seconds){
        countDown.setTime(seconds);
    }

    public void addScore(double points) {
        this.score = score+points;
    }

    public void reduceBricks(){
        this.remaining_bricks--;
    }

    public void reduceBall(){
        ball_dropped =true;
    remaining_balls--;
    }

    public void backDefault(){
        score = 0;
        remaining_balls =DEFAULT_REMAINING_BALL;
        this.STARTING_LEVEL = 0;
        remainingTime.destroy();
    }
    public  void  addTime(){
        remainingTime.addDuration(DEFAULT_REMAINING_TIME);
    }
    public void removeCountdown(){
        countDown.destroy();
    }

    public void increaseLevel() {
        this.STARTING_LEVEL += 1;
    }



}
