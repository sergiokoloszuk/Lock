package br.com.klz.lock.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {

    public static DatabaseReference referenciaFarebase;
    public static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase (){
        if(referenciaFarebase == null){
            referenciaFarebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFarebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao (){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }


}
