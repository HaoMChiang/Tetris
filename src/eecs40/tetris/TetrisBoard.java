package eecs40.tetris;

import eecs40.util.Utility;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TetrisBoard extends AbstractTetrisBoard{

    int[][] board;
    int height;
    int width;
    int tempHeight;
    Queue<TetrisShape> shapes = new LinkedList<>();
    ArrayList<TetrisObserver> observe = new ArrayList<>();
    int[][] currentShape = new int[4][4];
    int[][] tempBoard;
    boolean isShapeEmpty = true;
    ArrayList<int[]> movement = new ArrayList<>();
    int[] position = new int[2];

    @Override
    public void init(int height, int width) {
        this.height = height;
        this.width = width;
        tempHeight = height + 3;
        board = new int[height+1][width+2];
        tempBoard = new int[tempHeight+1][width+2];
        for(int i = 0; i<tempHeight+1; i++){
            for(int j = 0; j<width+2; j++){
                if(j==0 || j==width+1 || i==tempHeight){
                    tempBoard[i][j] = 2;
                    if(i >= 3){
                        board[i-3][j] = 2;
                    }
                } else{
                    tempBoard[i][j] = 0;
                    if(i >= 3) {
                        board[i-3][j] = 0;
                    }
                }
            }
        }
    }

    @Override
        public void addShape(TetrisShape s) {
            shapes.add(s);
            isShapeEmpty = shapes.isEmpty();
            if(!isShapeEmpty && needShape()){
                createShape(shapes.peek());
                if(canShapeAppear()){
                    appearShape();
                }
            }
        }

    @Override
    public void moveLeft() {
        if(canMoveLeft()){
            movingLeft();
        }
    }

    @Override
    public void moveRight() {
        if(canMoveRight()){
            movingRight();
        }
    }

    @Override
    public void moveDown() {
        if(canMoveDown()){
            movingDown();
            if(!canMoveDown()){
                for(int i = 0; i<tempHeight+1; i++){
                    for(int j = 0; j<width+2; j++){
                        if(tempBoard[i][j] == 3){
                            tempBoard[i][j] = 1;
                        }
                    }
                }
                updateBoard();
                if(canClearLine()){
                    clear();
                }
            }
        }
    }

    @Override
    public void moveDownToEnd() {
        while(canMoveDown() && !needShape()){
            moveDown();
        }
    }

    @Override
    public void rotateLeft() {
        if(canRotate() && !needShape()){
            rotating();
        }
    }

    @Override
    public int[][] getBoardArray() {
        return board;
    }

    @Override
    public void addObserver(TetrisObserver o) {
        observe.add(o);
    }

    @Override
    public void removeObserver(TetrisObserver o) {
        observe.remove(o);
    }

    public boolean canShapeAppear(){
        int startPosition = ((width-4)/2)+1;
        for(int i = 0; i<4; i++){
            if(currentShape[3][i] == 3 && tempBoard[3][startPosition+i] != 0){
                return false;
            }
        }
        return true;
    }

    public void appearShape(){
        int startPosition = ((width-4)/2)+1;
        for(int i = 2; i<4; i++){
            for(int j = 0; j<4; j++){
                if(currentShape[i][j] == 3) {
                    tempBoard[i][startPosition + j] = currentShape[i][j];
                }
            }
        }
        position[0] = 3;
        position[1] = startPosition;
        updateBoard();
    }

    public void createShape(TetrisShape shape){
        currentShape = new int[4][4];
        if(shape == TetrisShape.BOX){
            currentShape[2][1] = 3;
            currentShape[2][2] = 3;
            currentShape[3][1] = 3;
            currentShape[3][2] = 3;
        } else {
            int[][] temp = shape.getShapeArray();
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    if(temp[i][j] == 1){
                        currentShape[i+2][j] = 3;
                    }
                }
            }
        }
        shapes.poll();
    }

    public void updateBoard(){
        for(int i = 0; i<height+1; i++){
            for(int j = 0; j<width+2; j++){
                if(tempBoard[i+3][j] == 3 || tempBoard[i+3][j] == 1){
                    board[i][j] = 1;
                } else if(tempBoard[i+3][j] == 0){
                    board[i][j] = 0;
                } else if(tempBoard[i+3][j] == 2){
                    board[i][j] = 2;
                }
            }
        }
    }

    public void moving(ArrayList<int[]> movement){
        for(int[] position: movement){
            tempBoard[position[2]][position[3]] = 3;
            tempBoard[position[0]][position[1]] = 0;
        }
        updateBoard();
    }

    public boolean canMoveDown(){
        int[][] temp = new int[tempHeight+1][width+2];
        Utility.arrayCopy(this.tempBoard,temp);
        for(int i = tempHeight-1; i>=0; i--){
            for(int j = 1; j<=width; j++){
                if(temp[i][j]==3 && temp[i+1][j]==0){
                    temp[i+1][j]=3;
                    temp[i][j]=0;
                } else if(temp[i][j]==3 && temp[i+1][j] != 0){
                    return false;
                }
            }
        }
        return true;
    }

    public void movingDown(){
        int[] movement;
        for(int i = tempHeight-1; i>=0; i--){
            for(int j = 1; j<=width; j++){
                if(tempBoard[i][j]==3){
                    movement= new int[]{i, j, i + 1, j};
                    this.movement.add(movement);
                }
            }
        }
        moving(this.movement);
        position[0]++;
        this.movement.clear();
    }

    public boolean canMoveLeft(){
        int[][] temp = new int[tempHeight+1][width+2];
        Utility.arrayCopy(this.tempBoard, temp);
        for(int i = 0; i<tempHeight; i++){
            for(int j = 1; j<=width; j++){
                if(temp[i][j] == 3 && temp[i][j-1] == 0){
                    temp[i][j] = 0;
                    temp[i][j-1] = 3;
                }
                else if(temp[i][j] == 3 && temp[i][j-1] != 0){
                    return false;
                }
            }
        }
        return true;
    }

    public void movingLeft(){
        int[] movement;
        for(int i = 0; i<tempHeight; i++){
            for(int j = 1; j<=width; j++){
                if(tempBoard[i][j]==3){
                    movement= new int[]{i, j, i, j-1};
                    this.movement.add(movement);
                }
            }
        }
        moving(this.movement);
        position[1]--;
        this.movement.clear();
    }

    public boolean canMoveRight(){
        int[][] temp = new int[tempHeight+1][width+2];
        Utility.arrayCopy(this.tempBoard, temp);
        for(int i = 0; i<tempHeight; i++){
            for(int j = width; j>=1; j--){
                if(temp[i][j] == 3 && temp[i][j+1] == 0){
                    temp[i][j] = 0;
                    temp[i][j+1] = 3;
                }
                else if(temp[i][j] == 3 && temp[i][j+1] != 0){
                    return false;
                }
            }
        }
        return true;
    }

    public void movingRight(){
        int[] movement;
        for(int i = 0; i<tempHeight; i++){
            for(int j = width; j>=1; j--){
                if(tempBoard[i][j]==3){
                    movement= new int[]{i, j, i, j+1};
                    this.movement.add(movement);
                }
            }
        }
        moving(this.movement);
        position[1]++;
        this.movement.clear();
    }

    public int[][] rotateShape(){
        int[][] temp = new int[4][4];
        Utility.arrayCopy(currentShape,temp);
        for(int i = 0; i<4; i++){
            for(int j = 0; j<4; j++){
                temp[i][j] = currentShape[j][3-i];
            }
        }
        return temp;
    }

    public boolean canRotate(){
        int[][] temp = rotateShape();
        for(int i = 3; i>=0; i--){
            for(int j = 0; j<4; j++){
                if(temp[i][j] == 3 && tempBoard[position[0]-(3-i)][position[1]+j] != 0){
                    return false;
                }
            }
        }
        return true;
    }

    public void rotating(){
        currentShape = rotateShape();
        for(int i = 3; i>=0; i--){
            for(int j = 0; j<4; j++){
                if(currentShape[i][j] == 3 && tempBoard[position[0]-(3-i)][position[1]+j] == 0){
                    tempBoard[position[0]-(3-i)][position[1]+j] = 3;
                } else if(currentShape[i][j] == 0 && tempBoard[position[0]-(3-i)][position[1]+j] == 3){
                    tempBoard[position[0]-(3-i)][position[1]+j] = 0;
                }
            }
        }
        updateBoard();
    }

    public boolean needShape(){
        for(int i = 0; i<tempBoard.length; i++){
            for(int j = 0; j<tempBoard[0].length; j++){
                if(tempBoard[i][j] == 3){
                    return false;
                }
            }
        }
        return true;
    }

    public void clear(){
        int[] index = new int[tempHeight];
        int numLineToClear = 0;
        for(int i = tempHeight-1; i>=0; i--){
            boolean haveOnes = true;
            for(int j = 1; j<=width; j++){
                if(tempBoard[i][j] == 0){
                    haveOnes = false;
                    break;
                }
            }
            if(!haveOnes){
                index[numLineToClear] = i;
                numLineToClear++;
            }
        }
        int[][] updatedBlock = new int[numLineToClear][width];
        for(int i = 0; i<numLineToClear; i++){
            for(int j = 0; j<width; j++){
                updatedBlock[i][j] = tempBoard[index[numLineToClear-1-i]][j+1];
            }
        }
        int counter = 0;
        for(int i = tempHeight-1; i>=0; i--){
            for(int j = 1; j<=width; j++){
                if(counter < numLineToClear){
                    tempBoard[i][j] = updatedBlock[numLineToClear-1-counter][j-1];
                }
            }
            counter++;
        }
        updateBoard();
    }

    public boolean canClearLine(){
        for(int i = tempHeight-1; i>=0; i--){
            boolean haveOnes = true;
            for(int j = 1; j<=width; j++){
                if(tempBoard[i][j] == 0){
                    haveOnes = false;
                    break;
                }
            }
            if(haveOnes){
                return true;
            }
        }
        return false;
    }

}
