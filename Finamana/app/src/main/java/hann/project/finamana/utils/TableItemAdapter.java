package hann.project.finamana.utils;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

import hann.project.finamana.R;
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
            tblHolder.btnDeleteTable = (ImageButton)view.findViewById(R.id.btnDeleteTable);
            view.setTag(tblHolder);
        }else{
            tblHolder = (TableViewHoler) view.getTag();
        }
        RecordTable table = tableItemList.get(i);
        String title = table.getMonth().toString() +"_"+ table.getYear();
        long createdDate = table.getCreatedDate();
        tblHolder.title.setText(title);
        tblHolder.createdDate.setText(new Date(createdDate).toString());//Might be bugged
        tblHolder.owner.setText(table.getUsername());// Change to fullname later
        tblHolder.btnDeleteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Perform Delete SQL by tableId (Remember to Delete all the relative Records).
            }
        });
        return view;
    }

static class TableViewHoler{
    TextView title;
    TextView createdDate;
    TextView owner;
    ImageButton btnDeleteTable;
    }
}