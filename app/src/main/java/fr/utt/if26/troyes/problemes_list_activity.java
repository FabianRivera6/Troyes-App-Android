package fr.utt.if26.troyes;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class problemes_list_activity extends AppCompatActivity {

    GridView gridView;
    ArrayList<problemes> list;
    problemesListAdapter adapter=null;

    ImageView imageViewFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problemes_list_activity);


        gridView=(GridView)findViewById(R.id.gridViewL);
        list = new ArrayList<>();

        adapter=new problemesListAdapter(this, R.layout.problemes_items, list);
        gridView.setAdapter(adapter);

        Cursor cursor = Signaler_Probleme.sqLiteHelper.getData("SELECT * FROM FOOD");
        list.clear();

        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String plato = cursor.getString(1);
            String precio = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new problemes(id, plato, precio, image));

        }

        adapter.notifyDataSetChanged();


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(problemes_list_activity.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {

                        if(item == 0){

                            Cursor c= Signaler_Probleme.sqLiteHelper.getData("SELECT id FROM FOOD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();

                            while(c.moveToNext()){

                                arrID.add(c.getInt(0));
                            }

                            showDialogUpdate(problemes_list_activity.this, arrID.get(i));

                        }else {

                            Cursor c = Signaler_Probleme.sqLiteHelper.getData("SELECT id FROM FOOD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }

                            showDialogDelete(arrID.get(i));
                        }
                    }
                });

                dialog.show();
                return true;
            }
        });
    }

    private void showDialogUpdate(Activity activity, final int position){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_probleme);

        imageViewFood = (ImageView) dialog.findViewById(R.id.imageViewUpdate);
        final EditText edtPlato = (EditText) dialog.findViewById(R.id.etPlatoUpdatel);
        final EditText edtPrecio = (EditText) dialog.findViewById(R.id.etPrecioUpdatel);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);

        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(
                        problemes_list_activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    Signaler_Probleme.sqLiteHelper.updateData(
                            edtPlato.getText().toString().trim(),
                            edtPrecio.getText().toString().trim(),
                            Signaler_Probleme.imageViewToByte(imageViewFood),
                            position
                    );

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Successfully", Toast.LENGTH_SHORT).show();

                }catch (Exception error){

                    Log.e("Update Error:", error.getMessage());
                }
                UpdateFoodList();
            }
        });

    }

    private void showDialogDelete(final int idFood) {

        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(problemes_list_activity.this);

        dialogDelete.setTitle("Atention");
        dialogDelete.setMessage("Esta seguro que desea eliminar la foto");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try{
                    Signaler_Probleme.sqLiteHelper.deleteData(idFood);
                    Toast.makeText(getApplicationContext(), "Bien Eliminado!!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                    Log.e("Error", e.getMessage());
                }

                UpdateFoodList();
            }
        });

        dialogDelete.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        dialogDelete.show();
    }

    private void UpdateFoodList(){

        Cursor cursor = Signaler_Probleme.sqLiteHelper.getData("SELECT * FROM FOOD");
        list.clear();

        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String plato = cursor.getString(1);
            String precio = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new problemes(id, plato, precio, image));

        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){

            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }else{

                Toast.makeText(getApplicationContext(), "No tiene permisos paraa acceder a los archivos", Toast.LENGTH_SHORT).show();

            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data !=null){

            Uri uri= data.getData();

            try{

                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                imageViewFood.setImageBitmap(bitmap);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
