package br.com.ridesforme.ridesforme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends Activity {
    UserSessionManager session;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogar = (Button) findViewById(R.id.button2);
        final EditText txtLogin = (EditText) findViewById(R.id.editLogin);
        final EditText txtPassword = (EditText) findViewById(R.id.editPassword);
        session = new UserSessionManager(getApplication());

        btnLogar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtLogin.getText().toString();
                String password = txtPassword.getText().toString();
                boolean b;
                try {
                    b = new LoginControllerTask().execute(username, password).get();
                    if (b == true) {
                        session.createUserLoginSession(username, password);
                        Intent intent = new Intent(getApplicationContext(), TesteLoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplication(), "login ou senha inválidos!", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });

    }

}
