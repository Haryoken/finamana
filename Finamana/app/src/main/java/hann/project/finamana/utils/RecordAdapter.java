package hann.project.finamana.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hann.project.finamana.R;
import hann.project.finamana.entities.Record;

/**
 * Created by DUCVINH on 21/10/2017.
 */

public class RecordAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    List<Record> recordList;
    public RecordAdapter(Context context,List<Record>recordList){
        this.context=context;
        this.recordList=recordList;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return recordList.get(position).getRecordId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        RecordViewHoler recordHolder = new RecordViewHoler();
        if(view == null){
            view = layoutInflater.inflate(R.layout.item_record,null);

            recordHolder.recordDate = (TextView)view.findViewById(R.id.txtRecordDate);
            recordHolder.category = (TextView)view.findViewById(R.id.txtCategory);
            recordHolder.description =(TextView)view.findViewById(R.id.txtDescription);
            recordHolder.moneyAmount = (TextView)view.findViewById(R.id.txtRecordMoneyAmount);

            recordHolder.btnEdit=(ImageButton)view.findViewById(R.id.btnEditRecord);
            recordHolder.btnRemove=(ImageButton)view.findViewById(R.id.btnRemoveRecord);

            view.setTag(recordHolder);
        }else{
            recordHolder = (RecordViewHoler)view.getTag();
        }

        //Set values for a record
        final Record record = recordList.get(position);

        SimpleDateFormat formatter = new SimpleDateFormat("EE, MMM dd");
        String strDate = formatter.format(new Date(record.getRecordDate()));
        recordHolder.recordDate.setText(strDate);

        recordHolder.category.setText(record.getCategory().toString());
        recordHolder.description.setText(record.getDescription());

        if (record.getExpense() != 0){
            recordHolder.moneyAmount.setText("-" + String.format("%,.2f",record.getExpense()));
            recordHolder.moneyAmount.setTextColor(Color.RED);
        } else{
            recordHolder.moneyAmount.setText("+" + String.format("%,.2f",record.getRevenue()));
            recordHolder.moneyAmount.setTextColor(Color.rgb(100,150,100));
        }





        return view;
    }

    static class RecordViewHoler{
        TextView recordDate;
        TextView category;
        TextView description;
        TextView moneyAmount;
        ImageButton btnEdit;
        ImageButton btnRemove;
    }
}
