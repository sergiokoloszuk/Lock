package br.com.klz.lock.Fragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.klz.lock.R;
import br.com.klz.lock.activities.MainActivity;
import br.com.klz.lock.model.RegisterModel;

public class EditFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView lista;
    ProgressBar barralist;
    private List<RegisterModel> modelRegister = new ArrayList<RegisterModel>();
    private ArrayAdapter<RegisterModel> adapterRegister;
    RegisterModel registerSelect;
    private String nomeApp, userName, email, senha, uid;
    TextView title;
    EditText edNome, edUser, edEmail, edSenha;
    Button bt;

    public EditFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate( R.layout.fragment_edit, null );
        lista = rootview.findViewById( R.id.listviewEdit );
        FirebaseApp.initializeApp( getActivity() );
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        barralist = rootview.findViewById( R.id.progressEdit );
        populateListView();
        lista.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                registerSelect = (RegisterModel) parent.getItemAtPosition( position );
                nomeApp = registerSelect.getNomeApp();
                userName = registerSelect.getUserName();
                email = registerSelect.getEmail();
                senha = registerSelect.getSenha();
                uid = registerSelect.getUid();
                viewDialog( nomeApp, userName, email, senha );
            }
        } );
        return rootview;
    }

    private void viewDialog(String nomeApp, String userName, String email, String senha) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder( getActivity() );
        View mView = getLayoutInflater().inflate( R.layout.activity_dialog, null );
        mBuilder.setView( mView );
        final AlertDialog dialog = mBuilder.create();
        title = (TextView) mView.findViewById( R.id.txtTitle );
        title.setText( R.string.atualizar_dados );
        edNome = (EditText) mView.findViewById( R.id.edtNome );
        edUser = (EditText) mView.findViewById( R.id.edtUser );
        edEmail = (EditText) mView.findViewById( R.id.edtEmail );
        edSenha = (EditText) mView.findViewById( R.id.edtPass );
        bt = (Button) mView.findViewById( R.id.btnR );
        bt.setText( R.string.Atualizar );
        edNome.setText( nomeApp );
        edUser.setText( userName );
        edEmail.setText( email );
        edSenha.setText( senha );
        bt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegisterModel r = new RegisterModel();
                r.setUid( uid );
                r.setNomeApp( edNome.getText().toString() );
                r.setSenha( edSenha.getText().toString() );
                r.setEmail( edEmail.getText().toString() );
                r.setUserName( edUser.getText().toString() );
                databaseReference.child( MainActivity.identificador ).child( uid ).setValue( r );
                dialog.dismiss();
            }
        } );
        dialog.show();
    }

    public void populateListView() {

        databaseReference.child( MainActivity.identificador ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelRegister.clear();
                if (dataSnapshot.exists()) {

                    for (DataSnapshot obgSnapShot : dataSnapshot.getChildren()) {
                        RegisterModel m = obgSnapShot.getValue( RegisterModel.class );
                        modelRegister.add( m );
                    }
                    adapterRegister = new ArrayAdapter<RegisterModel>( getActivity(), android.R.layout.simple_list_item_1, modelRegister );
                    lista.setAdapter( adapterRegister );
                    barralist.setVisibility( ProgressBar.INVISIBLE );
                } else {
                    Toast.makeText( getActivity(), R.string.nenhum_registro, Toast.LENGTH_SHORT ).show();
                    barralist.setVisibility( ProgressBar.INVISIBLE );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

}
