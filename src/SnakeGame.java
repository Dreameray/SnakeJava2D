import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    
    enum GameState{
        MENU, CLASSIC, POWERUP
    }

    GameState gameState = GameState.MENU;
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
    int powerUpType = 0;
    
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
        gameLoop.setDelay(100);
        snakeHead = new Tile(5, 5);
        snakeBody.clear();
        velocityX  = 0;
        velocityY = 0;
        gameOver = false;

        if(gameState == GameState.POWERUP){
            placeFood();
        }
        else if(gameState == GameState.CLASSIC){
            placeFood();
        }

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

        if(gameState == GameState.MENU){
            System.out.println("Game State is Menu");
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Snake Game", boardWidth/2 -75, boardHeight/2 - 50);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Press 1 for Classic Mode", boardWidth / 2 -75, boardHeight / 2);
            g.drawString("Press 2 for Power-Up Mode", boardWidth / 2 -75, boardHeight / 2 + 25);
        } else {
            //Food
            switch (gameState) {
                case CLASSIC:
                    g.setColor(Color.RED);
                    g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);
                    break;
                case POWERUP:
                    if(powerUpType == 0){
                        g.setColor(Color.CYAN); // Different color for speed-up power-up
                    } else if(powerUpType == 1) {
                        g.setColor(Color.MAGENTA); // Different color for triple growth power-up
                    } else {
                        g.setColor(Color.YELLOW); // Default color for other power-ups
                    }
                    g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);    
                    break;
                default:
                    g.setColor(Color.RED);
                    g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);
                    break;
            }
            

            //Snake Head
            g.setColor(Color.GREEN);
            g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize,true);

            //Snake Body
            for(int i = 0; i<snakeBody.size(); i++){
                Tile snakePart = snakeBody.get(i);
                g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
            }

            //Score
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            
            if(gameOver){   
                g.setColor(Color.red);
                g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
                g.drawString( "Press SPACE BAR to Restart", tileSize - 16, tileSize + 20);
                g.drawString( "Press ESCAPE to Return to Menu", tileSize - 16, tileSize + 40);
            } else{
                g.drawString("Score: " + String.valueOf(snakeBody.size()),tileSize - 16, tileSize);

            }

            //High Score
            g.setColor(Color.WHITE);
            if(snakeBody.size()>highScore){
                g.drawString("Highest Score: " + String.valueOf(snakeBody.size()), boardWidth - 150, tileSize);
            } else{
                g.drawString("Highest Score: " + highScore, boardWidth - 150, tileSize);
            }
            
            }

    }

    public void placeFood(){
       food.x = random.nextInt(boardWidth/tileSize);
       food.y = random.nextInt(boardHeight/tileSize);
    }
    
    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        if (collision(snakeHead, food)) {
            if (gameState == GameState.POWERUP) {
                powerUpType = random.nextInt(3); // Random power-up type
                System.out.println("Power-Up Type: " + powerUpType);
                
                if (powerUpType == 0) {
                    // Speed up
                    System.out.println("Speed Up!");
                    //gameLoop.setDelay(Math.max(50, gameLoop.getDelay() - 10));
                    gameLoop.setDelay(50);
                    // Still add one tail segment
                    snakeBody.add(new Tile(food.x, food.y));
                } else if (powerUpType == 1) {
                    // Add extra tails (3 segments)
                    System.out.println("Triple Growth!");
                    //gamespeed set to normal
                    gameLoop.setDelay(100);
                    // Add three segments to the snake
                    for (int i = 0; i < 3; i++) {
                        snakeBody.add(new Tile(food.x, food.y));
                    }
                }else if (powerUpType == 2) {
                    // Add extra tails (3 segments)
                    System.out.println("Reductional!");
                    //gamespeed set to normal
                    gameLoop.setDelay(100);
                    // Remove one segment from the snake
                    if (!snakeBody.isEmpty()) {
                        snakeBody.remove(snakeBody.size() - 1);
                    }
                } else {
                    // Classic mode: just grow the snake by one segment
                    snakeBody.add(new Tile(food.x, food.y));
                }
                placeFood();
            } else {

                snakeBody.add(new Tile(food.x, food.y));
                placeFood();
            }
        }

        // Snake body movement
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Game Over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // collide with snake head
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || 
            snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight) {
            gameOver = true;
        }

        if (snakeHead.x < 0 || snakeHead.x >= boardWidth/tileSize || 
            snakeHead.y < 0 || snakeHead.y >= boardHeight/tileSize) {
            gameOver = true;
        }

        // Update the highest score
        if (gameOver) {
            if (snakeBody.size() > highScore) {
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

        if(gameState == GameState.MENU){
            if (e.getKeyCode() == KeyEvent.VK_1) {
                gameState = GameState.CLASSIC;
                startGame();
            } else if (e.getKeyCode() == KeyEvent.VK_2) {
                gameState = GameState.POWERUP;
                startGame();
            }

        }else{
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
           }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                if(gameOver){
                    snakeBody.clear();
                    startGame();
                    gameOver = false;
                    gameLoop.start();
                    gameState = GameState.MENU;
                }
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
