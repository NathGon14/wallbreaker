package Code.level;

import Code.bricks.Bricks;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Level extends  MapGenerator {


    private  double score;
    private  int remaining_balls;
    private  int remaining_bricks;
    private  long milisec = 1000;
    private double countdown;
    private  long  remaining_time;
    private String   status;
    private CountDown remainingTimeCounter =new CountDown();
    private CountDown countDownTimer =new CountDown();
    private boolean   paused  = true;
    int fontSize = 20;

    String fontStyle = "Helvetica";
    Font font = new Font(fontStyle, Font.BOLD, fontSize);
    int modalSize = (number_of_bricks_on_width-1)*brick_size;

    protected Image modalImage = new ImageIcon("wallbreaker/src/Images/modal.png").getImage().getScaledInstance(modalSize,modalSize,Image.SCALE_DEFAULT);

    DecimalFormat formatter = new DecimalFormat("#,###");
    public  void drawScreenData(Graphics g2d){
        g2d.setColor(Color.RED);
        g2d.setFont(font);
        g2d.drawString(formatter.format(score),5,getBOARD_HEIGHT()-30);
        int margin = 5;
        for (int i = 0; i< remaining_balls; i++){
            g2d.drawImage(ballImage,margin+(margin*i)+(i*ball_diameter),getBOARD_HEIGHT()-(ball_diameter+margin),null);
        }

        //draw remaining time upper part
        g2d.setColor(Color.white);
        g2d.drawString("Level "+level,5,fontSize+10);
        drawRemainingTime(g2d);

    }
        public void drawRemainingTime(Graphics g2d){
            remainingTime();

            long minutes = (remaining_time/1000)/60;
            long seconds = (remaining_time/1000) % 60;
            String minutesString =minutes <10 ? "0"+minutes: minutes+"";
            String secondsString = seconds <10 ? "0"+seconds: seconds+"";


            g2d.setColor(Color.RED);
            String remainingTime = "Time: "+minutesString+":"+secondsString;



            g2d.drawString(remainingTime,getBOARD_WIDTH()  -  g2d.getFontMetrics().stringWidth(remainingTime)-10,getBOARD_HEIGHT()-10);

        }
        public  void remainingTime(){
            if(paused) return;
            remaining_time =  remaining_time -remainingTimeCounter.getEstimatedTime();
            if(remaining_time <=0){
                remaining_time=0;
            }
        }

        public  void  resetRemainingTimeCounter(){
            remainingTimeCounter.startTime(true);
        }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void addTime() {
      this.remaining_time += 60*milisec*remaining_balls;
    }
    public void addBall() {
        if(this.remaining_balls >5)return;
        this.remaining_balls++;
    }


    public String  getCountDown(){
        String countDownSecond =   (int)(countdown/milisec)+"";
        countDown();
        return countDownSecond;
    }
    public void countDown(){
        countdown = countdown -  countDownTimer.getEstimatedTime();
        if(countdown <=1)
            removeCountDown();

    }
    public void removeCountDown(){
        countdown = 0;
        countDownTimer.startTime(false);
        unPaused();
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
        if(countDownTimer.isUsed()){
            g2d.setColor(Color.red);
            newY = drawString(g2d,getCountDown(),1,startingX,newY,modalSize);
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
          int balls_width = (modalSize - remaining_balls*(ball_diameter+margin))/2;
        for (int i = 0; i<remaining_balls; i++){
            g2d.drawImage(ballImage,startingX + balls_width + (ball_diameter+margin)*i,newY+30,null);
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

    public void unPaused(){
        paused = false;
        resetRemainingTimeCounter();
    }

    public void pause() {
        paused = true;
    }

    public  void startCountDown(int seconds){
        if (!countDownTimer.isUsed()){
            countdown = seconds * milisec;
            pause();
            countDownTimer.startTime(true);
        }
    }

    public boolean allGood(){



        if(countdown !=0 ){
            setStatus("starting");
            startCountDown(5);
            return  false;
        }
        if(remaining_bricks ==0){
            if(level >= maxLevel){
                setStatus("completed");
            }else  {
                startCountDown(5);
                setStatus("next");
            }

            return  false;
        }

        if(remaining_time == 0 ||  remaining_balls ==0 ) {
            setStatus("lose");
            return  false;
        }

        if(status.equals("dropped") ){
            setStatus("starting");
            startCountDown(5);
            return  false;
        }

        return  true;
    }


    public void addScore(double points) {
        this.score = score+points;
    }
    public void getRemainingBricks(Bricks bricks){
        this.remaining_bricks = bricks.getRemainingBricks();
    }

    public void reduceBricks(){
        this.remaining_bricks--;
    }

    public void reduceBall(){
    remaining_balls--;

    }
    public void gameReset(){
        score = 0;
        remaining_balls =3;
         countdown = 3.9 * milisec;
        remaining_time= 130 * milisec; //seconds
         status  = "starting";
        paused  = true;
        resetlLevel();
    }




}
