package com.example.android_chicken_invaders.model.entities;

import com.example.android_chicken_invaders.model.GameConstants;
import com.example.android_chicken_invaders.model.ObstacleTypes;

public class Board {

    private static Board instance;

    private int rows;
    private int cols;
    private int gapIndex;
//    private double obstacleDensity;
    private ObstacleTypes[][] boardMatrix;

    private Board(int rows, int cols/*, double obstacleDensity*/) {
        this.rows = rows;
        this.cols = cols;
//        this.obstacleDensity = obstacleDensity;
        this.boardMatrix = new ObstacleTypes[rows][cols];
    }

    public static Board getInstance(int rows, int cols/*, double obstacleDensity*/) {
        if (instance == null)
            instance = new Board(rows, cols/*, obstacleDensity*/);

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


    public ObstacleTypes[][] getBoardMatrix() {
        return boardMatrix;
    }

    public Board setBoardMatrix(ObstacleTypes[][] boardMatrix) {
        this.boardMatrix = boardMatrix;
        return this;
    }

//    public double getObstacleDensity() {
//        return obstacleDensity;
//    }

//    public Board setObstacleDensity(int obstacleDensity) {
//        this.obstacleDensity = obstacleDensity;
//        return this;
//    }

    public ObstacleTypes[] getLastRow() {
        return this.boardMatrix[boardMatrix.length-1];
    }

    public void shiftDownObstacles() {
        if (getRows() - 1 >= 0) System.arraycopy(boardMatrix, 0, boardMatrix, 1, getRows() - 1);
    }

    public boolean newObstaclesRow() {
        int numCols = getCols();
        int randomCol;
        boolean isReward;

        ObstacleTypes[] newFirstRow = new ObstacleTypes[numCols];
        for (int i = 0; i < numCols; i++)
            newFirstRow[i] = ObstacleTypes.NONE;

        randomCol = (int) (Math.random() * numCols);
        isReward = Math.random() < GameConstants.REWARD_PROB;

        newFirstRow[randomCol] = isReward? ObstacleTypes.REWARD : ObstacleTypes.OBSTACLE;

        boardMatrix[0] = newFirstRow;
        return isReward;
    }


//    public boolean newObstaclesRow() {
//        int numCols = getCols();
//        int lastGapIndex = gapIndex;
//        boolean isObstacle = false;
//        ObstacleTypes[] newFirstRow = new ObstacleTypes[numCols];
//        gapIndex = (int) (Math.random() * numCols);
//
//        for (int i = 0; i < numCols; i++) {
//            if (i == gapIndex || i == lastGapIndex)
//                newFirstRow[i] = ObstacleTypes.NONE;
//            else {
//                newFirstRow[i] = (Math.random() < obstacleDensity) ? ObstacleTypes.OBSTACLE : ObstacleTypes.NONE;
//                isObstacle |= newFirstRow[i] == ObstacleTypes.NONE;
//            }
//        }
//
//        boardMatrix[0] = newFirstRow;
//        return isObstacle;
//    }

    public void clearObstacles() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.boardMatrix[i][j] = ObstacleTypes.NONE;
    }
}
