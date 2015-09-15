package br.com.ridesforme.ridesforme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CaronaPasso1Activity extends AppCompatActivity {
    private TextView txtEndereco;
    private TextView txtNumero;
    private TextView txtCidade;
    private TextView txtBairro;

    private TextView txtEnderecoDestino;
    private TextView txtNumeroDestino;
    private TextView txtCidadeDestino;
    private TextView txtBairroDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carona_passo1);
        Intent intent = getIntent();
        Bundle params = intent.getExtras();
        txtEndereco = (TextView) findViewById(R.id.txtEnderecoOrigem);
        txtNumero = (TextView) findViewById(R.id.txtNumeroOrigem);
        txtCidade = (TextView) findViewById(R.id.txtCidadeOrigem);
        txtBairro = (TextView) findViewById(R.id.txtBairroOrigem);

        txtEndereco.setText(params.getString("endereco"));
        txtNumero.setText(params.getString("numero"));
        txtCidade.setText(params.getString("cidade"));
        txtBairro.setText(params.getString("bairro"));

        txtEnderecoDestino = (TextView) findViewById(R.id.txtRuaDestino);
        txtNumeroDestino = (TextView) findViewById(R.id.txtNumeroDestino);
        txtCidadeDestino = (TextView) findViewById(R.id.txtCidadeDestino);
        txtBairroDestino = (TextView) findViewById(R.id.txtBairroDestino);


        Button proxPasso = (Button) findViewById(R.id.button4);
        proxPasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("endereco",txtEndereco.getText().toString());
                b.putString("numero",txtNumero.getText().toString());
                b.putString("bairro",txtBairro.getText().toString());
                b.putString("cidade",txtCidade.getText().toString());
                b.putString("enderecoDestino",txtEnderecoDestino.getText().toString());
                b.putString("bairroDestino",txtBairroDestino.getText().toString());
                b.putString("numeroDestino",txtNumeroDestino.getText().toString());
                b.putString("cidadeDestino",txtCidadeDestino.getText().toString());
                Intent intent = new Intent(CaronaPasso1Activity.this,CaronaPasso2Activity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
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
