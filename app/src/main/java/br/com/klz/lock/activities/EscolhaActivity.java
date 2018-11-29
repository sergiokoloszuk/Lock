package br.com.klz.lock.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.klz.lock.R;

public class EscolhaActivity extends AppCompatActivity {

    private Button btnInserir;
    private Button btnConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_escolha );

        btnInserir = findViewById( R.id.btn_inserirEscolha );
        btnConsultar = findViewById( R.id.btn_consultarEscolha );

        btnInserir.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(),RegistroDadosActivity.class );
                startActivity( intent );
                finish();
            }
        } );

        btnConsultar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(),ConsultaDadosActivity.class);
                startActivity( intent );
                finish();
            }
        } );
    }
}
