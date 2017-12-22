package fr.utt.if26.troyes;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AjouterPhoto extends AppCompatActivity {



    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ImageView imageView;
    private EditText txtImageName,txtImageDescription;
    private Uri imguri;

    public static final String FB_Storage_path="image/";
    public static final String FB_Databse_path="image";
    public static final int Request_code=1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_photo);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference(FB_Databse_path);

        imageView=(ImageView)findViewById(R.id.imageViewL);
        txtImageName=(EditText)findViewById(R.id.txtImageNamel);
        txtImageDescription=(EditText)findViewById(R.id.txtImageDescriptionl);

    }

    public void btnBrowse_Click(View view){

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "SelectImage"), Request_code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Request_code && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            imguri=data.getData();

            try{
                Bitmap bm= MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);
                imageView.setImageBitmap(bm);

            }catch (FileNotFoundException e){

                e.printStackTrace();
            }catch (IOException e){

                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri){

        ContentResolver contentResolver= getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void btnUpload(View view){
        if(imguri !=null){

            final ProgressDialog dialog=new ProgressDialog(this);
            dialog.setTitle("Uploading Image...");
            dialog.show();

            StorageReference ref= storageReference.child((FB_Storage_path + System.currentTimeMillis() + "." + getImageExt(imguri)));

            ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Image Upload", Toast.LENGTH_SHORT).show();
                    ImageUpload imageUpload = new ImageUpload(txtImageName.getText().toString(), txtImageDescription.getText().toString(), taskSnapshot.getDownloadUrl().toString());

                    String uploadurl = databaseReference.push().getKey();
                    databaseReference.child(uploadurl).setValue(imageUpload);

                    txtImageName.setText("");
                    txtImageDescription.setText("");


                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();;


                        }
                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress=(100* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();

                            dialog.setMessage("Uploading.." + (int)progress + "@");
                        }
                    });
        }else {

            Toast.makeText(getApplicationContext(), "Please select Image", Toast.LENGTH_SHORT).show();

        }


    }

    public void btnShowListImage_Click(View view){

        Intent i=new Intent(AjouterPhoto.this, ImageListActivity.class);
        startActivity(i);


    }
}
