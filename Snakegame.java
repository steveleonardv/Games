import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;

public class Snakegame extends JPanel implements ActionListener,KeyListener {

    private class Tile{
        int x;
        int y;
        Tile(int x,int y){
            this.x=x;
            this.y=y;
        }
    }

    int boardHeight;
    int boardWidth;
    int tilesize=25;
    Random random;
    
    ArrayList<Tile> snakebody;

    Tile snakeHead;

    Tile food;

    //game logic
    Timer gameloop;
    int velocityx;
    int velocityy;
    boolean gameover=false;
    
    
    Snakegame(int boardWidth,int boardHeight){
        this.boardHeight=boardHeight;
        this.boardWidth=boardWidth;
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        setBackground(Color.black);

        addKeyListener(this);
        setFocusable(true);
        
        snakeHead=new Tile(5,5);
        snakebody=new ArrayList<>();

        food=new Tile(10,10);
        random=new Random();

        velocityx=1;
        velocityy=0;

    
        gameloop=new Timer(150,this);
        gameloop.start();
        
    placeFood();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //Grid
        //for (int i=0;i<boardHeight/tilesize;i++){
            //g.drawLine(i*tilesize,0,i*tilesize,boardHeight);
            //g.drawLine(0,i*tilesize,boardWidth,i*tilesize);

        //}
        //Snake
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x*tilesize,snakeHead.y*tilesize,tilesize,tilesize,true);

        //snake body
        for(int i=0;i<snakebody.size();i++){
            Tile snakepart = snakebody.get(i);
            g.fill3DRect(snakepart.x*tilesize,snakepart.y*tilesize,tilesize,tilesize,true);
        }

        //food
        g.setColor(Color.red);
        g.fill3DRect(food.x*tilesize,food.y*tilesize,tilesize,tilesize,true);

        //score
        
        if(gameover==true){
            g.setFont(new Font("Times New Roman",Font.BOLD,100));
            g.setColor(Color.RED);
       
             
            g.drawString("Game",tilesize+165,tilesize+250);
             g.drawString("Over",tilesize+165,tilesize+400);

        }

            else{
                g.drawString("Score: " + String.valueOf(snakebody.size()),tilesize-16,tilesize);
            }
    }
    public void placeFood(){

        food.x=random.nextInt(boardWidth/tilesize);
        food.y=random.nextInt(boardHeight/tilesize);
    }

    public boolean collision(Tile t1,Tile t2){
        return t1.x == t2.x && t1.y == t2.y;
    }

    public void move(){
        
        //food eaten
        if(collision(snakeHead,food)){
            snakebody.add(new Tile(food.x,food.y));
            placeFood();
        }
        //snakebody

        for(int i=snakebody.size()-1;i>=0;i--){
            Tile snakepart = snakebody.get(i);
            if(i==0){
                snakepart.x=snakeHead.x;
                snakepart.y=snakeHead.y;
            }
            else{
                Tile prevsnakepart = snakebody.get(i-1);
                snakepart.x=prevsnakepart.x;
                snakepart.y=prevsnakepart.y;
            }
        }
        
        
        //snakehead
        snakeHead.x += velocityx;
        snakeHead.y += velocityy;

        //game over

        for(int i=0;i<snakebody.size();i++){
            Tile snakepart=snakebody.get(i);
            if(collision(snakepart, snakeHead)){
                gameover= true;
                

            }
        }
        if(snakeHead.x*tilesize <= 0 || snakeHead.x*tilesize >=boardWidth || 
        snakeHead.y*tilesize <= 0 || snakeHead.y*tilesize >=boardHeight){
            gameover = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
        if(gameover){
            gameloop.stop();
        }
    }
     @Override
    public void keyPressed(KeyEvent e) {
        int kc=e.getKeyCode();
        if(kc==KeyEvent.VK_UP && velocityy!=1){
            velocityx=0;
            velocityy=-1;
        }
        else if(kc==KeyEvent.VK_DOWN && velocityy!=-1){
            velocityx=0;
            velocityy=1;
        }
         else if(kc==KeyEvent.VK_LEFT && velocityx!=1){
            velocityx=-1;
            velocityy=0;
        }
         else if(kc==KeyEvent.VK_RIGHT && velocityx!=-1){
            velocityx=1;
            velocityy=0;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {}
     @Override
    public void keyReleased(KeyEvent e) {}
       
}
