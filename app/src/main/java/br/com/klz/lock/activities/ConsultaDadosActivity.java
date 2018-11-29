package br.com.klz.lock.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.klz.lock.Adapter.RegistroAdapter;
import br.com.klz.lock.DAO.ConfigFirebase;
import br.com.klz.lock.Helper.Preferencias;
import br.com.klz.lock.R;
import br.com.klz.lock.model.Registros;

public class ConsultaDadosActivity extends AppCompatActivity {

    private Button btnVoltarConsulta;
    private ListView listView;
    private ArrayAdapter<Registros> adapter;
    private ArrayList<Registros> registros;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerRegistros;

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener( valueEventListenerRegistros );

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener( valueEventListenerRegistros );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_consulta_dados );

        registros = new ArrayList<>();
        listView = (ListView) findViewById( R.id.listView_registros );
        adapter = new RegistroAdapter( this, registros );

        listView.setAdapter( adapter );

        Preferencias preferencias = new Preferencias( ConsultaDadosActivity.this );
        String id = preferencias.getIdentificador();

        firebase = ConfigFirebase.getFirebase().child( "addRegistros/" + id );

        valueEventListenerRegistros = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                registros.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Registros registrosNovos = dados.getValue( Registros.class );
                    registros.add( registrosNovos );
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        btnVoltarConsulta = findViewById( R.id.btn_voltarConsulta );


        btnVoltarConsulta.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), RegistroDadosActivity.class );
                startActivity( intent );
                finish();
            }


        } );


    }
}
