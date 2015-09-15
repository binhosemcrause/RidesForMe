package br.com.ridesforme.ridesforme;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import br.com.ridesforme.ridesforme.basicas.Contato;


public class ContatoActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    public void onClick(View v) {

        Contato contato = new Contato();
        contato.nomeContato = mTxtNomeContatoResultado.getText().toString();
        contato.mensagemContato = mTxtMensagemContato.getText().toString();
        contato.classificacaoApp = mRatingNotaApp.getRating();
        if(mRadioTipoProblema.isChecked()){
            contato.tipoContato = mRadioTipoProblema.getText().toString();
        }else{
            if(mRadioTipoSugestao.isChecked()){
                contato.tipoContato = mRadioTipoSugestao.getText().toString();
            }
        }

        if(isDadosValidos()){
            Intent emailIntent = new Intent(Intent.ACTION_VIEW);
            emailIntent.setData(Uri.parse("contato@ridesforme.com"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contato@ridesforme.com" });
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contato");
            emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
            emailIntent.setType("text/html");
            String mensagemBody = "<b><i>Dados do Contato:</i></b><br>"
                    + "Usuário: " + "<b>" + contato.nomeContato + "</b><br>" +
                    "Classificação:" + "<b>" + String.valueOf(contato.classificacaoApp) + "</b><br>" +
                    "Mensagem:<br>" + "<p>"+ contato.mensagemContato + "</p><br>" +
                    "<span style='margin:auto'><b>Mensagem enviada automaticamente pelo App RidesForMe.</b></span>";

            emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(mensagemBody));
            startActivity(Intent.createChooser(emailIntent, "Email"));
            Toast.makeText(this, getString(R.string.email_success), Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    //Método para verificar se os campos estão preenchidos.
    private boolean isDadosValidos(){
        boolean validado = true;

        if(!mRadioTipoSugestao.isChecked() && !mRadioTipoProblema.isChecked()){
            Toast.makeText(this, getString(R.string.alert_radioButtonEmpty), Toast.LENGTH_SHORT).show();
            validado = false;
        }

        if(mTxtMensagemContato.getText().toString().equals("")){
            mTxtMensagemContato.setError(getString(R.string.alert_campo_obrigatorio));
            mTxtMensagemContato.requestFocus();
            validado = false;
        }

        return validado;
    }

    TextView mTxtNomeContatoResultado;
    EditText mTxtEmailContato;
    EditText mTxtMensagemContato;
    RatingBar mRatingNotaApp;
    RadioButton mRadioTipoSugestao;
    RadioButton mRadioTipoProblema;
    Button mBotaoEnviarEmailContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);


        UserSessionManager sessao = new UserSessionManager(getApplicationContext());
        HashMap<String, String> mapUser = sessao.getUserDetails();


        //Obtendo a instância dos componentes da Activity.
        mTxtNomeContatoResultado = (TextView)findViewById(R.id.txtViewResultadoNomeContato);
        mTxtMensagemContato = (EditText)findViewById(R.id.edtMensagemContato);
        mRatingNotaApp = (RatingBar)findViewById(R.id.ratingBar);
        mBotaoEnviarEmailContato = (Button)findViewById(R.id.botao_enviar);
        mRadioTipoSugestao = (RadioButton)findViewById(R.id.rb_sugestao);
        mRadioTipoProblema = (RadioButton)findViewById(R.id.rb_problema);
        mBotaoEnviarEmailContato.setOnClickListener(this);

        mTxtNomeContatoResultado.setText(mapUser.get(UserSessionManager.KEY_NAME));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contato, menu);
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
