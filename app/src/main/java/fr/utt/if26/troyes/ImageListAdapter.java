package fr.utt.if26.troyes;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Elkin R on 10/12/2017.
 */

public class ImageListAdapter extends ArrayAdapter<ImageUpload> {



    private Activity context;
    private int resource;
    private List<ImageUpload> listimage;

    public  ImageListAdapter(@NonNull Activity context, int resource, List<ImageUpload> objects){

        super(context, resource, objects);
        this.context= context;
        this.resource=resource;
        listimage= objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();

        View v= layoutInflater.inflate(resource, null);
        TextView tvname= (TextView) v.findViewById(R.id.tvImageNameL);
        TextView tvDescription= (TextView) v.findViewById(R.id.tvImageDescriptionL);

        ImageView img=(ImageView)v.findViewById(R.id.imgViewL);

        tvname.setText(listimage.get(position).getName());
        tvDescription.setText(listimage.get(position).getDescription());
        Glide.with(context).load(listimage.get(position).getUrl()).into(img);

        return v;
    }

}
