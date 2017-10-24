package hann.project.finamana.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hann.project.finamana.R;
import hann.project.finamana.controllers.TableListManager;
import hann.project.finamana.entities.RecordTable;

/**
 * Created by hann on 16/10/2017.
 */

public class TableItemAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    List<RecordTable> tableItemList;

    public TableItemAdapter(Context context,List<RecordTable> tableItemList){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.tableItemList = tableItemList;
    }
    @Override
    public int getCount() {
        return tableItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return tableItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tableItemList.get(i).getTableId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TableViewHoler tblHolder = new TableViewHoler();
        if(view == null){
            view = layoutInflater.inflate(R.layout.item_table,null);

            tblHolder.title = (TextView)view.findViewById(R.id.txtTableTitle);
            tblHolder.createdDate = (TextView)view.findViewById(R.id.txtCreatedDate);
            tblHolder.owner = (TextView)view.findViewById(R.id.txtTableOwner);
            tblHolder.odd = (TextView)view.findViewById(R.id.txtTableOdd);





            view.setTag(tblHolder);
        }else{
            tblHolder = (TableViewHoler) view.getTag();
        }

        final RecordTable table = tableItemList.get(i);
        String title = RecordTable.parseMONTH(table.getMonth()).toString() +"_"+ table.getYear();
        long createdDate = table.getCreatedDate();
        Double odd = table.getOdd();

        tblHolder.title.setText(title);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm  EE, MMM-dd-yyyy");
        String strDate = formatter.format(new Date(table.getCreatedDate()));
        tblHolder.createdDate.setText(strDate);

        //odd

        if(odd > 0){
            tblHolder.odd.setTextColor(Color.rgb(100,150,100));//GREEN
            tblHolder.odd.setText("+"+String.format("%,.2f", odd));
        } else if(odd < 0){
            tblHolder.odd.setTextColor(Color.RED);
            tblHolder.odd.setText("-"+String.format("%,.2f", odd));
        } else if(odd == 0){
            tblHolder.odd.setText("0");
        }
        tblHolder.owner.setText(table.getUsername());// Change to fullname later





        return view;
    }

static class TableViewHoler{
    TextView title;
    TextView createdDate;
    TextView owner;
    TextView odd;

    }
}