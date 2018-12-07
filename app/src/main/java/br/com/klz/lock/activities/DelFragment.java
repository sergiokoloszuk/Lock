package br.com.klz.lock.activities;


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
import br.com.klz.lock.model.RegisterModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView lista;
    ProgressBar barralist;
    private List<RegisterModel> modelRegister = new ArrayList<RegisterModel>();
    private ArrayAdapter<RegisterModel> adapterRegister;
    RegisterModel registerSelect;
    private String uidList;
    TextView title;
    EditText edNome,edUser,edEmail,edSenha;
    Button bt;

    public DelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_delete,null);
        lista = (ListView)rootview.findViewById(R.id.listviewDelete);
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        barralist = (ProgressBar)rootview.findViewById(R.id.progressDelete);
        populateListView();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                registerSelect = (RegisterModel) parent.getItemAtPosition(position);
                uidList= registerSelect.getUid();
                viewDialog();
            }
        });
        return rootview;
    }

    private void viewDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getLayoutInflater().inflate(R.layout.activity_del,null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        Button sim = (Button)mView.findViewById(R.id.btS);
        Button nao = (Button)mView.findViewById(R.id.btN);
        sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(MainActivity.identificador).child(uidList).removeValue();
                dialog.dismiss();
                lista.setVisibility(View.GONE);
                populateListView();
            }
        });
        nao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }



    public void populateListView(){

        lista.setVisibility(View.VISIBLE);
        databaseReference.child(MainActivity.identificador).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelRegister.clear();
                if (dataSnapshot.exists()){

                    for (DataSnapshot obgSnapShot:dataSnapshot.getChildren()){
                        RegisterModel m = obgSnapShot.getValue(RegisterModel.class);
                        modelRegister.add(m);
                    }adapterRegister = new ArrayAdapter<RegisterModel>(getActivity(),android.R.layout.simple_list_item_1,modelRegister);
                    lista.setAdapter(adapterRegister);
                    barralist.setVisibility(ProgressBar.INVISIBLE);
                }else{
                    Toast.makeText(getActivity(), "Nenhum Registro Encontrado", Toast.LENGTH_SHORT).show();
                    barralist.setVisibility(ProgressBar.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
