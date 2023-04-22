package com.example.android_chicken_invaders.model;

public class GameManager {
    private static GameManager gameManager;

    private final static double RANDOM_DENSITY = 0.4;

    private int rows;
    private int cols;
    private final Board board;
    private int lives;
    private int playerPosition;

    private GameManager(int rows, int cols) {
        setRows(rows);
        setCols(cols);
        board = Board.getInstance(getRows(), getCols(), RANDOM_DENSITY);
    }

    public static void init(int rows, int cols) {
        if (gameManager == null) {
            gameManager = new GameManager(rows, cols);
            gameManager.setRows(rows);
            gameManager.setCols(cols);
        }
    }

    public static GameManager getGameManager() {
        return gameManager;
    }

    public int getCols() {
        return cols;
    }

    private void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    private void setRows(int rows) {
        this.rows = rows;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    public boolean isObstacleAtPosition(int i, int j) {
        return board.getBoardMatrix()[i][j] == ObstacleTypes.OBSTACLE;
    }

    public boolean isCrashed() {
        return isObstacleAtPosition(getRows() - 1, getPlayerPosition());
    }

    public void resetBoard() {
        board.clearObstacles();
    }

    public boolean updateGameBoard() {
        board.shiftDownObstacles();
        return board.newObstaclesRow();
    }

    public void reduceLives() {
        if(lives > 0)
            lives--;
    }

    public boolean isGameOver() {
        return lives == 0;
    }

    public void movePlayerRight() {
        if(playerPosition < getCols() - 1)
            playerPosition++;
    }

    public void movePlayerLeft() {
        if(playerPosition > 0)
            playerPosition--;
    }

    public ObstacleTypes[][] getObstacles() {
        return board.getBoardMatrix();
    }
}
