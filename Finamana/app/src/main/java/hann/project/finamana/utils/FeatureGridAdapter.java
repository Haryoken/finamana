package hann.project.finamana.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hann.project.finamana.R;
import hann.project.finamana.entities.MenuItem;

/**
 * Created by DUCVINH on 13/10/2017.
 */

public class FeatureGridAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    List<MenuItem> menuItemList;
    public FeatureGridAdapter(Context context, List<MenuItem> menuItemList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.menuItemList=menuItemList;
    }
    @Override
    public int getCount() {
        return menuItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = layoutInflater.inflate(R.layout.menu_item_layout, null);

            holder.featurePics = (ImageView) view.findViewById(R.id.imgFeaturePicture);
            holder.featureName = (TextView) view.findViewById(R.id.txtFeatureName);
            view.setTag(holder);

        } else {
            holder = (ViewHolder)view.getTag();
        }
        MenuItem item = menuItemList.get(position);

        holder.featureName.setText(item.getName());
        int picId = this.getMipmapResIdByName(item.getPicID());
        holder.featurePics.setImageResource(picId);
        return view;
    }
    //Tìm ID của Image ứng với tên của ảnh (Trong thư mục mipmap).
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("FeatureGridAdapter", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }
    static class ViewHolder {
        ImageView featurePics;
        TextView featureName;

    }
}
