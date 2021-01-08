package eecs40.tetris;

public enum TetrisShape {
    BAR(new int[][]{{0,0,0,0},{1,1,1,1}}),
    BOX(new int[][]{{0,1,1,0},{0,1,1,0}}),
    T(new int[][]{{0,1,0,0},{1,1,1,0}}),
    S(new int[][]{{0,1,1,0},{1,1,0,0}}),    //S
    SR(new int[][]{{1,1,0,0},{0,1,1,0}}),   //S-reverse
    L(new int[][]{{0,0,1,0},{1,1,1,0}}),    //L
    LR(new int[][]{{1,1,1,0},{0,0,1,0}});   //L-reverse

    private int[][] shapeArray;

    TetrisShape(int[][] shapeArray){
        this.shapeArray = shapeArray;
    }

    public int[][] getShapeArray(){
        return shapeArray;
    }

    public static TetrisShape getRandomShape(){
        return TetrisShape.values()[new java.util.Random().nextInt(TetrisShape.values().length)];
    }

    @Override
    public String toString(){
        String toStr = "" + this.name() + " " + System.lineSeparator();
        for(int i = 0; i<this.shapeArray.length; i++){
            for(int j = 0; j<this.shapeArray[i].length; j++){
                toStr += this.shapeArray[i][j];
            }
            toStr += System.lineSeparator();
        }
        return toStr;
    }
}
