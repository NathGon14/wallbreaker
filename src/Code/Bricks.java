package Code;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Bricks {


    private HashMap<String, Brick> Bricks_Collection;
    private int brick_size;

    private Random ran = new Random();


    public Bricks(int brick_size){
        this.brick_size = brick_size;
    }
    public  void  removeBrick(String objectKey){

        Bricks_Collection.remove(objectKey);
    }
    public  boolean  findBrick(String key){
        return Bricks_Collection.containsKey(key);
    }
    public  Brick  getBrick(String key){
        return Bricks_Collection.get(key);
    }
    public int createBricks(String map [][],int level){
        Bricks_Collection= new HashMap<>();
        for (int y = 0 ; y<map.length;y++){
            for (int x = 0 ; x<map[y].length;x++){
                if(map[y][x]== null )continue;
                int toughness = ran.nextInt(0,level+1);
                toughness = toughness ==0? 1 :toughness;
                Bricks_Collection.putIfAbsent(x+"/"+y,new Brick(toughness,brick_size,x,y));
            }

        }

        return  Bricks_Collection.size();
    }

    public int getRemainingBricks() {
        return  Bricks_Collection.size();
    }
    public int getBrick_size() {
        return brick_size;
    }

    public void drawBricks(Graphics g2d){
        for (Brick brick:Bricks_Collection.values()
             ) {
            brick.drawBrick(g2d);
        }


    }


}
