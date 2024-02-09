package com.example.android_chicken_invaders.model.entities;

import android.util.Log;

import com.example.android_chicken_invaders.model.ObstacleTypeSingleton;
import com.example.android_chicken_invaders.model.constants.GameConstants;
import com.example.android_chicken_invaders.model.eObstacleTypes;

public class Board {

    private static Board instance;

    private int rows;
    private int cols;
    private ObstacleType[][] boardMatrix;

    private Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.boardMatrix = new ObstacleType[rows][cols];
    }

    public static Board getInstance(int rows, int cols) {
        if (instance == null)
            instance = new Board(rows, cols);

        return instance;
    }

    public int getRows() {
        return rows;
    }

    public Board setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public int getCols() {
        return cols;
    }

    public Board setCols(int cols) {
        this.cols = cols;
        return this;
    }


    public ObstacleType[][] getBoardMatrix() {
        return boardMatrix;
    }

    public Board setBoardMatrix(ObstacleType[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
        return this;
    }

    public ObstacleType[] getLastRow() {
        return this.boardMatrix[boardMatrix.length-1];
    }

    public void shiftDownObstacles() {
        int numCols = getCols();

        System.arraycopy(boardMatrix, 0, boardMatrix, 1, getRows() - 1);
        boardMatrix[0] = getEmptyRow();
    }

    private ObstacleType[] getEmptyRow() {
        int numCols = getCols();

        ObstacleType[] newFirstRow = new ObstacleType[numCols];
        for (int i = 0; i < numCols; i++)
            newFirstRow[i] = ObstacleTypeSingleton.getInstance().getNone();

        return newFirstRow;
    }

    public boolean newObstaclesRow() {
        int numCols = getCols();
        int randomCol;
        boolean isReward;

        ObstacleType[] newFirstRow = new ObstacleType[numCols];
        for (int i = 0; i < numCols; i++)
            newFirstRow[i] = ObstacleTypeSingleton.getInstance().getNone();

        randomCol = (int) (Math.random() * numCols);
        isReward = Math.random() < GameConstants.REWARD_PROB;

        if(isReward)
            newFirstRow[randomCol] = ObstacleTypeSingleton.getInstance().getRandomObstacleType(eObstacleTypes.REWARD);
        else
            newFirstRow[randomCol] = ObstacleTypeSingleton.getInstance().getRandomObstacleType(eObstacleTypes.OBSTACLE);


        boardMatrix[0] = newFirstRow;
        Log.d("board", getObstacleRowStr(newFirstRow));

        return isReward;
    }

    public void clearObstacles() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.boardMatrix[i][j] = ObstacleTypeSingleton.getInstance().getNone();
    }

    public void printObstacleMatrix(ObstacleType[][] matrix) {
        StringBuilder str = new StringBuilder();
        for (ObstacleType[] obstacleTypes : matrix) {
            str.append(getObstacleRowStr(obstacleTypes));
            str.append("\n");
        }
        Log.d("board", str.toString());
    }

    public String getObstacleRowStr(ObstacleType[] row) {
        StringBuilder str = new StringBuilder();
        for (ObstacleType obstacleTypes : row) str.append(obstacleTypes.getName()).append(" ");

        return str.toString();
    }
}
