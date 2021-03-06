package fr.utt.if26.troyes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by Elkin R on 17/12/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper{

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){

        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String plato, String precio, byte[] imagen){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO FOOD VALUES (NULL, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, plato);
        statement.bindString(2, precio);
        statement.bindBlob(3, imagen);

        statement.executeInsert();

    }

    public void updateData(String plato, String precio, byte[] imagen, int id){

        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE FOOD SET plato = ?, precio = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, plato);
        statement.bindString(2, precio);
        statement.bindBlob(3, imagen);
        statement.bindDouble(4, (double)id);

        statement.execute();
        database.close();
    }

    public  void deleteData(int id){

        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM FOOD WHERE id = ?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        database.close();
    }


    public Cursor getData(String sql){

        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
