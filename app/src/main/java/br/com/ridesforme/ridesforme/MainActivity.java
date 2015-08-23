package br.com.ridesforme.ridesforme;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            Button btn_telaLogin = (Button)findViewById(R.id.button);

        btn_telaLogin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "hehe", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                
            }
        });





    }

}
