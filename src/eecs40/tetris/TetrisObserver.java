package eecs40.tetris;

public interface TetrisObserver {
    public void notifyLinesClear(int num);
    public void notifyNeedShape(TetrisBoard b);
    public void notifyGameOver(TetrisBoard b);
}
