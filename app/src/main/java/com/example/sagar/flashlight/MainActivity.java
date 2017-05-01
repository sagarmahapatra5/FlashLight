package com.example.sagar.flashlight;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

    private boolean isLightOn = false;
    private Camera camera;
    private ImageButton buttonOn;
    private ImageButton buttonSOS;
    MediaPlayer mp;

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOn = (ImageButton) findViewById(R.id.btnSwitch);
        Context context = this;
        PackageManager pm = context.getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.e("err", "Device has no camera!");
            return;
        }
        camera = Camera.open();
        final android.hardware.Camera.Parameters p = camera.getParameters();

        buttonOn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (isLightOn) {
                    mp = MediaPlayer.create(MainActivity.this, R.raw.torch_sound);
                    mp.start();
                    buttonOn.setImageResource(R.drawable.power_off);
                    Toast.makeText(MainActivity.this, "torch is turn off!", Toast.LENGTH_SHORT).show();

                    p.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(p);
                    camera.stopPreview();
                    mp.release();
                    isLightOn = false;

                } else {
                    mp = MediaPlayer.create(MainActivity.this, R.raw.torch_sound);
                    mp.start();
                    buttonOn.setImageResource(R.drawable.power_on);
                    Toast.makeText(MainActivity.this, "torch is turn on!", Toast.LENGTH_SHORT).show();

                    p.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);

                    camera.setParameters(p);
                    camera.startPreview();
                    mp.release();
                    isLightOn = true;

                }

            }
        });
        /*buttonSOS = (ImageButton) findViewById(R.id.SOS_Button);

        buttonSOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String myString = "0101010101";
                long blinkDelay = 50; //Delay in ms
                for (int i = 0; i < myString.length(); i++) {
                    if (myString.charAt(i) == '0') {
                        mp = MediaPlayer.create(MainActivity.this, R.raw.torch_sound);
                        mp.start();
                        buttonSOS.setImageResource(R.drawable.sos_red);
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        mp.release();
                    } else {
                        mp = MediaPlayer.create(MainActivity.this, R.raw.torch_sound);
                        mp.start();
                        buttonSOS.setImageResource(R.drawable.sos_pink);
                        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        mp.release();
                    }
                    try {
                        Thread.sleep(blinkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
    }
}
