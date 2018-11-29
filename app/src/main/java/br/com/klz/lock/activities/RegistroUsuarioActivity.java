package br.com.klz.lock.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import br.com.klz.lock.DAO.ConfigFirebase;
import br.com.klz.lock.Helper.Base64Custom;
import br.com.klz.lock.Helper.Preferencias;
import br.com.klz.lock.R;
import br.com.klz.lock.model.User;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;
    private TextInputLayout confirmPassword;
    private Button btnConfirm;
    private User user;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registro_usuario );

        email = findViewById( R.id.textInputLayout_emailRegister );
        password = findViewById( R.id.textInputLayout_firstPasswordRegister );
        confirmPassword = findViewById( R.id.textInputLayout_secondPasswordRegister );
        btnConfirm = findViewById( R.id.btn_confirmRegister );

        btnConfirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getEditText().getText().toString().equals( confirmPassword.getEditText().getText().toString() )) {

                    user = new User();
                    user.setEmail( email.getEditText().getText().toString() );
                    user.setSenha( password.getEditText().getText().toString() );


                } else {
                    Toast.makeText( RegistroUsuarioActivity.this, "As senhas precisam ser iguais", Toast.LENGTH_SHORT ).show();

                }

                cadastrarUsuario();

            }

            public void cadastrarUsuario() {

                autenticacao = ConfigFirebase.getFirebaseAutenticacao();
                autenticacao.createUserWithEmailAndPassword(
                        user.getEmail(),
                        user.getSenha()
                ).addOnCompleteListener( RegistroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( RegistroUsuarioActivity.this, "Usuario cadastrado com sucesso", Toast.LENGTH_SHORT ).show();
                            //String identificadorUser = Base64Custom.codificarBase64( user.getEmail() );
                            FirebaseUser usuarioFirebase = task.getResult().getUser();
                            user.setId( usuarioFirebase.getUid() );
                            user.salvar();

                            Preferencias preferencias = new Preferencias( RegistroUsuarioActivity.this );
                            preferencias.salvarPreferenciasUsuarios( user.getId(), user.getNome() );

                            abrirLoginUsuario();


                        } else {
                            String erroException = "";

                            try {

                                throw task.getException();

                            } catch (FirebaseAuthWeakPasswordException e) {
                                erroException = "Senha muito fraca";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erroException = "O e-mail digitado é inválido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                erroException = "E-mail já cadastrado";
                            } catch (Exception e) {
                                erroException = "Erro ao efetuar o cadastro";
                                e.printStackTrace();
                            }
                            Toast.makeText( RegistroUsuarioActivity.this, "Erro" + erroException, Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );

            }
        } );


    }
    public void abrirLoginUsuario (){
        Intent intent = new Intent( RegistroUsuarioActivity.this,LoginActivity.class );
        startActivity( intent );
        finish();
    }
}
