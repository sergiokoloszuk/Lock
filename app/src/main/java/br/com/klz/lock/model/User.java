package br.com.klz.lock.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import br.com.klz.lock.DAO.ConfigFirebase;

public class User {

    public User() {
    }

    public void salvar () {
        DatabaseReference referenciaDatabase = ConfigFirebase.getFirebase();
        referenciaDatabase.child( "user" ).child( String.valueOf( getId() ) ).setValue( this );

    }

    @Exclude
    public Map<String,Object> toMap(){
        HashMap <String,Object> hashMapUser = new HashMap<>(  );

        hashMapUser.put("id",getId());
        hashMapUser.put( "email",getEmail() );
        hashMapUser.put("senha",getSenha());
        hashMapUser.put ("nome",getNome());

        return hashMapUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private String id;
    private String email;
    private String senha;
    private String nome;
}
