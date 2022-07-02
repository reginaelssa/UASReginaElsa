package com.example.uas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class Activity2 extends AppCompatActivity {
    protected SensorManager SM;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;
    protected TextView value_azimuth;
    protected TextView value_pitch;
    protected TextView value_roll;
    protected String SQLiteQuery;
    protected float azimuth,pitch,roll;
    protected SQLiteDatabase sqLiteDatabase;
    private static final float VALUE_DRIFT = 0.05f;
    private float[] mAccelerometerData = new float[3];
    private float[] mMagnetometerData = new float[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        value_azimuth = findViewById(R.id.value_azimuth);
        value_pitch = findViewById(R.id.value_pitch);
        value_roll = findViewById(R.id.value_roll);

        SM = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorAccelerometer == null && mSensorMagnetometer == null){
            if (SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null &&
                    SM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
                createDatabase();
                int MINUTES = 2; // The delay in minutes
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        addData(); // If the function you wanted was static
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(
                                new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        Toast.makeText(Activity2.this, "Data berhasil ditambah", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }
                }, 0, 1000 * 60 * MINUTES);
                mSensorAccelerometer = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorMagnetometer = SM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            } else{
                value_pitch.setText("SmartPhone anda tidak mendukung");
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (mSensorAccelerometer != null) {
            SM.registerListener(orientationListener, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetometer != null) {
            SM.registerListener(orientationListener, mSensorMagnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onStop() {
        super.onStop();
        SM.unregisterListener(orientationListener);;
    }

    SensorEventListener orientationListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) { }
        @SuppressLint("SetTextI18n")
        public void onSensorChanged(SensorEvent event) {
            int sensorType = event.sensor.getType();

            switch (sensorType) {
                case Sensor.TYPE_ACCELEROMETER:
                    mAccelerometerData = event.values.clone();
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    mMagnetometerData = event.values.clone();
                    break;
                default:
                    return;
            }

            float[] rotationMatrix = new float[9];
            boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix,
                    null, mAccelerometerData, mMagnetometerData);

            float orientationValues[] = new float[3];
            if (rotationOK) {
                SensorManager.getOrientation(rotationMatrix, orientationValues);
            }
            azimuth = orientationValues[0];
            pitch = orientationValues[1];
            roll = orientationValues[2];

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 10 seconds
                    value_azimuth.setText(getResources().getString(
                            R.string.value_format, azimuth));
                    value_pitch.setText(getResources().getString(
                            R.string.value_format, pitch));
                    value_roll.setText(getResources().getString(
                            R.string.value_format, roll));
                    handler.postDelayed(this, 2000);
                }
            }, 2000);  //the time is in miliseconds
        }
    };
    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }

    private void createDatabase() {
        sqLiteDatabase = openOrCreateDatabase("Nama_Database_Baru", Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Nama_Tabel (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title VARCHAR, azimuth VARCHAR, pitch VARCHAR, roll VARCHAR);");
    }

    private void addData() {
        SQLiteQuery = "INSERT INTO Nama_Tabel (title,azimuth,pitch,roll) VALUES ('"+ getCurrentDate() +"', '"+ azimuth +"', '"+ pitch +"', '"+ roll +"');";
        sqLiteDatabase.execSQL(SQLiteQuery);
    }
    public void MainActivity (View view){
        Intent intent = new Intent(Activity2.this, MainActivity.class);
        startActivity(intent);
    }

    public void Activity3 (View view){
        Intent intent = new Intent(Activity2.this, Activity3.class);
        startActivity(intent);
    }
}
