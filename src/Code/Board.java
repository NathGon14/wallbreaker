package Code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;


public class Board extends JPanel implements ActionListener {

    private GameLogic game ;
    private int brick_size;
    private int BOARD_HEIGHT;
    private int BOARD_WIDTH;

    private Timer timer;
    private Painter painter;
    private Paddle player;
   private  Balls balls;
    private Bricks bricks;
    private ColllisionChecker CC;
    private  int delay;
    public Board( GameLogic game) {
        this.game = game;
        init();

    }
    public  void init() {

       brick_size = game.getBrick_size();
       BOARD_HEIGHT =game.getBOARD_HEIGHT();
       BOARD_WIDTH= game.getBOARD_WIDTH();
        delay = game.getDelay();
       player = new Paddle(BOARD_WIDTH, BOARD_HEIGHT, brick_size);
        balls = new Balls(BOARD_WIDTH,BOARD_HEIGHT);
        bricks =new Bricks(brick_size);

        setFocusable(true);
        setBackground(Color.white);
        addKeyListener(new TAdapter());
        addMouseMotionListener(new MouseListenerHandler());

        timer = new Timer(delay,this);
        timer.start();
        painter = new Painter(player, balls, bricks,game);
        CC = new ColllisionChecker(player, balls, bricks,game);

        game.generateBall(balls);
        game.generateLevel(bricks);
        game.gameReset();
        nextLevel();

    }
    public  void  nextLevel(){
        //reset balls in to the middle
        balls.resetBalls();
        //reset player position
        player.reset();
        game.increaseLevel();
        //generate another map
        game.generateLevel(bricks);
        //
        game.getRemainingBricks(bricks);
        game.addBall();
        game.setStatus("Starting");
        game.resetRemainingTimeCounter();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(game.allGood() == false){
            player.disableMovement();
            player.reset();
            painter.draw(g);
            game.pause();
            checkGameStatus(g);
            return;
        }
        player.enableMovement();
        CC.checkCollsion();
        painter.draw(g);
    }
    public  void  checkGameStatus(Graphics g2d){
        switch (game.getStatus()){
            case "starting":
                painter.drawIntermission(g2d,"Game start in");
                break;
            case "completed":
                painter.drawIntermission(g2d,"You beat the game!!!");
                break;
            case "lose":
                painter.drawIntermission(g2d,"You lose");
                break;
            case "next":
                game.addTime();
                nextLevel();
                break;

        }


    }
    @Override
    public void addNotify() {
        super.addNotify();
        print();
    }

    public void print() {
        System.out.println("hellow");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }


    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                game.gameReset();
                nextLevel();
            } else if (key == KeyEvent.VK_SPACE) {
                game.removeCountDown();
            }

        }

    }





    class MouseListenerHandler implements MouseMotionListener{



        MouseListenerHandler(){

        }
        public void mouseScrolled(MouseEvent e){

        }


        public void mouseClicked(MouseEvent e) {


            int x = e.getClickCount();
            System.out.println("You CLICKED the mouse " + x + " times.");
        }


        public void mouseEntered(MouseEvent e) {


        }


        public void mouseExited(MouseEvent e) {

        }


        public void mousePressed(MouseEvent e) {
            System.out.println("You have PRESSED the mouse");
            int a =  e.getX();
            int b = e.getY();

            System.out.println("You have RELEASED the mouse at (" + a + "," + b + ") - (X,Y)");
        }



        public void mouseReleased(MouseEvent e) {



        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        public void mouseMoved(MouseEvent e){
            int a =  e.getX();
         player.updatePostionMouse(a);
        }


    }


}