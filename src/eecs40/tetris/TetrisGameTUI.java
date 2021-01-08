package eecs40.tetris;

import javax.swing.*;
import java.io.IOException;

import static eecs40.util.Utility.clearConsole;

public class TetrisGameTUI extends Thread implements TetrisObserver{
    TetrisBoard tb;
    String msg = "";
    public TetrisGameTUI(int height, int width){
        tb = new TetrisBoard();
        tb.init(height,width);
    }
    public void left(){
        tb.moveLeft();
    }
    public void right(){
        tb.moveRight();
    }
    public void down(){
        tb.moveDown();
    }
    public void downToEnd(){
        tb.moveDownToEnd();
    }
    public void rotate(){
        tb.rotateLeft();
    }
    public void showBoard(){
        clearConsole();
        String boardString = tb.toString();
        boardString = boardString.replace('2','\u25A0');
        boardString = boardString.replace('1','☐');
        boardString = boardString.replace('0',' ');
        System.out.println(boardString + msg);
    }
    @Override
    public void run(){
//        tb.addShape(TetrisShape.getRandomShape());

        while(true){
            tb.addShape(TetrisShape.getRandomShape());
            showBoard();
            tb.moveDown();
            try{
                Thread.sleep(200);
            }catch(InterruptedException ie){
                System.err.println("Interrupted...");
            }
        }
    }
    public static void main(String[] args) throws IOException{
        TetrisGameTUI tg = new TetrisGameTUI(20,20);
        tg.tb.addObserver(tg);

        tg.start();
        JFrame jf = new JFrame("keyboard utility");
        jf.addKeyListener(new java.awt.event.KeyAdapter(){
            @Override
            public void keyPressed(java.awt.event.KeyEvent e){
                if(e.getKeyCode() == 37)    //left key
                    tg.left();
                if(e.getKeyCode() == 39)    //right key
                    tg.right();
                if(e.getKeyCode() == 40)    //down key
                    tg.down();
                if(e.getKeyCode() == 38)    //up key
                    tg.rotate();
                if(e.getKeyCode() == 32)    //space key
                    tg.downToEnd();
                if(e.getKeyCode() == 27)    //esc key
                    System.exit(0);
            }
        });
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }

    @Override
    public void notifyLinesClear(int num){
        if(num == 4){
            msg = "Tetris!";
        } else {
            msg = num + " lines cleared";
        }
    }

    @Override
    public void notifyNeedShape(TetrisBoard b){
        b.addShape(TetrisShape.getRandomShape()); //give random shape
    }

    @Override
    public void notifyGameOver(TetrisBoard b){
        showBoard(); //last update
        System.out.println("Game Over");
        System.exit(1);
    }
}
