package com.example.pract25;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mDorimeSound, mUtkaSound, mVroomVroomSound, mKuruKuruSound, mSeptemberSound, mEheSound ;
    private int mStreamID;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Для новых устройств
        createNewSoundPool();

//        mAssetManager = getAssets();


        ImageButton cowImageButton = findViewById(R.id.dorime);
        cowImageButton.setOnClickListener(onClickListener);

        ImageButton chickenImageButton = findViewById(R.id.utka);
        chickenImageButton.setOnClickListener(onClickListener);

        ImageButton catImageButton = findViewById(R.id.VroomVroom);
        catImageButton.setOnClickListener(onClickListener);

        ImageButton duckImageButton = findViewById(R.id.KuruKuru);
        duckImageButton.setOnClickListener(onClickListener);

        ImageButton sheepImageButton = findViewById(R.id.september);
        sheepImageButton.setOnClickListener(onClickListener);

        ImageButton dogImageButton = findViewById(R.id.Ehe);
        dogImageButton.setOnClickListener(onClickListener);



    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.dorime:
                    playSound(mDorimeSound);
                    break;
                case R.id.utka:
                    playSound(mUtkaSound);
                    break;
                case R.id.VroomVroom:
                    playSound(mVroomVroomSound);
                    break;
                case R.id.KuruKuru:
                    playSound(mKuruKuruSound);
                    break;
                case R.id.september:
                    playSound(mSeptemberSound);
                    break;
                case R.id.Ehe:
                    playSound(mEheSound);
                    break;
            }
        }
    };

    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Для новых устройств
        createNewSoundPool();

        mAssetManager = getAssets();

        // получим идентификаторы
        mDorimeSound = loadSound("dorime.mp3");
        mUtkaSound = loadSound("utka.mp3");
        mVroomVroomSound = loadSound("vroomrooms.mp3");
        mKuruKuruSound = loadSound("kurukuru.m4a");
        mSeptemberSound = loadSound("bones.mp3");
        mEheSound = loadSound("ehe.m4a");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}

