package intospace;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Main implements ActionListener, KeyListener {
    public static int WIDTH=800,HEIGHT=600;
    
    private JFrame frame;
    private GamePanel panel;
    private Spaceship spaceship;
    private ArrayList<Rocket> rocket;
    private ArrayList<UFO> rect;
    private ArrayList<Meteorite> meteorite;
    private ArrayList<Rectangle> addRocket;
    private boolean pause,end;
    private int score;
    
    public void start(){
        //create frame
        frame=new JFrame("Into Space");
        frame.setSize(WIDTH,HEIGHT);
        frame.setResizable(true);
        frame.addKeyListener(this);
        frame.setMinimumSize(new Dimension(800,600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //spaceship
        spaceship=new Spaceship(Main.WIDTH/2-Spaceship.WIDTH/2,Main.HEIGHT/2-Spaceship.HEIGHT/2);
        
        //rocket
        rocket=new ArrayList<>();
        
        //vat can
        rect=new ArrayList<>();
        
        //meteorite
        meteorite=new ArrayList<>();
        
        //add rocket
        addRocket=new ArrayList<>();
        
        //add panel
        panel=new GamePanel(this,spaceship,rect,rocket,meteorite,addRocket);
        frame.add(panel);
        
        //resize game theo window
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Main.HEIGHT=e.getComponent().getHeight();
                Main.WIDTH=e.getComponent().getWidth();
                spaceship.x=e.getComponent().getWidth()/2-Spaceship.WIDTH/2;
                spaceship.y=e.getComponent().getHeight()/2-Spaceship.HEIGHT/2;
                panel.resizeImg(e.getComponent().getWidth(), e.getComponent().getHeight());
            }
        });
        
        //hien thi
        frame.setVisible(true);
        
        //boolean end game
        pause=true;
        end=false;
        
        //loop actionPerformed
        new Timer(10,this).start();
    }
    public static void main(String[] args){
        new Main().start();
    }
    
    public int getscore(){
        return this.score;
    }
    
    public boolean getpause(){
        return this.pause;
    }
    
    public boolean getend(){
        return this.end;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        panel.repaint();
        if(!pause){
//            panel.repaint();
            score+=1;
            
            if(spaceship.up)spaceship.y-=5;
            if(spaceship.down)spaceship.y+=5;
            if(spaceship.left)spaceship.x-=10;
            if(spaceship.right)spaceship.x+=5;
            
            
            ArrayList<UFO> remove=new ArrayList<>();
            ArrayList<Rocket> removeRocket=new ArrayList<>();
            ArrayList<Meteorite> removeMeteorite=new ArrayList<>();
            ArrayList<Rectangle> removeAddRocket=new ArrayList<>();
            
            if(score%199==0){
                rect.add(new UFO(Main.WIDTH,0,50,(int)(Math.random()*(Main.HEIGHT-250-100)+100)));
            } else if(score%23==0){
                meteorite.add(new Meteorite(Main.WIDTH,(int)(Math.random()*Main.HEIGHT),0));
            } else if(score%53==0){
                meteorite.add(new Meteorite(Main.WIDTH,(int)(Math.random()*Main.HEIGHT),1));
            } else if(score%101==0){
                meteorite.add(new Meteorite(Main.WIDTH,(int)(Math.random()*Main.HEIGHT),2));
            } else if(score%257==0){
                addRocket.add(new Rectangle(Main.WIDTH,(int)(Math.random()*Main.HEIGHT),50,50));
            }
            for(UFO x:rect){
                if((x.Up && x.intersects(spaceship)) || (x.Down && new UFO(x.x,x.y+x.height+150,50,Main.HEIGHT-(x.y+x.height+150)).intersects(spaceship))){
                    pause=true;
                    break;
                }
                for(Rocket y:rocket){
                    //fire to ufo
                    if(x.Up && new UFO(x.x-50,x.y-5,150,50).intersects(y)){
                        x.Up=false;
                        removeRocket.add(y);
                    } else if(x.Down && new UFO(x.x-50,Main.HEIGHT-85,150,50).intersects(y)){
                        x.Down=false;
                        removeRocket.add(y);
                    }
                    
                    //fire to laser
//                    if(x.Up && x.intersects(y)){
//                        x.Up=false;
//                        removeRocket.add(y);
//                    } else if(x.Down && new UFO(x.x,x.y+x.height+150,50,Main.HEIGHT-(x.y+x.height+150)).intersects(y)){
//                        x.Down=false;
//                        removeRocket.add(y);
//                    }
                }
                if(x.move)x.height+=5;
                    else x.height-=5;
                if(x.height>=Main.HEIGHT-250)x.move=false;
                    else if(x.height<=100) x.move=true;
               if(x.x<0)remove.add(x);
                x.x-=10;
            }
            rect.removeAll(remove);
            
            for(Meteorite x: meteorite){
                if(x.intersects(spaceship)){
                    pause=true;
                    break;
                }
                for(Rocket y:rocket){
                    if(x.intersects(y)){
                        removeRocket.add(y);
                        if(x.decreaseHp())removeMeteorite.add(x);
                    }
                }
                if(x.x<0)removeMeteorite.add(x);
                x.x-=10;
            }
            meteorite.removeAll(removeMeteorite);
            
            for(Rectangle x:addRocket){
                if(x.intersects(spaceship)){
                    Rocket.num+=1;
                    removeAddRocket.add(x);
                }
                if(x.x<0)removeAddRocket.add(x);
                x.x-=10;
            }
            addRocket.removeAll(removeAddRocket);
            
            if(spaceship.x<0)spaceship.x=0;
            if(spaceship.x+Spaceship.WIDTH>Main.WIDTH)spaceship.x=Main.WIDTH-Spaceship.WIDTH;
            if(spaceship.y<0)spaceship.y=0;
            if(spaceship.y+Spaceship.HEIGHT+30>Main.HEIGHT)spaceship.y=Main.HEIGHT-Spaceship.HEIGHT-30;
            
            for(Rocket y:rocket){
                y.x+=10;
                if(y.x>Main.WIDTH)removeRocket.add(y);
            }
            rocket.removeAll(removeRocket);
            
            if(pause){
                end=true;
//               rocket.clear();
//               addRocket.clear();
//               Rocket.num=10;
//               score=0;
//               rect.clear();
//               meteorite.clear();
//               spaceship.y=Main.HEIGHT/2-Spaceship.HEIGHT/2;
//               spaceship.x=Main.WIDTH/2-Spaceship.WIDTH/2;
//               spaceship.up=false;
//               spaceship.down=false;
//               spaceship.left=false;
//               spaceship.right=false;
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e){
        if(!end)pause=false;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                spaceship.up=true;
                break;
            case KeyEvent.VK_DOWN:
                spaceship.down=true;
                break;
            case KeyEvent.VK_RIGHT:
                spaceship.right=true;
                break;
            case KeyEvent.VK_LEFT:
                spaceship.left=true;
                break;
            case KeyEvent.VK_ENTER:
                if(--Rocket.num>=0)
                    this.rocket.add(new Rocket(spaceship.x+25,spaceship.y+25));
                else Rocket.num=0;
                break;
            case KeyEvent.VK_P:
                this.pause=true;
                break;
            case KeyEvent.VK_SPACE:
                if(end){
                    end=false;
                    rocket.clear();
                    addRocket.clear();
                    Rocket.num=10;
                    score=0;
                    rect.clear();
                    meteorite.clear();
                    spaceship.y=Main.HEIGHT/2-Spaceship.HEIGHT/2;
                    spaceship.x=Main.WIDTH/2-Spaceship.WIDTH/2;
                    spaceship.up=false;
                    spaceship.down=false;
                    spaceship.left=false;
                    spaceship.right=false;
                }
               break;
            default:
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                spaceship.up=false;
                break;
            case KeyEvent.VK_DOWN:
                spaceship.down=false;
                break;
            case KeyEvent.VK_RIGHT:
                spaceship.right=false;
                break;
            case KeyEvent.VK_LEFT:
                spaceship.left=false;
                break;
            default:
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e){
        
    }
}
