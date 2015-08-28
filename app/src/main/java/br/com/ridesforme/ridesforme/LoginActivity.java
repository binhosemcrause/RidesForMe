package br.com.ridesforme.ridesforme;

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
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


//Fonte:
//http://androidexample.com/Android_Session_Management_Using_SharedPreferences_-_Android_Example/index.php?view=article_discription&aid=127&aaid=147

public class LoginActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogar = (Button)findViewById(R.id.button2);
        final EditText txtUsername = (EditText) findViewById(R.id.editLogin);
        final EditText txtPassword = (EditText) findViewById(R.id.editPassword);
        final UserSessionManager session;
        session = new UserSessionManager(getApplicationContext());

        //temporário (excluir depois)
        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();


        btnLogar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

               // String[] loginDados = {username,password};

                new LoginControllerTask().execute(username,password);

                /*if(username.trim().length() > 0 && password.trim().length() > 0){
                    if(username.equals("admin") && password.equals("admin")){
                        session.createUserLoginSession(username,
                                "androidexample84@gmail.com");
                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), TesteLoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();

                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Username/Password is incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Please enter username and password",
                            Toast.LENGTH_LONG).show();

                }

                Log.w("Clicou", "Clicou");*/

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


}
