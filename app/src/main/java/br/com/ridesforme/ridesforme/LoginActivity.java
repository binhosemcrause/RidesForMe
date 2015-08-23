package br.com.ridesforme.ridesforme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogar = (Button)findViewById(R.id.button2);

        btnLogar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("Clicou","Clicou");
                post();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void post() {

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new MultipartBuilder()
                            .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
                            .addFormDataPart("login", "silvana")
                            .addFormDataPart("senha", "silvana")
                            .build();
                    Request request = new Request.Builder()
                            .url("http://192.168.25.34/rpg/usuario/cadastrarUsuario")
                            .post(requestBody)
                            .build();
                    try {
                        Response response = client.newCall(request).execute();
                        String responseString = response.body().string();
                        response.body().close();
                        // do whatever you need to do with responseString
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }
}
