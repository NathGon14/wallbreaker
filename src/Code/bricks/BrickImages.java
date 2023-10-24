package Code.bricks;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class BrickImages {


    ArrayList<Image> brickImages = new ArrayList<Image>();
    private  String pathname;
    private int size;
    private int framesSize;
    private int toughness;
    private  int framePerHit;
    private  int frameIndex;
    private  int frameCounter = -1;
    public BrickImages(String pathname,int toughness ,int size){
        //folders number
        if(toughness > 5){
            toughness = 5;
        }
        this.pathname = pathname+toughness;

        this.toughness =toughness;
            this.size = size;
        loadImages();
    }
    public void loadImages(){
        File folder = new File(this.pathname);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {

                Image frame = new ImageIcon(pathname+"/"+ listOfFiles[i].getName()).getImage()
                        .getScaledInstance(size,size,Image.SCALE_DEFAULT);
                brickImages.add(frame);
            }
        }
        framesSize = brickImages.size();
      framePerHit = (int) Math.ceil((double) toughness / (double)framesSize);
        frameIndex = framesSize-1;


    }

    public Image getImage(){
        frameCounter++;


    if( frameCounter != 0 && frameCounter % framePerHit == 0 ){
        frameIndex = frameIndex -1;
    }
     if(frameIndex < 0 )frameIndex =0;

    return brickImages.get(frameIndex);

    }




}
