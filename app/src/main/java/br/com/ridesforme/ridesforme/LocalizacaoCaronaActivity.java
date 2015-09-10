package br.com.ridesforme.ridesforme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class LocalizacaoCaronaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacao_carona);
        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        TextView txtEndereco = (TextView) findViewById(R.id.txtEnderecoOrigem);
        TextView txtNumero = (TextView) findViewById(R.id.txtNumeroOrigem);
        TextView txtCidade = (TextView) findViewById(R.id.txtCidadeOrigem);
        txtEndereco.setText(params.getString("endereco"));
        txtNumero.setText(params.getString("numero"));
        txtCidade.setText(params.getString("cidade"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.localizacao, menu);

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
