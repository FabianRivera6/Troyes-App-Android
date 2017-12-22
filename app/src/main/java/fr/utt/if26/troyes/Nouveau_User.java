package fr.utt.if26.troyes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Nouveau_User extends AppCompatActivity {

    Button btnCrear;
    EditText etMail,etPassword,etRePassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau__user);

        btnCrear=(Button)findViewById(R.id.btnCrearl);
        etMail=(EditText)findViewById(R.id.etMaill);
        etPassword=(EditText)findViewById(R.id.etPasswordl);
        etRePassword=(EditText)findViewById(R.id.etRePasswordl);
        firebaseAuth=FirebaseAuth.getInstance();


        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                btnUserLogin_Click();
            }
        });
    }

    public void btnUserLogin_Click(){


        String email=etMail.getText().toString().trim();
        String password=etPassword.getText().toString().trim();
        String Repassword=etRePassword.getText().toString().trim();

        if(email.isEmpty()){
            etMail.setError("Email is required");
            etMail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etMail.setError("Please entre a valid email");
            etMail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;

        }

        if(password.length()<6){

            etPassword.setError("Minimum lenght of password should be 6");
            etPassword.requestFocus();
            return;
        }

        if(Repassword.isEmpty()){
            etRePassword.setError("Password is required");
            etRePassword.requestFocus();
            return;

        }

        if(!password.equals(Repassword)){

            etPassword.setError("The password doesn't match");
            etPassword.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(Nouveau_User.this, "Veuillez Patienter...", "Creating User...", true);
        firebaseAuth.createUserWithEmailAndPassword(etMail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration Succesful!!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), Login.class);
                            i.putExtra("Email", firebaseAuth.getCurrentUser().getEmail());
                            startActivity(i);
                        } else {
                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}
