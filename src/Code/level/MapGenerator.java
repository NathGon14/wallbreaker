package Code.level;

import Code.Ball.Balls;
import Code.bricks.Bricks;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MapGenerator {
     int brick_size = 30;
    private int delay = 5;
    protected int ball_diameter = 20;
    protected int number_of_bricks_on_width = 12;
    private int height_multiplier = 22;
    private  int heightOffset = 7;//how far the bricks from the top
    private  int number_row = 2;//how rows of bricks
    private Random random = new Random();
    protected int BOARD_HEIGHT = brick_size * height_multiplier;
    protected int BOARD_WIDTH = brick_size * number_of_bricks_on_width;
    private String mapStucture [][];
    protected  int level;
    protected  int maxLevel = 5;
    protected Image ballImage = new ImageIcon("wallbreaker/src/Images/ball.png").getImage().getScaledInstance(ball_diameter,ball_diameter,Image.SCALE_DEFAULT);;
    protected Image background = new ImageIcon("wallbreaker/src/Images/background.png").getImage().getScaledInstance(BOARD_WIDTH,BOARD_HEIGHT,Image.SCALE_DEFAULT);;

    private  String [] rowStructure ={"full","alternate","besides","middle","left","right"};

    public void generateLevel(Bricks bricks){
        String [][]  mapData = new String[BOARD_HEIGHT/brick_size][number_of_bricks_on_width];

        for(int y = heightOffset ; y< number_row +heightOffset +Math.min(level, 7) ;y++){
            generateRow(mapData,y);
        }
        mapStucture = mapData;
       bricks.createBricks(mapStucture,level);
    }
    public void generateBall(Balls balls){
        balls.createBall(ball_diameter);
    }

    public void generateRow(String [][] mapData, int currentRow){
        String row_structure = rowStructure[random.nextInt(0,rowStructure.length-1)];

        switch (row_structure){
            case "full":
                for (int x = 0; x < mapData[currentRow].length;x++)
                    mapData[currentRow][x] ="brick";
                break;
            case "alternate":
                for (int x = 0; x < mapData[currentRow].length;x+=2)
                    mapData[currentRow][x] ="brick";
                break;
            case "besides":
                int number_per_side = mapData[currentRow].length / 3;
                for (int x =0; x < number_per_side;x++){
                    mapData[currentRow][x] ="brick";
                    mapData[currentRow][ mapData[currentRow].length-1 - x] ="brick";
                }
                break;
            case "middle":
                int middleIndex = mapData[currentRow].length / 3;
                for (int x = middleIndex; x<= mapData[currentRow].length -middleIndex;x++ ){
                    mapData[currentRow][x] ="brick";
                }
                break;
            case "left":
                int maxlength = mapData[currentRow].length / 2;
                for (int x = 0; x<= mapData[currentRow].length -maxlength;x++ ){
                    mapData[currentRow][x] ="brick";
                }
                break;
            case "right":
                int midIndex = mapData[currentRow].length / 2;
                for (int x = midIndex; x<mapData[currentRow].length;x++ ){
                    mapData[currentRow][x] ="brick";
                }
                break;
            case "empty":
                for (int x = 0; x<mapData[currentRow].length;x++ ){
                    mapData[currentRow][x] =null;
                }
                break;
        }

    }


    public int getDelay() {
        return delay;
    }


    public int getBrick_size() {
        return brick_size;
    }

    public int getBOARD_HEIGHT() {
        return BOARD_HEIGHT;
    }

    public int getBOARD_WIDTH() {
        return BOARD_WIDTH;
    }

    public Image getBackground() {
        return background;
    }

    public void increaseLevel() {
        this.level+= 1;
    }
    public void resetlLevel() {
        this.level = 0;
    }

}
