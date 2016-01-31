package br.com.tdm.convertermoeda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;


public class MainActivity extends ActionBarActivity {

    ImageButton calcular;
    ImageButton btn_editMoeda;
    ImageButton btn_editCotacao;

    EditText edt_cotacao;
    EditText edt_qtde;
    TextView view_reais;
    EditText edt_moeda;


    SharedPreferences arqDeDadosDaCotacao;

    private float cotacao;
    private float valorMoeda;
    private float reais;
    private String moeda;

    final static String NOME_FILE = "convertermoeda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arqDeDadosDaCotacao = getSharedPreferences(NOME_FILE, MODE_PRIVATE);

        cotacao =  arqDeDadosDaCotacao.getFloat("cotacao",0) ;
        moeda = arqDeDadosDaCotacao.getString("moedaDestino","");
        edt_qtde = (EditText) findViewById(R.id.edt_qtde);

        edt_cotacao = (EditText) findViewById(R.id.edt_cotacao);
        if (cotacao > 0) {
            edt_cotacao.setText(cotacao+"");
        }else{
            edt_cotacao.setHint("em "+moeda);
        }

        if (!moeda.equals("") ) {
            edt_moeda = (EditText) findViewById(R.id.edt_moeda);
            edt_moeda.setText(moeda);
        }



        // setando o focus
        edt_qtde.findFocus();
        edt_qtde.setFocusable(true);
        edt_qtde.setHint("em "+moeda);



        // botão edit moeda
        btn_editMoeda = (ImageButton) findViewById(R.id.btn_editMoeda);
        btn_editMoeda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            edt_moeda = (EditText) findViewById(R.id.edt_moeda);
            //edt_moeda.setText("    ");
            edt_moeda.setEnabled(true);
            edt_moeda.findFocus();
            edt_moeda.setFocusable(true);
            mensagem("Informe a nova moeda");

            }
        });

        // botão edit moeda
        btn_editCotacao = (ImageButton) findViewById(R.id.btn_editCotacao);
        btn_editCotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            edt_cotacao = (EditText) findViewById(R.id.edt_cotacao);
            edt_cotacao.setEnabled(true);
            mensagem("Informe a nova cotação");

            }
        });


        calcular = (ImageButton) findViewById(R.id.btn_calcular);
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            calcular();

            }
        });


    }


    private void calcular(){

        edt_cotacao = (EditText) findViewById(R.id.edt_cotacao);
        edt_qtde =  (EditText) findViewById(R.id.edt_qtde);
        view_reais = (TextView) findViewById(R.id.txfld_valorreais);
        edt_moeda = (EditText) findViewById(R.id.edt_moeda);

        if (edt_cotacao.getText().toString().trim().equals("")) {
            cotacao = 0;
            mensagem("Faltou informar o valor da cotação");
        }else{
            cotacao = Float.parseFloat( edt_cotacao.getText().toString());
        }

        if (edt_qtde.getText().toString().trim().equals("")) {
            valorMoeda = 0;
            mensagem("Faltou informar o valor na moeda ");
        }else{
            valorMoeda = Float.parseFloat(edt_qtde.getText().toString());
        }

        reais  = valorMoeda * cotacao;

        view_reais.setText( "R$ "+String.valueOf(round(reais,2) ));


        if (edt_moeda.getText().toString().trim().equals("")) {
            mensagem("Faltou informar o nome da moeda");
        }else{
            moeda = edt_moeda.getText().toString();
        }


        // salvando dados
        Editor editor = arqDeDadosDaCotacao.edit();

        if ( cotacao>0.00) {
            editor.putFloat("cotacao", cotacao);
        }
        if (moeda != null ) {
            editor.putString("moedaDestino", moeda);
        }
        editor.commit();

    }

    private BigDecimal round( Float reais, Integer casasDecimais) {
        BigDecimal big = new BigDecimal(reais);
        big = big.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return big;
    }

    private void mensagem(String mensagem){

        Context contexto = getApplicationContext();
        int duracao = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(contexto, mensagem,duracao);
        toast.show();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id){

            case R.id.action_calcular:

                Intent it = new Intent( MainActivity.this, Calcular.class);
                startActivity(it);

        }
        if (id == R.id.action_calcular) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
