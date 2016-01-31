package br.com.tdm.convertermoeda;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;


public class Calcular extends ActionBarActivity {

    private Button btnCalcular;
    private EditText edtMoedaOrigem;
    private EditText edtVrMoedaOrigem;
    private EditText edtMoedaDestino;
    private EditText edtVrMoedaDestino;
    private TextView txvCotacao;

    private Float vrNaMoedaOrigem;
    private Float vrNaMoedaDestino;
    private BigDecimal cotacao;
    private String moedaDestino;
    private String moedaOrigem;

    SharedPreferences arqDeDadosDaCotacao;
    final static String NOME_FILE = "convertermoeda";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcular);

        edtMoedaOrigem = (EditText) findViewById(R.id.edt_moedaOrigem);
        edtVrMoedaOrigem = (EditText) findViewById(R.id.edt_vrMoedaOrigem);
        edtMoedaDestino = (EditText) findViewById(R.id.edt_moedaDestino);
        edtVrMoedaDestino = (EditText) findViewById(R.id.edt_vrMoedaDestino);
        txvCotacao = (TextView) findViewById(R.id.txfld_valorCotacao);


        // ler nome da moeda origem
        arqDeDadosDaCotacao = getSharedPreferences(NOME_FILE, MODE_PRIVATE);
        moedaDestino = arqDeDadosDaCotacao.getString("moedaDestino","") ;
        if ( !moedaDestino.equals("") ) {
            edtMoedaDestino.setText(moedaDestino);
        }


        btnCalcular = (Button) findViewById(R.id.btn_calcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                moedaOrigem = edtMoedaOrigem.getText().toString();
                moedaDestino = edtMoedaDestino.getText().toString();

                vrNaMoedaOrigem = Float.parseFloat( edtVrMoedaOrigem.getText().toString());
                vrNaMoedaDestino = Float.parseFloat( edtVrMoedaDestino.getText().toString());

                cotacao = round( vrNaMoedaOrigem / vrNaMoedaDestino,4);

                txvCotacao.setText( String.valueOf(cotacao ));


            }
        });


    }

    private BigDecimal round( Float reais, Integer casasDecimais) {
        BigDecimal big = new BigDecimal(reais);
        big = big.setScale(casasDecimais, BigDecimal.ROUND_HALF_EVEN);
        return big;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_calcular, menu);
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
