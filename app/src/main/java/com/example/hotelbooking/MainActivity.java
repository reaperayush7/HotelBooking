package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep( 3000 );
                    Intent intent = new Intent( MainActivity.this, DashboardActivity.class );
                    startActivity( intent );
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ).start();

    }
}
