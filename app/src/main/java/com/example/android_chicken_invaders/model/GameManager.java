package com.example.android_chicken_invaders.model;

import com.example.android_chicken_invaders.model.entities.Board;

public class GameManager {
    private static GameManager gameManager;

    private int rows;
    private int cols;
    private final Board board;
    private int lives;
    private int playerPosition;
    private int score;
    private boolean addObstacle = true;

    private GameManager(int rows, int cols) {
        setRows(rows);
        setCols(cols);
        board = Board.getInstance(getRows(), getCols()/*, GameConstants.RANDOM_DENSITY*/);
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

    private void setLives(int lives) {
        this.lives = lives;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public boolean isObstacleAtPosition(int i, int j) {
        return board.getBoardMatrix()[i][j] == ObstacleTypes.OBSTACLE;
    }

    public boolean isCrashed() {
        return board.getBoardMatrix()[getRows() - 1][getPlayerPosition()] == ObstacleTypes.OBSTACLE;
    }

    public boolean isReward() {
        return board.getBoardMatrix()[getRows() - 1][getPlayerPosition()] == ObstacleTypes.REWARD;
    }

    private void resetBoard() {
        board.clearObstacles();
    }

    public boolean updateGameBoard() {
        board.shiftDownObstacles();
        if(addNewObstacleRow())
            return board.newObstaclesRow();

        return false;
    }

    public boolean addNewObstacleRow() {
        this.addObstacle = !this.addObstacle;
        return this.addObstacle;
    }

    public void reduceLives() {
        if(lives > 0)
            lives--;
    }

    public boolean isGameOver() {
        return lives == 0;
    }

    public void restartGameParameters() {
        setLives(GameConstants.INITIAL_LIVES_COUNT);
        setPlayerPosition(GameConstants.INITIAL_PLAYER_POSITION);
        this.addObstacle = true;
        setScore(0);
        resetBoard();
    }

    public void increaseScore() {
        score++;
    }

    public void increaseScoreBy(int amount) {
        score += amount;
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