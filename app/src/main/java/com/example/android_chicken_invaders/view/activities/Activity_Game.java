package com.example.android_chicken_invaders.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.android_chicken_invaders.R;
import com.example.android_chicken_invaders.interfaces.MovementCallback;
import com.example.android_chicken_invaders.model.constants.AppConstants;
import com.example.android_chicken_invaders.model.constants.GameConstants;
import com.example.android_chicken_invaders.model.GameManager;
import com.example.android_chicken_invaders.model.RecordsListMng;
import com.example.android_chicken_invaders.model.eObstacleTypes;
import com.example.android_chicken_invaders.model.entities.GameRecord;
import com.example.android_chicken_invaders.model.entities.ObstacleType;
import com.example.android_chicken_invaders.sensor.MovementSensor;
import com.example.android_chicken_invaders.utils.MyScreenUtils;
import com.example.android_chicken_invaders.utils.MySignal;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;
import java.util.Random;

public class Activity_Game extends AppCompatActivity {

    private AppCompatImageView game_IMG_back;
    private AppCompatImageView[] game_IMG_player;
    private AppCompatImageView[] game_IMG_player_crash;
    private AppCompatImageView[] game_IMG_opponent;
    private AppCompatImageView[] game_IMG_lives;
    private AppCompatImageView[][] game_IMG_obstacles;
    private CardView game_LAY_gameOver;
    private MaterialButton game_BTN_right;
    private MaterialButton game_BTN_left;
    private MaterialButton game_BTN_scoreBoard;
    private MaterialButton game_BTN_mainPage;
    private MaterialTextView game_LBL_score;
    private AppCompatEditText game_EDT_name;
    private MaterialTextView game_LBL_finalScore;

    private Handler timerHandler;
    private Runnable timerRunnable;
    private int timerDelay;

    private MovementSensor movementSensor;

    private boolean isGameOver = false;
    private boolean isFirstGame = true;
    private boolean isFastMode = false;
    private boolean isSensorMode = false;
    double lat;
    double lng;

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        MyScreenUtils.hideSystemUI(this);

        initBundleData();
        findViews();
        initUIObjects();
        initButtonsListeners();
        initMovementMode();
        initBoardManager();

