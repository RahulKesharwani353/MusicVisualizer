package com.example.opmusic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chibde.visualizer.BarVisualizer;
import com.chibde.visualizer.SquareBarVisualizer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.tone);

        reqestPermission();

    }


    private void reqestPermission() {


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.RECORD_AUDIO
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now

                            startAudioVisulizer();

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();


    }

    private void startAudioVisulizer() {

        SquareBarVisualizer squareBarVisualizer = findViewById(R.id.visualizer);
        squareBarVisualizer.setColor(ContextCompat.getColor(this, R.color.purple_200));
        squareBarVisualizer.setDensity(65);
        squareBarVisualizer.setGap(2);
        squareBarVisualizer.setPlayer(mediaPlayer.getAudioSessionId());



    }


    public void playButtonClick(View view) {

        mediaPlayer.start();

    }

    public void stopButtonClick(View view) {

        mediaPlayer.stop();

    }

    public void pauseButtonClick(View view) {

        mediaPlayer.pause();

    }
}
