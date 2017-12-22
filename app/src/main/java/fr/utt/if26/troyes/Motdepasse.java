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
import com.google.firebase.auth.FirebaseAuth;

public class Motdepasse extends AppCompatActivity {

    EditText etMailRecuperer;
    Button btnRecuperer;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motdepasse);

        etMailRecuperer=(EditText)findViewById(R.id.etMailRecupererl);
        btnRecuperer=(Button)findViewById(R.id.btnRecupererl);
        firebaseAuth = FirebaseAuth.getInstance();

        btnRecuperer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetPassword(etMailRecuperer.getText().toString());
            }
        });
    }

    private void resetPassword(final String email) {

        String Correo=etMailRecuperer.getText().toString().trim();

        if(email.isEmpty()){
            etMailRecuperer.setError("Email is required");
            etMailRecuperer.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etMailRecuperer.setError("Please entre a valid email");
            etMailRecuperer.requestFocus();
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(Motdepasse.this, "Veuillez Patienter...", "Sending mail...", true);
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(), "L'email a été envoyé " +email, Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(), Login.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }else {

                            Log.e("ERROR", task.getException().toString());
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}
