package fr.utt.if26.troyes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Elkin R on 17/12/2017.
 */

public class problemesListAdapter extends BaseAdapter {


    private Context context;
    private int layout;
    private ArrayList<problemes> fooddslist;


    public problemesListAdapter(Context context, int layout, ArrayList<problemes> fooddslist) {
        this.context = context;
        this.layout = layout;
        this.fooddslist = fooddslist;
    }

    @Override
    public int getCount() {
        return fooddslist.size();
    }

    @Override
    public Object getItem(int position) {
        return fooddslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{

        ImageView imageView;
        TextView txtplato, txtprecio;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row=view;
        ViewHolder holder = new ViewHolder();

        if(row == null){

            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtplato = (TextView) row.findViewById(R.id.textView);
            holder.txtprecio = (TextView) row.findViewById(R.id.textView2);
            holder.imageView = (ImageView) row.findViewById(R.id.imageView);

            row.setTag(holder);


        }else {

            holder = (ViewHolder) row.getTag();
        }

        problemes problemes= fooddslist.get(position);

        holder.txtplato.setText(problemes.getPlato());
        holder.txtprecio.setText(problemes.getPrecio());

        byte[] foodImage = problemes.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
