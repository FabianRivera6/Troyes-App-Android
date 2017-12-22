package fr.utt.if26.troyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Accueil extends AppCompatActivity {


    ListViewAdapter adapter;

    String [] titulo= new String[]{"Je Signale", "Je Sors", "Je Participe", "Je Découvre", "Je Cherche"};

    int [] imagenes= {
            R.drawable.signaler,
            R.drawable.sors,
            R.drawable.participe,
            R.drawable.decouvre,
            R.drawable.chercher,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        final ListView lista=(ListView)findViewById(R.id.lv1);
        adapter= new ListViewAdapter(this,titulo,imagenes);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0){

                    Intent intent=new Intent(getApplicationContext(), Signaler_Probleme.class);
                    startActivity(intent);
                }

                Toast.makeText(getApplicationContext(), "L'app est en construction ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
