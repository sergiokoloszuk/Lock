package br.com.klz.lock.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private String NOME = "LockPreferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "Usuario_Logado";
    private final String CHAVE_NOME = "Nome_Usuario_Logado";

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences( NOME,MODE);

        editor = preferences.edit();
    }
    public void salvarPreferenciasUsuarios (String identificadorUsuario, String nomeUsuario){
        editor.putString( CHAVE_IDENTIFICADOR,identificadorUsuario );
        editor.putString( CHAVE_NOME, nomeUsuario );
        editor.commit();
    }
    public String getIdentificador (){
        return preferences.getString(CHAVE_IDENTIFICADOR,null);
    }
    public String getNome (){
        return preferences.getString( CHAVE_NOME,null );
    }
}
