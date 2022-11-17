package intospace;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Meteorite extends Rectangle{
    public static final int Small=0,Normal=1,Big=2;
    public static final int SizeSmall=50,SizeNormal=70,SizeBig=100;
    private int type;
    
    public Meteorite(int x, int y, int type){
        super(x,y,0,0);
        this.type=type;
        switch(type){
            case Meteorite.Small:
                this.width=Meteorite.SizeSmall;
                this.height=Meteorite.SizeSmall;
                break;
            case Meteorite.Normal:
                this.width=Meteorite.SizeNormal;
                this.height=Meteorite.SizeNormal;
                break;
            case Meteorite.Big:
                this.width=Meteorite.SizeBig;
                this.height=Meteorite.SizeBig;
                break;
        }
    }
    public boolean decreaseHp(){
        this.type--;
        switch(type){
            case Meteorite.Small:
                this.width=Meteorite.SizeSmall;
                this.height=Meteorite.SizeSmall;
                break;
            case Meteorite.Normal:
                this.width=Meteorite.SizeNormal;
                this.height=Meteorite.SizeNormal;
                break;
            case Meteorite.Big:
                this.width=Meteorite.SizeBig;
                this.height=Meteorite.SizeBig;
                break;
        }
        return this.type==-1;
    }
    public int getType(){
        return this.type;
    }
    public void draw(Graphics g, Image i){
        g.drawImage(i, this.x,this.y, null);
    }
}
