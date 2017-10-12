package hann.project.finamana.controllers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import hann.project.finamana.R;

/**
 * Created by hann on 12/10/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;

    public ImageAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView img;
        if(view == null){
            img = new ImageView(context);
            img.setLayoutParams(new GridView.LayoutParams(85, 85));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setPadding(8,8,8,8);
        }else{
            img =(ImageView)view;
        }
        img.setImageResource(imgIDs[i]);
        return img;
    }
    public Integer[] imgIDs = {R.drawable.finamanalogo
                              ,R.mipmap.money
                              ,R.mipmap.drone
                              ,R.mipmap.music_note
                              ,R.mipmap.umbrealla};
}
