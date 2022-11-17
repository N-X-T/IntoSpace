package intospace;

import java.awt.Rectangle;

public class Spaceship extends Rectangle{
    public static final int WIDTH=100,HEIGHT=72;
    
    public boolean up=false,down=false,left=false,right=false;
    
    public Spaceship(int x,int y){
        super(x,y,100,72);
    }
}
