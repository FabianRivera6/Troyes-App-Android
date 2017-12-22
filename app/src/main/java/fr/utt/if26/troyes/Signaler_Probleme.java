package fr.utt.if26.troyes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Signaler_Probleme extends AppCompatActivity {

    EditText etPlato, etPrecio;
    Button btnEscoger, btnGuardar, btnlista;
    ImageView ivComida;

    final int REQUEST_CODE_GALLERY = 999;
    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signaler__probleme);

        init();
        sqLiteHelper = new SQLiteHelper(this, "FoodDB.sqlite", null, 1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS FOOD (Id INTEGER PRIMARY KEY AUTOINCREMENT, plato VARCHAR, precio VARCHAR, image BLOG)");

        btnEscoger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(Signaler_Probleme.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);


            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             String txtPlato=etPlato.getText().toString().trim();
             String txtPrecio=etPrecio.getText().toString().trim();

              if(txtPlato.isEmpty()){

                    etPlato.setError("Veuillez remplir les champs");
                    etPlato.requestFocus();
                    return;
              }

              if(txtPrecio.isEmpty()){

                  etPrecio.setError("Veuillez remplir les champs");
                  etPrecio.requestFocus();
                  return;
              }

                try{
                    sqLiteHelper.insertData(
                            etPlato.getText().toString().trim(),
                            etPrecio.getText().toString().trim(),
                            imageViewToByte(ivComida)
                    );

                    Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    etPlato.setText("");
                    etPrecio.setText("");
                    ivComida.setImageResource(R.drawable.ic_group_add_black_24dp);
                }

                catch (Exception e){

                    e.printStackTrace();
                }
            }
        });

        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(Signaler_Probleme.this, problemes_list_activity.class);
                startActivity(intent);
            }
        });
    }

    public static  byte[] imageViewToByte(ImageView image) {

        Bitmap bitmap =((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){

            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else{

                Toast.makeText(getApplicationContext(), "No tiene permisos paraa acceder a los archivos", Toast.LENGTH_SHORT).show();

            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data !=null){

            Uri uri= data.getData();

            try{

                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                ivComida.setImageBitmap(bitmap);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void init(){

        etPlato=(EditText)findViewById(R.id.etPlatoL);
        etPrecio=(EditText)findViewById(R.id.etPreciol);

        btnEscoger=(Button)findViewById(R.id.btnEscogerL);
        btnGuardar=(Button)findViewById(R.id.btnGuardarL);
        btnlista=(Button)findViewById(R.id.btnlistaL);
        ivComida=(ImageView)findViewById(R.id.ivComidaL);
    }
}
