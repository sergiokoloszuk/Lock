package br.com.klz.lock.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.klz.lock.R;
//import br.com.klz.lock.activities.RegistroDadosActivity;
import br.com.klz.lock.model.Registros;

public class RegistroAdapter extends ArrayAdapter <Registros> {

    private ArrayList <Registros> registros;
    private Context context;


    public RegistroAdapter(Context c, ArrayList<Registros> objects) {
        super( c, 0, objects );
        this.context = c;
        this.registros = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if(registros != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( context.LAYOUT_INFLATER_SERVICE );

            view = inflater.inflate(R.layout.lista_registros,parent,false);

            TextView textViewNomeApp = (TextView) view.findViewById( R.id.textView_nomeDoApp );
            TextView textViewUserName = (TextView)view.findViewById( R.id.textView_user );
            TextView textViewEmail = (TextView)view.findViewById( R.id.textView_email );
            TextView textViewSenha = (TextView)view.findViewById( R.id.textView_senha );

            Registros registros2 = registros.get( position );

            textViewNomeApp.setText( registros2.getNomeApp() );
            textViewUserName.setText(registros2.getUserName());
            textViewEmail.setText( registros2.getEmail() );
            textViewSenha.setText( registros2.getSenha() );


        }
        return view;
    }
}
