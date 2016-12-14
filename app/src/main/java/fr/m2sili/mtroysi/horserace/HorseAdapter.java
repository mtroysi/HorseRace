package fr.m2sili.mtroysi.horserace;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Morgane TROYSI on 12/9/16.
 */

public class HorseAdapter extends ArrayAdapter<Horse> {

    private Context context;
    private int resource;

    public HorseAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, parent, false);

        Horse horse = getItem(position);

        ProgressBar bar = (ProgressBar) v.findViewById(R.id.progress);
        ImageView icon = (ImageView) v.findViewById(R.id.horse_icon);
        TextView number = (TextView)v.findViewById(R.id.horse_num);
        TextView arrived = (TextView)v.findViewById(R.id.horse_arrived);


        number.setText(String.valueOf(horse.getNum()));

        if(horse.isInProgress() || horse.getProgression() == 0) {
            bar.setMax(horse.getTotal_distance());
            bar.setProgress(horse.getProgression());
            arrived.setVisibility(View.GONE);
        } else {
            bar.setVisibility(View.GONE);
            arrived.setVisibility(View.VISIBLE);
        }

        if(horse.isBoosted()) {
            bar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }


        AssetManager asset = context.getAssets();
        String imgPath = "image_cheval.png";
        try {
            InputStream ims = asset.open(imgPath);
            Drawable d = Drawable.createFromStream(ims, null);
            icon.setImageDrawable(d);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void add(Horse object) {
        object.setNum(this.getCount() + 1);
        super.add(object);
    }
}
