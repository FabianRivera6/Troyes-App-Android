package fr.utt.if26.troyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textvMotPasseOublie;
    TextView texttvNouveau;
    ImageView imvAcceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textvMotPasseOublie=(TextView)findViewById(R.id.tvMotPasseOublie);

        textvMotPasseOublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), MotdePasseOublie.class);
                startActivity(i);
            }
        });

        texttvNouveau=(TextView)findViewById(R.id.tvNouveau);
        texttvNouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), Nouveau_utilisateur.class);
                startActivity(i);
            }
        });

        imvAcceder=(ImageView)findViewById(R.id.ivAcceder);
        imvAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(), PageAccueil.class);
                startActivity(i);
            }
        });

    }
}
