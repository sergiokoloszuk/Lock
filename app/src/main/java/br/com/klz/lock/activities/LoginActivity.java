package br.com.klz.lock.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import br.com.klz.lock.DAO.ConfigFirebase;
import br.com.klz.lock.R;
import br.com.klz.lock.model.User;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout emailLogin;
    TextInputLayout passwordLogin;
    Button btnConfirm;
    Button btnRegister;
    FirebaseAuth autenticacao;
    User user;
    public static String id;
    ProgressBar barra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        emailLogin = findViewById( R.id.textInputLayout_emailLogin);
        passwordLogin = findViewById( R.id.textInputLayout_passwordLogin );
        btnConfirm = findViewById( R.id.btn_confirmLogin );
        btnRegister = findViewById( R.id.btn_registerLogin );
        barra = findViewById(R.id.progressBar);
        barra.setVisibility(View.GONE);



        btnRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Crashlytics.getInstance().crash(); // Force a crash

                Intent intent = new Intent( getApplicationContext(), RegistroUsuarioActivity.class );
                startActivity( intent );
                finish();
            }
        } );

        btnConfirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailLogin.getEditText().getText().toString().equals( "" ) && !passwordLogin.getEditText().getText().toString().equals( "" )){

                    user = new User();
                    user.setEmail( emailLogin.getEditText().getText().toString());
                    user.setSenha( passwordLogin.getEditText().getText().toString() );
                    validarLogin();

                    barra.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(LoginActivity.this,"Preencha seus dados",Toast.LENGTH_SHORT).show();

                    barra.setVisibility(View.GONE);
                }
            }
        } );





    }
    private void validarLogin(){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword( user.getEmail(),user.getSenha() ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    abrirTelaSearch();
                    if (task.getResult() != null && task.getResult().getUser() != null) {
                        id = task.getResult().getUser().getUid();
                    }

                }else{
                    Toast.makeText( LoginActivity.this,"Deu errado",Toast.LENGTH_SHORT ).show();
                    barra.setVisibility(View.GONE);
                }

            }
        } );


    }
    private void abrirTelaSearch(){

        Intent intent = new Intent( getApplicationContext(),MainActivity.class );
        startActivity(intent);
        finish();
    }


}
