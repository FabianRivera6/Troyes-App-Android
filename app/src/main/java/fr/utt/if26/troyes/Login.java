package fr.utt.if26.troyes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button btnLogin, btnSincrire;
    TextView btnMPOublier;
    EditText etMailLogin, etPasswordLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=(Button) findViewById(R.id.btnLogInl);
        btnSincrire=(Button) findViewById(R.id.btnSinscrirel);
        btnMPOublier=(TextView)findViewById(R.id.btnOubliel);
        etMailLogin=(EditText)findViewById(R.id.etMailLoginl);
        etPasswordLogin=(EditText)findViewById(R.id.etPasswordLoginl);
        firebaseAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnUserLogin_Click();
            }
        });

        btnSincrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), Nouveau_User.class);
                startActivity(i);

            }
        });

        btnMPOublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), Motdepasse.class);
                startActivity(i);
            }
        });
    }

    public void btnUserLogin_Click(){

        String email=etMailLogin.getText().toString().trim();
        String password=etPasswordLogin.getText().toString().trim();


        if(email.isEmpty()){
            etMailLogin.setError("Email is required");
            etMailLogin.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etMailLogin.setError("Please enter a valid email");
            etMailLogin.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPasswordLogin.setError("Password is required");
            etPasswordLogin.requestFocus();
            return;

        }

        final ProgressDialog progressDialog = ProgressDialog.show(Login.this, "Veuillez Patienter...", "Login...", true);

        firebaseAuth.signInWithEmailAndPassword(etMailLogin.getText().toString(), etPasswordLogin.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), PageAccueil.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);
                            etMailLogin.setText("");
                            etPasswordLogin.setText("");

                        }else{

                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }


                    }
                });

    }
}
