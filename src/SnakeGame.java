import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile{
        int x,y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    
    //Food
    Tile food;
    Random random;

    //Game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;
    int highScore = 0;
    
    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;


        gameLoop = new Timer (100, this);
        gameLoop.start();
        

    }

    public void startGame(){
        //Snake
        snakeHead = new Tile(5, 5);

        //Food
        food = new Tile(10, 10);

    }

    public void update(){
        //Snake
        snakeHead = new Tile(5, 5);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //Grid
        // for(int i=0; i<boardWidth/tileSize; i++){
        //     g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
        //     g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
        // }

        //Food
        g.setColor(Color.red);
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);


        //Snake Head
        g.setColor(Color.green);
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize,true);

        //Snake Body
        for(int i = 0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver){   
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            g.drawString( "Press SPACE BAR to Restart", tileSize - 16, tileSize + 20);
        }else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()),tileSize - 16, tileSize);

        }

        //High Score
        g.setColor(Color.WHITE);
        if(snakeBody.size()>highScore){
            g.drawString("Highest Score: " + String.valueOf(snakeBody.size()), boardWidth - 150, tileSize);
        }else{
            g.drawString("Highest Score: " + highScore, boardWidth - 150, tileSize);
        }
       
    }

    public void placeFood(){
       food.x = random.nextInt(boardWidth/tileSize);
       food.y = random.nextInt(boardHeight/tileSize);
    }
    
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){

        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //Snake body
        for(int i = snakeBody.size()-1; i>=0; i--){ 
            Tile snakePart = snakeBody.get(i);
            if(i==0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
                
            }
            else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
            
        }


        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //Game Over conditions
        for(int i = 0; i<snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //collide with snake head
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight){
            gameOver = true;
        }

        //Update the highest score
        if(gameOver){
            if(snakeBody.size()>highScore){
                highScore = snakeBody.size();
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    
    }


    @Override
    public void keyPressed(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
       }
       else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
       }
       else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
       }
       else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
       }
       else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            if(gameOver){
                snakeBody.clear();
                startGame();
                gameOver = false;
                gameLoop.start();
            }
       }
    }


    //do not need 
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
