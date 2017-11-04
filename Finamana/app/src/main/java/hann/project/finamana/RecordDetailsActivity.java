package hann.project.finamana;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hann.project.finamana.controllers.TableListManager;
import hann.project.finamana.controllers.TableManager;
import hann.project.finamana.entities.Record;
import hann.project.finamana.utils.BackupHelper;

public class RecordDetailsActivity extends AppCompatActivity {
    private EditText description;
    private Spinner spinnerCategory;
    private EditText moneyAmount;
    private RadioGroup rdgMoney;
    private RadioButton rdRevenue;
    private RadioButton rdExpense;
    private TableManager manager;
    private Record record;
    private EditText recordDate;
    private TextView btnDeleteRecord;

    private Intent fromTableDetialsIntent;
    private SimpleDateFormat dateFormatter;
    //private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fromTableDetialsIntent = getIntent();
        record = (Record)fromTableDetialsIntent.getExtras().getSerializable("record");


        manager = new TableManager(this);
        //INIT SECTION
        description = (EditText)findViewById(R.id.txtDescription);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        moneyAmount = (EditText)findViewById(R.id.txtMoneyAmount);
        rdgMoney = (RadioGroup)findViewById(R.id.rdgMoney);
        recordDate = (EditText)findViewById(R.id.txtRecordDate);
        rdRevenue = (RadioButton)findViewById(R.id.rdRevenue);
        rdExpense = (RadioButton)findViewById(R.id.rdExpense);
        btnDeleteRecord = (TextView)findViewById(R.id.btnDeleteRecord);


        //VALIDATION SECTION
        description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String text = description.getText().toString();
                if(description.getText().toString().length() > 150){
                    description.setError("Nahh. Make sure you won't tell me longer than 150 characters.");
                }
            }
        });

        moneyAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                validateInput();
            }
        });

        //SETTING SECTION

        description.setText(record.getDescription());
        recordDate.setEnabled(false);
        if(record.getRevenue() > 0){
            rdRevenue.setChecked(true);
            moneyAmount.setText(String.valueOf(record.getRevenue()));
        }else{
            rdExpense.setChecked(true);
            moneyAmount.setText(String.valueOf(record.getExpense()));
        }
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
        recordDate.setText(formatter.format(new Date(record.getRecordDate())));


        final Record.CATEGORY[] earnCategories = {Record.CATEGORY.SALARY,Record.CATEGORY.OTHER,Record.CATEGORY.DEBT, Record.CATEGORY.PROFIT};

        final Record.CATEGORY[] payCategories = {Record.CATEGORY.FOOD,Record.CATEGORY.BEVERAGE,Record.CATEGORY.FUEL
                ,Record.CATEGORY.OUTFIT,Record.CATEGORY.SHOPPING,Record.CATEGORY.ENTERTAINMENT
                ,Record.CATEGORY.OTHER};
        if(rdRevenue.isChecked()) {
            spinnerCategory.setAdapter(new ArrayAdapter<Record.CATEGORY>(this, R.layout.support_simple_spinner_dropdown_item, earnCategories));
        }else{
            spinnerCategory.setAdapter(new ArrayAdapter<Record.CATEGORY>(this, R.layout.support_simple_spinner_dropdown_item, payCategories));
        }
        rdRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerCategory.setAdapter(new ArrayAdapter<Record.CATEGORY>(RecordDetailsActivity.this
                        , R.layout.support_simple_spinner_dropdown_item
                        , earnCategories));
            }
        });
        rdExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerCategory.setAdapter(new ArrayAdapter<Record.CATEGORY>(RecordDetailsActivity.this
                        , R.layout.support_simple_spinner_dropdown_item
                        , payCategories));
            }
        });
        int categoryPosition = manager.getCategoryPosition(record.getCategory(),(ArrayAdapter)spinnerCategory.getAdapter());
        if( categoryPosition> -1){
            spinnerCategory.setSelection(categoryPosition);
        }

        btnDeleteRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(RecordDetailsActivity.this);
                        builder.setMessage("Are you sure to delete this Transaction ?");
                        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                if(manager.removeRecordFromTable(record)){
                                    Intent toTableListIntent = new Intent(RecordDetailsActivity.this, TableDetailsActivity.class);
                                    toTableListIntent.putExtra("tableId",record.getTableId());
                                    startActivity(toTableListIntent);
                                    Toast.makeText(RecordDetailsActivity.this," Record has been successfully removed.",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RecordDetailsActivity.this,"Remove failed.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

        });

    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btnSave:
                if(validateInput()) {
                    Record editedRecord = prepareUpdateRecord();
                    if (manager.updateRecord(editedRecord)) {
                        Intent toTableListIntent = new Intent(RecordDetailsActivity.this, TableDetailsActivity.class);
                        toTableListIntent.putExtra("tableId", record.getTableId());
                        startActivity(toTableListIntent);
                        Toast.makeText(RecordDetailsActivity.this, "Updated successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RecordDetailsActivity.this, "Update failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.record_details_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
    public void clickChooseDate(View view){
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(RecordDetailsActivity.this, recorDetailsDateListener,
                year, month, day);

        dialog.setTitle("Arrival Day");
        dialog.show();
    }
    private DatePickerDialog.OnDateSetListener recorDetailsDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {

                    recordDate.setText(arg1+"-"+(arg2+1)+"-"+arg3);
                }
            };
    private Record prepareUpdateRecord(){
        String description = this.description.getText().toString();
        Double moneyAmount = Double.parseDouble(this.moneyAmount.getText().toString());
        Record.CATEGORY category = (Record.CATEGORY)this.spinnerCategory.getSelectedItem();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date recordDate = null;
        try {
            recordDate = formatter.parse(this.recordDate.getText().toString());
        }catch(ParseException e){
            Log.d("RecrodDetailsActivity", "prepareUpdateRecord: "+e.getMessage());
        }
        long dateInLong = recordDate.getTime();
        int recordId = record.getRecordId();
        Record editedRecord = new Record(dateInLong,recordId,description,this.record.getTableId(),category);
        if(rdExpense.isChecked()){
            editedRecord.setExpense(moneyAmount);
        }
        if(rdRevenue.isChecked()){
            editedRecord.setRevenue(moneyAmount);
        }
        return editedRecord;
    }
    private boolean validateInput(){
        if (moneyAmount.getText().toString().equals("")) {
            moneyAmount.setError("Oh common!! No transaction equals 0.");
            return false;
        }
        if (moneyAmount.getText().toString().equals("0")) {
            moneyAmount.setError("Hey hey!! Transaction must have a change on money.");
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date date = formatter.parse(recordDate.getText().toString());
            formatter = new SimpleDateFormat("MMMM");
            String inputMonth = formatter.format(date);
            String tableMonth = fromTableDetialsIntent.getExtras().getString("month","");
            if(!inputMonth.equals(tableMonth)){
                TextView errDateError = (TextView)findViewById(R.id.errDateError);
                errDateError.setText("Month of this table is "+tableMonth);
                return false;
            }else{
                TextView errDateError = (TextView)findViewById(R.id.errDateError);
                errDateError.setText("");
            }
        }catch (ParseException e){
            Log.d("RecordDetailsActivity","ParseException: "+e.getMessage() );
        }
        return true;
    }
}
