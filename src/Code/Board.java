package Code;

import Code.Ball.Balls;
import Code.bricks.Bricks;
import Code.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Board extends JPanel implements ActionListener {

    private Level game ;
    private int brick_size;
    private int BOARD_HEIGHT;
    private int BOARD_WIDTH;

    private Timer timer;
    private Painter painter;
    private Paddle player;
   private Balls balls;
    private Bricks bricks;
    private ColllisionChecker CC;
    private  int delay;
    public Board( Level game) {
        this.game = game;
        init(); // starting the neccesary things

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
        gameReset();

    }
    public void gameReset(){
        backTodefault();
        nextLevel();
    }

    public  void  nextLevel(){
        balls.resetBalls();
        game.increaseLevel();
        game.generateMap(bricks);
        game.generateBall(balls);
        game.addBall();
        game.addTime();
        game.setCountDown(3);
    }
    public  void  backTodefault(){
        game.backDefault();
        balls.destroy(); // remove all the balls
        game.generateBall(balls);
        player.reset();
        balls.resetBalls();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String gameStatus = game.status();

        if(gameStatus.equals("in game")) player.enableMovement();
        else{
            player.disableMovement();
            player.reset();
        }
        if(gameStatus.equals("in game")){
            //check collision
            CC.checkCollsion();
            painter.drawInGame(g);
        }else if(gameStatus.equals("countdown")){
            painter.drawModal(g,"GAME START IN: ");
        }else if (gameStatus.equals("time")){
            painter.drawModal(g,"LOSE IN TIME");
        }else if (gameStatus.equals("ball")){
            painter.drawModal(g,"OUT OF BALL");
        }
        else if (gameStatus.equals("next level")){
            nextLevel();
            painter.drawInGame(g);
        }

    }

    @Override
    public void addNotify() {
        super.addNotify();

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
                gameReset();

            } else if (key == KeyEvent.VK_SPACE) {
                if(game.status().equals("countdown")) game.removeCountdown();

            }

        }

    }



    class MouseListenerHandler implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        public void mouseMoved(MouseEvent e){
            int a =  e.getX();
         player.updatePostionMouse(a);
        }


    }


}