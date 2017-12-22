package fr.utt.if26.troyes;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageListActivity extends AppCompatActivity {



    private DatabaseReference databaseReference;
    private List<ImageUpload> imglist;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        imglist=new ArrayList<>();
        lv=(ListView)findViewById(R.id.ListViewImagel);

        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        progressDialog.show();

        databaseReference= FirebaseDatabase.getInstance().getReference(AjouterPhoto.FB_Databse_path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){

                    ImageUpload img= snapshot.getValue(ImageUpload.class);
                    imglist.add(img);
                }

                adapter= new ImageListAdapter(ImageListActivity.this, R.layout.image_item, imglist);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();


            }
        });
    }
}
