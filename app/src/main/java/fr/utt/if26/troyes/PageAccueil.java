package fr.utt.if26.troyes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PageAccueil extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    CardView cvJePropose, cvJeSignale, cvJeDecouvre, cvJeSors, cvJeParticipe, cvJecherche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_accueil);

        firebaseAuth=FirebaseAuth.getInstance();
        cvJeSignale=(CardView)findViewById(R.id.jesignale);
        cvJePropose=(CardView)findViewById(R.id.jepropose);
        cvJeDecouvre=(CardView)findViewById(R.id.jedecouvre);
        cvJeSors=(CardView)findViewById(R.id.jesors);
        cvJeParticipe=(CardView)findViewById(R.id.jeparticipe);
        cvJecherche=(CardView)findViewById(R.id.jecherche);

        cvJeSignale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(), Signaler_Probleme.class);
                startActivity(intent);
            }
        });


        cvJePropose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "L'app est en construction", Toast.LENGTH_SHORT).show();
            }
        });

        cvJeDecouvre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "L'app est en construction", Toast.LENGTH_SHORT).show();
            }
        });

        cvJeSors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "L'app est en construction", Toast.LENGTH_SHORT).show();
            }
        });

        cvJeParticipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "L'app est en construction", Toast.LENGTH_SHORT).show();
            }
        });

        cvJecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "L'app est en construction", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu monmenu) {


        getMenuInflater().inflate(R.menu.menu_options, monmenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.LogOut){

            signOut();
            return true;
        }
        if(id==R.id.Info){


            Intent i=new Intent(getApplicationContext(), PopUpInformation.class);
            startActivity(i);

            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    public void signOut(){

        final ProgressDialog progressDialog = ProgressDialog.show(PageAccueil.this, "Veuillez Patienter...", "déconnexion...", true);
        firebaseAuth.signOut();
        if(firebaseAuth.getCurrentUser()==null){

            startActivity(new Intent(PageAccueil.this, Login.class));
            finish();
        }


    }
}
