package br.com.klz.lock.activities;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import br.com.klz.lock.DAO.ConfigFirebase;
import br.com.klz.lock.Helper.Preferencias;
import br.com.klz.lock.R;
import br.com.klz.lock.model.Registros;

public class RegistroDadosActivity extends AppCompatActivity {

    private TextInputLayout nomeDoApp;
    private TextInputLayout userName;
    private TextInputLayout email;
    private TextInputLayout senha;
    private Button btnGravar;
    private Button btnConsultar;
    private Registros registros;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registro_dados );

        nomeDoApp = findViewById( R.id.til_nomeDoApp );
        userName = findViewById( R.id.til_user );
        email = findViewById( R.id.til_email );
        senha = findViewById( R.id.til_senha );
        btnGravar = findViewById( R.id.btn_gravar );
        btnConsultar = findViewById( R.id.btn_consultarRegistros );

        btnConsultar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getApplicationContext(), ConsultaDadosActivity.class );
                startActivity( intent );
                finish();
            }
        } );

        btnGravar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registros = new Registros();
                registros.setNomeApp( nomeDoApp.getEditText().getText().toString() );
                registros.setUserName( userName.getEditText().getText().toString() );
                registros.setEmail( email.getEditText().getText().toString() );
                registros.setSenha( senha.getEditText().getText().toString() );

                salvarRegistros( registros );
            }

            private boolean salvarRegistros(Registros registros) {
                try {

                    Preferencias preferencias = new Preferencias( RegistroDadosActivity.this );
                    String id = preferencias.getIdentificador();

                    firebase = ConfigFirebase.getFirebase().child( "addRegistros/" + id );
                    firebase.child( registros.getNomeApp() ).setValue( registros );
                    Toast.makeText( RegistroDadosActivity.this, "Registro Inserido com sucesso", Toast.LENGTH_SHORT ).show();
                    nomeDoApp.getEditText().setText( "" );
                    userName.getEditText().setText( "" );
                    email.getEditText().setText( "" );
                    senha.getEditText().setText( "" );

                    return true;

                } catch (Exception e) {

                    e.printStackTrace();
                    return false;

                }

            }

            ;


        } );


    }
}
