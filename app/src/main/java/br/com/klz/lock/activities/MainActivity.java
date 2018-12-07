package br.com.klz.lock.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import br.com.klz.lock.Adapter.FragmentAdapter;
import br.com.klz.lock.R;
import br.com.klz.lock.model.RegisterModel;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText edNome,edUser,edEmail,edSenha;
    Button bt;
    public static String identificador;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());
        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
        identificador = LoginActivity.id;
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_dialog,null);

            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            edNome = (EditText)mView.findViewById(R.id.edtNome);
            edUser = (EditText)mView.findViewById(R.id.edtUser);
            edEmail = (EditText)mView.findViewById(R.id.edtEmail);
            edSenha = (EditText)mView.findViewById(R.id.edtPass);
            bt = (Button)mView.findViewById(R.id.btnR);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RegisterModel r = new RegisterModel();
                    r.setUid(UUID.randomUUID().toString());
                    r.setNomeApp(edNome.getText().toString());
                    r.setSenha(edSenha.getText().toString());
                    r.setEmail(edEmail.getText().toString());
                    r.setUserName(edEmail.getText().toString());
                    databaseReference.child(identificador).child(r.getUid()).setValue(r);
                    dialog.dismiss();
                }

            });
                dialog.show();


            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
