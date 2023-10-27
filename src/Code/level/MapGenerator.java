package Code.level;

import Code.Ball.Balls;
import Code.bricks.Bricks;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class MapGenerator extends GameData {


    private Random random = new Random();
    private String mapStucture [][];
    private  String [] rowStructure ={"full","alternate","besides","middle","left","right"};
    private  String [] mapDesign ={"no-left","no-middle","no-right","no-all","no-both-side","nothing","x-right!","x-left!","x!"};

private final String brick_name = "brick";
    protected   int remaining_bricks;

    public void generateMap(Bricks bricks){
        String [][]  mapData = new String[BOARD_HEIGHT/BRICK_SIZE][NUMBER_OF_BRICK_WIDTH];

        for(int y = HEIGHT_OFFSET; y< NUMBER_OF_BRICK_ROW + HEIGHT_OFFSET +Math.min(STARTING_LEVEL, 7) ; y++){
            generateRow(mapData,y);
        }
        mapStucture = mapData;
        mapDesign();
        remaining_bricks =   bricks.createBricks(mapStucture, STARTING_LEVEL);
    }
    public  void  mapDesign(){
        String design = mapDesign[random.nextInt(0,mapDesign.length-1)];
        System.out.println(design);
        switch (design){
            case "no-left":
                for(int y=HEIGHT_OFFSET ; y<mapStucture.length;y++){
                    mapStucture[y][0] = null;
                }
                break;
            case "no-right":
                for(int y=HEIGHT_OFFSET ; y<mapStucture.length;y++){
                    mapStucture[y][mapStucture[y].length-1] = null;
                }
                break;
            case "no-middle":
                for(int y=HEIGHT_OFFSET ; y<mapStucture.length;y++){
                    mapStucture[y][mapStucture[y].length/2] = null;
                }
                break;

            case "no-both-side":
                for(int y=HEIGHT_OFFSET ; y<mapStucture.length;y++){
                    mapStucture[y][0] = null;
                    mapStucture[y][mapStucture[y].length-1] = null;
                }
            case "no-all":
                for(int y=HEIGHT_OFFSET ; y<mapStucture.length;y++){
                    mapStucture[y][0] = null;
                    mapStucture[y][mapStucture[y].length/2] = null;
                    mapStucture[y][mapStucture[y].length-1] = null;
                }
                break;
            case "x-right":
                for(int y=HEIGHT_OFFSET,x=0; y<mapStucture.length;y++,x++){
                    if(mapStucture[y][x]==null) break;
                    mapStucture[y][x] = null;
                }
                break;
            case "x-left":
                for(int y=HEIGHT_OFFSET,x=0 ; y<mapStucture.length;y++,x++){
                    if(mapStucture[y].length -(x+1) <0) break;
                    mapStucture[y][mapStucture[y].length -(x+1)] = null;
                }
                break;

            case "x":
                for(int y=HEIGHT_OFFSET ,x=0; y<mapStucture.length;y++,x++){
                    if(mapStucture[y][x]==null || mapStucture[y].length -(x+1) <0) break;
                    mapStucture[y][x] = null;
                    mapStucture[y][mapStucture[y].length -(x+1)] = null;
                }
                break;

        }

    }
    public void generateBall(Balls balls){
        balls.createBall(BALL_DIAMETER);
    }

    public void generateRow(String [][] mapData, int currentRow){
        String row_structure = rowStructure[random.nextInt(0,rowStructure.length-1)];

        switch (row_structure){
            case "full":
                for (int x = 0; x < mapData[currentRow].length;x++)
                    mapData[currentRow][x] =brick_name;
                break;
            case "alternate":
                for (int x = 0; x < mapData[currentRow].length;x+=2)
                    mapData[currentRow][x] =brick_name;
                break;
            case "besides":
                int number_per_side = mapData[currentRow].length / 3;
                for (int x =0; x < number_per_side;x++){
                    mapData[currentRow][x] =brick_name;
                    mapData[currentRow][ mapData[currentRow].length-1 - x] =brick_name;
                }
                break;
            case "middle":
                int middleIndex = mapData[currentRow].length / 3;
                for (int x = middleIndex; x<= mapData[currentRow].length -middleIndex;x++ ){
                    mapData[currentRow][x] =brick_name;
                }
                break;
            case "left":
                int maxlength = mapData[currentRow].length / 2;
                for (int x = 0; x<= mapData[currentRow].length -maxlength;x++ ){
                    mapData[currentRow][x] =brick_name;
                }
                break;
            case "right":
                int midIndex = mapData[currentRow].length / 2;
                for (int x = midIndex; x<mapData[currentRow].length;x++ ){
                    mapData[currentRow][x] =brick_name;
                }
                break;
            case "empty":
                for (int x = 0; x<mapData[currentRow].length;x++ ){
                    mapData[currentRow][x] =null;
                }
                break;
        }

    }






}