        startGame();
    }

    private void initBundleData() {
        Bundle bundle = getIntent().getBundleExtra(AppConstants.KEY_BUNDLE);

        isSensorMode = bundle.getBoolean(AppConstants.KEY_SENSOR, false);
        isFastMode = bundle.getBoolean(AppConstants.KEY_FAST, false);
        lat = bundle.getDouble(AppConstants.KEY_LAT, 31.777109717423652);
        lng = bundle.getDouble(AppConstants.KEY_LNG, 35.23454639997845);

        timerDelay = GameConstants.TIMER_MS_BASE;

        if (isFastMode)
            timerDelay /= GameConstants.TIMER_FAST_FACTOR;
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
        if (isSensorMode)
            movementSensor.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopTimer();
        if (isSensorMode)
            movementSensor.stop();
    }

    private void findViews() {
        game_IMG_lives = new AppCompatImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3),
        };
        game_IMG_player = new AppCompatImageView[]{
                findViewById(R.id.game_IMG_player_1),
                findViewById(R.id.game_IMG_player_2),
                findViewById(R.id.game_IMG_player_3),
                findViewById(R.id.game_IMG_player_4),
                findViewById(R.id.game_IMG_player_5),
        };
        game_IMG_player_crash = new AppCompatImageView[]{
                findViewById(R.id.game_IMG_player_crash_1),
                findViewById(R.id.game_IMG_player_crash_2),
                findViewById(R.id.game_IMG_player_crash_3),
                findViewById(R.id.game_IMG_player_crash_4),
                findViewById(R.id.game_IMG_player_crash_5),
        };
        game_IMG_opponent = new AppCompatImageView[]{
                findViewById(R.id.game_IMG_opponent_1),
                findViewById(R.id.game_IMG_opponent_2),
                findViewById(R.id.game_IMG_opponent_3),
                findViewById(R.id.game_IMG_opponent_4),
                findViewById(R.id.game_IMG_opponent_5),
        };
        game_IMG_obstacles = new AppCompatImageView[][]{
                {findViewById(R.id.game_IMG_1_1), findViewById(R.id.game_IMG_1_2), findViewById(R.id.game_IMG_1_3), findViewById(R.id.game_IMG_1_4), findViewById(R.id.game_IMG_1_5)},
                {findViewById(R.id.game_IMG_2_1), findViewById(R.id.game_IMG_2_2), findViewById(R.id.game_IMG_2_3), findViewById(R.id.game_IMG_2_4), findViewById(R.id.game_IMG_2_5)},
                {findViewById(R.id.game_IMG_3_1), findViewById(R.id.game_IMG_3_2), findViewById(R.id.game_IMG_3_3), findViewById(R.id.game_IMG_3_4), findViewById(R.id.game_IMG_3_5)},
                {findViewById(R.id.game_IMG_4_1), findViewById(R.id.game_IMG_4_2), findViewById(R.id.game_IMG_4_3), findViewById(R.id.game_IMG_4_4), findViewById(R.id.game_IMG_4_5)},
                {findViewById(R.id.game_IMG_5_1), findViewById(R.id.game_IMG_5_2), findViewById(R.id.game_IMG_5_3), findViewById(R.id.game_IMG_5_4), findViewById(R.id.game_IMG_5_5)},
                {findViewById(R.id.game_IMG_6_1), findViewById(R.id.game_IMG_6_2), findViewById(R.id.game_IMG_6_3), findViewById(R.id.game_IMG_6_4), findViewById(R.id.game_IMG_6_5)},
                {findViewById(R.id.game_IMG_player_egg_1), findViewById(R.id.game_IMG_player_egg_2), findViewById(R.id.game_IMG_player_egg_3), findViewById(R.id.game_IMG_player_egg_4), findViewById(R.id.game_IMG_player_egg_5)}
        };

        game_IMG_back = findViewById(R.id.game_IMG_back);
        game_BTN_right = findViewById(R.id.game_BTN_right);
        game_BTN_left = findViewById(R.id.game_BTN_left);
        game_BTN_scoreBoard = findViewById(R.id.game_BTN_scoreBoard);
        game_BTN_mainPage = findViewById(R.id.game_BTN_mainPage);
        game_LBL_score = findViewById(R.id.game_LBL_score);
        game_LAY_gameOver = findViewById(R.id.game_LAY_gameOver);
        game_EDT_name = findViewById(R.id.game_EDT_name);
        game_LBL_finalScore = findViewById(R.id.game_LBL_finalScore);
    }

    private void initUIObjects() {
        initBackground();
        initGifAnimation();
    }

    private void initButtonsListeners() {
        game_BTN_left.setOnClickListener(v -> moveLeft());
        game_BTN_right.setOnClickListener(v -> moveRight());
        game_BTN_scoreBoard.setOnClickListener(v -> gotoActivityScoreBoard());
        game_BTN_mainPage.setOnClickListener(v -> finish());
    }

    private void initBackground() {
        int[] backgrounds = {
                R.drawable.img_bck_1,
                R.drawable.img_bck_2,
                R.drawable.img_bck_3,
                R.drawable.img_bck_4
        };

        Glide.with(Activity_Game.this)
                .load(backgrounds[new Random().nextInt(backgrounds.length)])
                .into(game_IMG_back);
    }

    private void initGifAnimation() {
        for (int i = 0; i < game_IMG_opponent.length; i++) {
            if (i % 2 == 0)
                Glide.with(Activity_Game.this)
                        .load(R.drawable.gif_chicken_red)
                        .into(game_IMG_opponent[i]);
            else
                Glide.with(Activity_Game.this)
                        .load(R.drawable.gif_chicken_blue)
                        .into(game_IMG_opponent[i]);
        }

        for (AppCompatImageView game_img_player : game_IMG_player)
            Glide.with(Activity_Game.this)
                    .load(R.drawable.gif_rocket1)
                    .into(game_img_player);
    }

    private void initMovementMode() {
        if (isSensorMode) {
            initSensor();
            game_BTN_left.setVisibility(View.INVISIBLE);
            game_BTN_right.setVisibility(View.INVISIBLE);
        } else {
            game_BTN_left.setVisibility(View.VISIBLE);
            game_BTN_right.setVisibility(View.VISIBLE);
        }
    }

    private void initSensor() {
        movementSensor = new MovementSensor(this, new MovementCallback() {
            @Override
            public void movePlayerLeft() { moveLeft(); }

            @Override
            public void movePlayerRight() { moveRight(); }
        });
    }

    private void initBoardManager() {
        GameManager.init(GameConstants.ROWS, GameConstants.COLS);
        gameManager = GameManager.getGameManager();
    }

    private void startGame() {
        game_BTN_right.setEnabled(true);
        game_BTN_left.setEnabled(true);
        game_LAY_gameOver.setVisibility(View.GONE);

        gameManager.restartGameParameters();
        isGameOver = false;

        if (!isFirstGame) {
            stopTimer();
            startTimer();
        } else
            isFirstGame = false;

        updateLivesUI();
        MySignal.getInstance().sound(R.raw.msg_new_game);
    }

    private void startTimer() {
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                timerHandler.postDelayed(this, timerDelay);
                timerIteration();
            }
        };

        timerHandler.postDelayed(timerRunnable, timerDelay);
    }

    private void stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    public void timerIteration() {
        boolean isNewReward;
        resetCrashUI();

        isNewReward = gameManager.updateGameBoard();
        gameManager.increaseScore();
        updateBoardUI();

        if (isNewReward)
            playNewObstacleSound();

        checkCrashReward();
        updatePlayerPositionUI();
        updateScoreUI();
    }

    private void playNewObstacleSound() {
        MySignal.getInstance().sound(R.raw.msc_chicken);
    }

    private void checkCrashReward() {
        if (gameManager.isReward()) {
            gameManager.increaseScoreBy(gameManager.getCrashedReward().getScoreValue());
            gameManager.increaseLivesBy(gameManager.getCrashedObstacle().getLivesValue());
            updateLivesUI();
            updateScoreUI();
            updatePlayerRewardUI();
            return;
        }

        if (gameManager.isCrashed()) {
            gameManager.reduceLivesBy(gameManager.getCrashedObstacle().getLivesValue());
            gameManager.reduceScoreBy(gameManager.getCrashedReward().getScoreValue());
            isGameOver = gameManager.isGameOver();
            updateLivesUI();
            updateScoreUI();
            updatePlayerCrashUI();

            if (isGameOver)
                gameOver();
        }
    }

    private void updatePlayerCrashUI() {
        int resourceId = getResources().getIdentifier(gameManager.getCrashedObstacle().getImpactDrawableRef(), "drawable", this.getPackageName());

        game_IMG_player_crash[gameManager.getPlayerPosition()].setVisibility(View.VISIBLE);

        Glide.with(Activity_Game.this)
                .load(resourceId)
                .into(game_IMG_player_crash[gameManager.getPlayerPosition()]);

        if (!isGameOver)
            new Handler().postDelayed(() -> game_IMG_player_crash[gameManager.getPlayerPosition()].setVisibility(View.INVISIBLE), timerDelay);

        if (isGameOver)
            MySignal.getInstance().sound(R.raw.msc_game_over);
        else
            MySignal.getInstance().sound(R.raw.msc_crash);

        MySignal.getInstance().vibrate(500);
    }

    private void updatePlayerRewardUI() {
        int resourceId = getResources().getIdentifier(gameManager.getCrashedReward().getImpactDrawableRef(), "drawable", this.getPackageName());

        game_IMG_player_crash[gameManager.getPlayerPosition()].setVisibility(View.VISIBLE);

        Glide.with(Activity_Game.this)
                .load(resourceId)
                .into(game_IMG_player_crash[gameManager.getPlayerPosition()]);

        new Handler().postDelayed(() -> game_IMG_player_crash[gameManager.getPlayerPosition()].setVisibility(View.INVISIBLE), timerDelay);

        MySignal.getInstance().sound(R.raw.msc_earned);
    }

    private void resetCrashUI() {
        if (isGameOver)
            return;

        for (AppCompatImageView game_img_player_crash : game_IMG_player_crash) {
            game_img_player_crash.setVisibility(View.INVISIBLE);
        }
    }

    private void updateBoardUI() {
        ObstacleType[][] boardObstacles = gameManager.getObstacles();

        for (int i = 0; i < boardObstacles.length; i++) {
            for (int j = 0; j < boardObstacles[i].length; j++) {
                updateObstacleGifUI(i, j, boardObstacles[i][j]);
                if (i == boardObstacles.length - 1)
                    if (j != gameManager.getPlayerPosition())
                        updateObstacleGifUI(i, j, boardObstacles[i][j]);
            }
        }
    }

    private void updateObstacleGifUI(int i, int j, ObstacleType obstacle) {
        int resourceId = 0;
        if (obstacle.getType() != eObstacleTypes.NONE) {
            resourceId = getResources().getIdentifier(obstacle.getDrawableRef(), "drawable", this.getPackageName());
        }

        switch (obstacle.getType()) {
            case NONE:
                game_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
                break;
            case OBSTACLE:
                game_IMG_obstacles[i][j].setVisibility(View.VISIBLE);
                game_IMG_obstacles[i][j].setImageResource(resourceId);
                break;
            case REWARD:
                game_IMG_obstacles[i][j].setVisibility(View.VISIBLE);
                Glide.with(Activity_Game.this)
                        .load(resourceId)
                        .into(game_IMG_obstacles[i][j]);
                break;
        }

        if (gameManager.isObstacleAtPositionByBSP(i, j) && obstacle.getType() == eObstacleTypes.OBSTACLE) {
            gameManager.reduceScoreBy(5);
            game_IMG_obstacles[i][j].setImageResource(R.drawable.bomb);
        }
    }

    private void updatePlayerPositionUI() {
        for (int i = 0; i < game_IMG_player.length; i++)
            game_IMG_player[i].setVisibility(i == gameManager.getPlayerPosition() ? View.VISIBLE : View.INVISIBLE);
    }

    private void updateLivesUI() {
        for (int i = 0; i < gameManager.getLives(); i++)
            game_IMG_lives[i].setVisibility(View.VISIBLE);

        for (int i = gameManager.getLives(); i < game_IMG_lives.length; i++)
            game_IMG_lives[i].setVisibility(View.INVISIBLE);
    }

    private void updateScoreUI() {
        String scoreText = String.valueOf(gameManager.getScore());
        game_LBL_score.setText(scoreText);
    }

    private void moveRight() {
        if (isGameOver)
            return;

        gameManager.movePlayerRight();

        checkCrashReward();
        updatePlayerPositionUI();
    }

    private void moveLeft() {
        if (isGameOver)
            return;

        gameManager.movePlayerLeft();

        checkCrashReward();
        updatePlayerPositionUI();
    }

    private void gameOver() {
        stopTimer();
        if (isSensorMode)
            movementSensor.stop();

        String scoreText = game_LBL_finalScore.getText() + (String.valueOf(gameManager.getScore()));
        game_BTN_right.setEnabled(false);
        game_BTN_left.setEnabled(false);
        game_LAY_gameOver.setVisibility(View.VISIBLE);
        game_LBL_finalScore.setText(scoreText);
    }

    private void gotoActivityScoreBoard() {
        if (Objects.requireNonNull(game_EDT_name.getText()).toString().isEmpty())
            MySignal.getInstance().toast("Please enter a name");
        else {
            GameRecord record = new GameRecord()
                    .setUserId(game_EDT_name.getText().toString())
                    .setScore(gameManager.getScore())
                    .setLat(this.lat)
                    .setLng(this.lng);

            RecordsListMng.getInstance().addRecord(record);
            Intent intent = new Intent(this, Activity_ScoreBoard.class);
            startActivity(intent);
            finish();
        }
    }
}