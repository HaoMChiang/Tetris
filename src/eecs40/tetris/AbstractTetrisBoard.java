package eecs40.tetris;

public abstract class AbstractTetrisBoard {
    public abstract void init(int height, int width);
    public abstract void addShape(TetrisShape s);
    public abstract void moveLeft();
    public abstract void moveRight();
    public abstract void moveDown();
    public abstract void moveDownToEnd();
    public abstract void rotateLeft();
    public abstract int[][] getBoardArray();

    //Add or remove observer
    public abstract void addObserver(TetrisObserver o);
    public abstract void removeObserver(TetrisObserver o);

    //overridden toString() method
    public String toString(){
        String toStr = "";
        for(int i = 0; i<this.getBoardArray().length; i++){
            for(int j = 0; j<this.getBoardArray()[i].length;j++){
                toStr += this.getBoardArray()[i][j];
            }
            toStr += System.lineSeparator();
        }
        return toStr;
    }
}
