package fr.utt.if26.troyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class BienvenuePage extends AppCompatActivity {

    private TextView tv;
    private ImageView imlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenue_page);

        tv=(TextView)findViewById(R.id.tvbienve);
        imlogo=(ImageView)findViewById(R.id.ivLogo);

        Animation myanim= AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(myanim);
        imlogo.startAnimation(myanim);

        final Intent i=new Intent(getApplicationContext(), Login.class);

        Thread timer =new Thread(){

            public void run(){

                try{

                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finally {

                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();
    }
}
