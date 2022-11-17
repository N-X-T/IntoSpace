package intospace;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
    private final Spaceship spaceship;
    private Main main;
    private ArrayList<Rocket> rocket;
    private Image Background,spaceshipImg,Pipe,rocketImg,meteoriteBig,meteoriteSmall,meteoriteNormal,addRocketImg,ufo1,ufo2;
    private Image score;
    private ArrayList<UFO> rect;
    private ArrayList<Meteorite> meteorite;
    private ArrayList<Rectangle> addRocket;

    public GamePanel(Main main,Spaceship spaceship,ArrayList<UFO> rect, ArrayList<Rocket> rocket, ArrayList<Meteorite> meteorite, ArrayList<Rectangle> addRocket){
        this.main=main;
        this.spaceship=spaceship;
        this.rect=rect;
        this.rocket=rocket;
        this.meteorite=meteorite;
        this.addRocket=addRocket;
        try{
            Background=ImageIO.read(getClass().getResource("/Background.jpg")).getScaledInstance(Main.WIDTH, Main.HEIGHT, Image.SCALE_SMOOTH);
            spaceshipImg=ImageIO.read(getClass().getResource("/spaceship.png")).getScaledInstance(Spaceship.WIDTH, Spaceship.HEIGHT, Image.SCALE_SMOOTH);
            rocketImg=ImageIO.read(getClass().getResource("/rocket.png")).getScaledInstance(Rocket.WIDTH, Rocket.HEIGHT, Image.SCALE_SMOOTH);
            Pipe=ImageIO.read(getClass().getResource("/laser.png")).getScaledInstance(50, Main.HEIGHT*2, Image.SCALE_SMOOTH);
            
            meteoriteBig=ImageIO.read(getClass().getResource("/meteorite-big.png")).getScaledInstance(Meteorite.SizeBig,Meteorite.SizeBig, Image.SCALE_SMOOTH);
            meteoriteNormal=ImageIO.read(getClass().getResource("/meteorite-normal.png")).getScaledInstance(Meteorite.SizeNormal,Meteorite.SizeNormal, Image.SCALE_SMOOTH);
            meteoriteSmall=ImageIO.read(getClass().getResource("/meteorite-small.png")).getScaledInstance(Meteorite.SizeSmall,Meteorite.SizeSmall, Image.SCALE_SMOOTH);
            
            addRocketImg=ImageIO.read(getClass().getResource("/addRocket.png")).getScaledInstance(50,50, Image.SCALE_SMOOTH);
            
            ufo1=ImageIO.read(getClass().getResource("/ufo1.png")).getScaledInstance(150,50, Image.SCALE_SMOOTH);
            ufo2=ImageIO.read(getClass().getResource("/ufo2.png")).getScaledInstance(150,50, Image.SCALE_SMOOTH);
            
            score=ImageIO.read(getClass().getResource("/score.png")).getScaledInstance(500,252, Image.SCALE_SMOOTH);
        }catch(IOException e){System.out.println(e);}
    }
    public void resizeImg(int WIDTH, int HEIGHT){
        this.Background=this.Background.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
        this.Pipe=this.Pipe.getScaledInstance(50, HEIGHT*2, Image.SCALE_SMOOTH);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(Background, 0, 0, null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,30));
        g.drawString("Score: "+this.main.getscore(), 0, 30);
        g.drawString("Rocket: "+Rocket.num, 0, 60);
        
        for(Meteorite x: meteorite){
            switch(x.getType()){
                case Meteorite.Small:
                    x.draw(g, meteoriteSmall);
                    break;
                case Meteorite.Normal:
                    x.draw(g, meteoriteNormal);
                    break;
                case Meteorite.Big:
                    x.draw(g, meteoriteBig);
                    break;
            }
        }
        for(UFO x:rect){
            if(x.Up){
                g.drawImage(Pipe,x.x,-(Main.HEIGHT*2-x.height),null);
                g.drawImage(ufo1,x.x-50,x.y-5,null);
            }
            if(x.Down){
                g.drawImage(Pipe,x.x,x.height+150,null);
                g.drawImage(ufo2,x.x-50,Main.HEIGHT-85,null);
            }
        }
        for(Rocket x:rocket){
            g.drawImage(rocketImg, Math.round(x.x),Math.round(x.y), null);
        }
        for(Rectangle x:addRocket){
            g.drawImage(addRocketImg,x.x,x.y,null);
        }
        g.drawImage(spaceshipImg, Math.round(spaceship.x),Math.round(spaceship.y), null);
        
        if(this.main.getpause()){
            g.drawString("Guide:", Main.WIDTH-425, 30);
            g.drawString("Up,Down,Left,Right : Move", Main.WIDTH-425, 60);
            g.drawString("Enter: Fire Rocket",Main.WIDTH-425, 90);
            g.drawString("P: Pause",Main.WIDTH-425, 120);
        }
        if(this.main.getend()){
            g.drawImage(score, (Main.WIDTH-score.getWidth(null))/2, (Main.HEIGHT-score.getHeight(null))/2, null);
            g.setFont(new Font("Arial",Font.BOLD,100));
            g.drawString(String.valueOf(this.main.getscore()), (Main.WIDTH-g.getFontMetrics().stringWidth(String.valueOf(this.main.getscore())))/2, (Main.HEIGHT+100)/2);
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString("Press Space To Continue!", (Main.WIDTH-g.getFontMetrics().stringWidth("Press Space To Continue!"))/2, (Main.HEIGHT+200)/2);
        }
    }
}
