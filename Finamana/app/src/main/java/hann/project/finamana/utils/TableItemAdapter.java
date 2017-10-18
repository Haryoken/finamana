package hann.project.finamana.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hann.project.finamana.R;
import hann.project.finamana.controllers.TableManager;
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

            tblHolder.btnRemoveTbl = (TextView)view.findViewById(R.id.btnRemoveTbl);


            view.setTag(tblHolder);
        }else{
            tblHolder = (TableViewHoler) view.getTag();
        }
        final RecordTable table = tableItemList.get(i);
        String title = RecordTable.parseMONTH(table.getMonth()).toString() +"_"+ table.getYear();
        long createdDate = table.getCreatedDate();
        Double odd = table.getOdd();

        tblHolder.title.setText(title);

            Date date = new Date(createdDate);
            tblHolder.createdDate.setText(date.toString());

        //odd
        tblHolder.odd.setText(String.format("%,.2f", odd));
        if(odd > 0){
            tblHolder.odd.setTextColor(Color.rgb(100,150,100));
        } else {
            tblHolder.odd.setTextColor(Color.RED);
        }
        tblHolder.owner.setText(table.getUsername());// Change to fullname later


        tblHolder.btnRemoveTbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure to delete this Table ?");
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        TableManager manager = new TableManager(context);
                        if(manager.removeTableFromList(table)){
                            tableItemList.remove(table);
                            notifyDataSetChanged();
                            Toast.makeText(context," Table has been successfully removed.",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"Remove failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return view;
    }

static class TableViewHoler{
    TextView title;
    TextView createdDate;
    TextView owner;
    TextView odd;
    TextView btnRemoveTbl;
    }
}