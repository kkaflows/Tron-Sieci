package project.model;

import java.io.Serializable;

/**
 * Created by Lenovo on 2017-05-11.
 */
public class DataPackage implements Serializable {
    private int x, y;
    private int dirX, dirY;
    private boolean[][] board;

    public DataPackage(int x, int y, int dirX, int dirY) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
        this.board = new boolean[400][400];
        clearBoard();
    }

    private void clearBoard(){
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                board[i][j] = false;
            }
        }
    }

    public void nextStep(){
        x +=dirX;
        y +=dirY;
    }

    public void goUp(){
        dirX = 0;
        dirY = -1;
    }

    public  void goRight(){
        dirX = 1;
        dirY = 0;
    }

    public void goDown(){
        dirX = 0;
        dirY = 1;
    }
    public void goLeft(){
        dirX = -1;
        dirY = 0;
    }

    public boolean[][] getBoard() {
        return board;
    }

    public void setBoard(boolean[][] board) {
        this.board = board;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirX() {
        return dirX;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public int getDirY() {
        return dirY;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    public void setBoard(int x, int y) {
        board[x][y] = true;
    }
}
