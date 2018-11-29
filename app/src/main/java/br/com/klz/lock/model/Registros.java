package br.com.klz.lock.model;

public class Registros {

    private String nomeApp;
    private String userName;
    private String email;
    private String senha;

    public Registros() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNomeApp() {
        return nomeApp;
    }

    public void setNomeApp(String nomeApp) {
        this.nomeApp = nomeApp;
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
}
