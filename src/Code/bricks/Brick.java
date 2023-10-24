package Code.bricks;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Random;

public class Brick {

    private int toughness;

    private  int width;
    private  int height;
    private  int x;
    private  int y;
    private boolean disabled =false;
    private  Color color;


    String image_path = "src/Images/bricks/";
    private int folderName;
    private Image imageView;

    BrickImages brickImage;
    public Brick(int toughness,int size,int x,int y){
        this.toughness= toughness;
        folderName = this.toughness;
        width = size;
        height = size;
        this.x =x * size;
        this.y =y* size;
        brickImage = new BrickImages(image_path,folderName,size);
        imageView = brickImage.getImage();

    }
    private Random rgb = new Random();


    private Image imageview;


    public void drawBrick(Graphics g2d){
        BufferedImage bf = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        g2d.drawImage(bf,x,y,null);
      g2d.drawImage(imageView,x,y,null);


    }
    public void changeColor(){
        float r = rgb.nextFloat();
        float g = rgb.nextFloat();
        float b = rgb.nextFloat();
        color = new Color(r,g,b);

    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getToughness() {
        return toughness;
    }

    public void setToughness(int toughness) {
        this.toughness = toughness;
        imageView = brickImage.getImage();
    }
}
