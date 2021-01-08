package eecs40.test;

import eecs40.tetris.TetrisBoard;
import eecs40.tetris.TetrisShape;

public class Test {
    public static void main(String[] args){
        TetrisBoard tb = new TetrisBoard();
        tb .init( 6 , 6 );
        System . out .println( tb .toString());
// will print out
/*
20000002
20000002
20000002
20000002
20000002
20000002
22222222
*/
        tb .addShape( TetrisShape . BOX );
        tb .moveDownToEnd();
        System . out .println( tb .toString());
        /*


20000002
20000002
20000002
20000002
20011002
20011002
22222222
Starting = (6-4)/2 = 1
         */
        tb .addShape( TetrisShape . S );
        tb .moveDown();
/*
20011002
20110002
20000002
20000002
20011002
20011002
22222222
*/
        tb .moveRight();
        System . out .println( tb .toString());
/*

20001102
20011002
20000002
20000002
20011002
20011002
22222222
*/
        tb .moveDownToEnd();
        tb .addShape( TetrisShape . T );
        tb .moveLeft();
        tb .moveDown();

/*

20100002
21110002
20001102
20011002
20011002
20011002
22222222
*/
        tb .moveDownToEnd();
        tb .addShape( TetrisShape . T );
        tb .moveRight();
        tb .rotateLeft();
        tb .moveDownToEnd(); // one line will disappear
        System . out .println( tb .toString());
/*

20000002
20000002
20000002
20011002
20011012
20111112
22222222
*/

        tb .addShape( TetrisShape . BAR );
        tb .moveDownToEnd();
        System . out .println( tb .toString());
/*

20000002
20000002
20111102
20011002
20011012
20111112
22222222
*/
        tb .addShape( TetrisShape . BOX );
        tb .moveDownToEnd();
        System . out .println( tb .toString());
 /*

20011002
20011002
20111102
20011002
20011012
20111112
22222222
*/
        tb .addShape( TetrisShape . BOX );
        tb .moveDown(); // should be ignored because it is stacked already.
        System . out .println( tb .toString());
    }
}

