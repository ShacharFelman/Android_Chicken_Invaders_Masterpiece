package com.example.android_chicken_invaders.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.android_chicken_invaders.interfaces.MovementCallback;

public class MovementSensor {

    private final SensorManager sensorManager;
    private final Sensor sensor;
    private SensorEventListener sensorEventListener;

    private final MovementCallback movementCallback;

    private long timestamp = 0;
    private int responseTime = 200;



    public MovementSensor(Context context, MovementCallback movementCallback) {
        this.movementCallback = movementCallback;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                calculateStep(x);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }

    private void calculateStep(float x) {
        if(System.currentTimeMillis() - timestamp > responseTime){
            timestamp = System.currentTimeMillis();
            if(x > 3.0){
                if(movementCallback != null)
                    movementCallback.movePlayerLeft();
            }
            if(x < -3.0){
                if(movementCallback != null)
                    movementCallback.movePlayerRight();
            }
        }
    }

    public void start(){
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

}
