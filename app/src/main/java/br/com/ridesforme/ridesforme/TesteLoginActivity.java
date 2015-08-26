package br.com.ridesforme.ridesforme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.HashMap;


//implentar logica abaixo na tela inicial

public class TesteLoginActivity extends AppCompatActivity {
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_login);

        session = new UserSessionManager(getApplicationContext());

        if(session.checkLogin())
            finish();

        HashMap<String,String> user = session.getUserDetails();

        String name = user.get(UserSessionManager.KEY_NAME);

        TextView txtLogin = (TextView)findViewById(R.id.lbllogin);
        txtLogin.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teste_login, menu);
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
