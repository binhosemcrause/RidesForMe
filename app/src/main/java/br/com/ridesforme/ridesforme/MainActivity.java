package br.com.ridesforme.ridesforme;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;


public class MainActivity extends Activity {
    private long ms = 0;
    private long splashTime = 5000;
    private boolean splashActive = true;
    private boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*SharedPreferences sf = getSharedPreferences("AndroidExamplePref", MODE_PRIVATE);
        String chaveSalva = sf.getString("KEY_NAME", "");

        if(chaveSalva != null && !chaveSalva.equals("")){
            Toast.makeText(getApplicationContext(),chaveSalva,Toast.LENGTH_SHORT).show();
            logado();
        }else{
            naoLogado();
        }*/


        File f = new File("/data/data/"+getPackageName()+"/shared_prefs/AndroidExamplePref.xml");
        if(f.exists()) {
            logado();
        }else {
            naoLogado();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void logado() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {
                        if (!paused) {
                            ms = ms + 100;
                            Thread.sleep(100);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(MainActivity.this, TesteLoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    public void naoLogado() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {
                        if (!paused) {
                            ms = ms + 100;
                            Thread.sleep(100);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

}
