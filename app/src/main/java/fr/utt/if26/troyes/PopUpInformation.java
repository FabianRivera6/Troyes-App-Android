package fr.utt.if26.troyes;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopUpInformation extends AppCompatActivity {

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_information);

        myDialog = new Dialog(this);
    }

    public void ShowPopUp(View v){

        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.activity_pop_up_information);;

        txtclose=(TextView) myDialog.findViewById(R.id.txClosel);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }


}
