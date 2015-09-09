package br.com.ridesforme.ridesforme;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


public class LoginActivity extends Activity {
    UserSessionManager session;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogar = (Button) findViewById(R.id.button2);
        final EditText txtLogin = (EditText) findViewById(R.id.edtLogin);
        final EditText txtPassword = (EditText) findViewById(R.id.edtPassWord);
        TextView txtCadastrar = (TextView) findViewById(R.id.cadastrarLogin);
        txtCadastrar.setText(Html.fromHtml("<p><u>Cadastre-se agora!</u></p>"));
        session = new UserSessionManager(getApplication());

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtLogin.getText().toString();
                String password = txtPassword.getText().toString();
                session.createUserLoginSession(username, password);
                Intent intent = new Intent(getApplicationContext(), MapHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        //Com WebService
        /*btnLogar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtLogin.getText().toString();
                String password = txtPassword.getText().toString();
                boolean b;
                try {
                    b = new LoginControllerTask().execute(username, password).get();
                    if (b == true) {
                        session.createUserLoginSession(username, password);
                        Intent intent = new Intent(getApplicationContext(), MapHomeActivity.class);
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
*/
        txtCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(it);
            }
        });

    }

    /**
     * Created by Felipe on 27/08/2015.
     */
    public static class LoginControllerTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBuilder()
                        .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
                        .addFormDataPart("login",params[0])
                        .addFormDataPart("senha",params[1])
                        .build();
                Request request = new Request.Builder()
                        .url("http://187.59.115.154:8080/rpg/usuario/login")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    response.body().close();
                    Log.v("a", responseString);
                    return Boolean.parseBoolean(responseString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
