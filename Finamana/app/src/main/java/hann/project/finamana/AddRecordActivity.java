package hann.project.finamana;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hann.project.finamana.controllers.TableManager;
import hann.project.finamana.entities.Record;
import hann.project.finamana.entities.RecordTable;

public class AddRecordActivity extends AppCompatActivity {
    EditText description;
    Spinner spinnerCategory;
    EditText moneyAmount;
    RadioGroup rdgMoney;
    RadioButton rdRevenue;
    RadioButton rdExpense;
    TableManager manager;
    int tableId;
    EditText recordDate;
    Intent fromTableDetialsIntent;
    SimpleDateFormat dateFormatter;
    //private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fromTableDetialsIntent = getIntent();
        tableId = fromTableDetialsIntent.getExtras().getInt("tableId");


        manager = new TableManager(this);
        //INIT SECTION
        description = (EditText)findViewById(R.id.txtDescription);
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        moneyAmount = (EditText)findViewById(R.id.txtMoneyAmount);
        rdgMoney = (RadioGroup)findViewById(R.id.rdgMoney);
        recordDate = (EditText)findViewById(R.id.txtRecordDate);
        rdRevenue = (RadioButton)findViewById(R.id.rdRevenue);
        rdExpense = (RadioButton)findViewById(R.id.rdExpense);

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
        recordDate.setEnabled(false);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        recordDate.setText(dateFormatter.format(new Date()));
        rdgMoney.check(R.id.rdRevenue);
        final Record.CATEGORY[] earnCategories = {Record.CATEGORY.SALARY,Record.CATEGORY.OTHER,Record.CATEGORY.DEBT, Record.CATEGORY.PROFIT};

        final Record.CATEGORY[] payCategories = {Record.CATEGORY.FOOD,Record.CATEGORY.BEVERAGE,Record.CATEGORY.FUEL
                                         ,Record.CATEGORY.OUTFIT,Record.CATEGORY.SHOPPING,Record.CATEGORY.ENTERTAINMENT
                                         ,Record.CATEGORY.OTHER, Record.CATEGORY.DEBT_PAY};
        if(rdRevenue.isChecked()) {
            spinnerCategory.setAdapter(new ArrayAdapter<Record.CATEGORY>(this, R.layout.support_simple_spinner_dropdown_item, earnCategories));
        }else{
            spinnerCategory.setAdapter(new ArrayAdapter<Record.CATEGORY>(this, R.layout.support_simple_spinner_dropdown_item, payCategories));
        }
        rdRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerCategory.setAdapter(new ArrayAdapter<Record.CATEGORY>(AddRecordActivity.this
                                                                            , R.layout.support_simple_spinner_dropdown_item
                                                                            , earnCategories));
            }
        });
        rdExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerCategory.setAdapter(new ArrayAdapter<Record.CATEGORY>(AddRecordActivity.this
                                                                           , R.layout.support_simple_spinner_dropdown_item
                                                                           , payCategories));
            }
        });
        //DATE PICKER

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


    }

    public void clickChooseDate(View view){
        DatePickerDialog dialog = new DatePickerDialog(AddRecordActivity.this, myDateListener,
                year, month, day);
        dialog.setTitle("Arrival Day");
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {

                    recordDate.setText(arg1+"-"+(arg2+1)+"-"+arg3);
                }
            };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_record_menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.btnDone:
                if(validateInput()) {
                    time = calendar.getTimeInMillis(); // For Sorting
                    Record record = prepareRecord();
                    if (manager.addRecordToTable(record)) {

                        Toast.makeText(this, "Successfully added a new record to table.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to add a new record to table.", Toast.LENGTH_SHORT).show();
                    }
                    Intent backToTableDetailsIntent = new Intent(AddRecordActivity.this, TableDetailsActivity.class);
                    backToTableDetailsIntent.putExtra("tableId", tableId);
                    startActivity(backToTableDetailsIntent);
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private Record prepareRecord(){
        Record record;
        long dateInLong=0;

        try{
        dateInLong = dateFormatter.parse(recordDate.getText().toString()).getTime();
        }catch (ParseException e){
            Log.d("AddRecordActivity",e.getMessage());
        }
        String description = this.description.getText().toString();
        Double moneyAmount = Double.parseDouble(this.moneyAmount.getText().toString());
        Record.CATEGORY category = (Record.CATEGORY) spinnerCategory.getSelectedItem();
        record = new Record(dateInLong,description,tableId,category);

        int checkedId = rdgMoney.getCheckedRadioButtonId();
        switch (checkedId){
            case R.id.rdExpense:
                record.setExpense(moneyAmount);
                break;
            case R.id.rdRevenue:
                record.setRevenue(moneyAmount);
                break;
            default:
                break;
        }
        return record;
    }
    private boolean validateInput(){
        boolean result = true;
        if (moneyAmount.getText().toString().equals("0")) {
            moneyAmount.setError("Oh common!! No transaction equals 0.");
            result = false;
        }
        if (moneyAmount.getText().toString().equals("")) {
            moneyAmount.setError("Hey hey!! Transaction must have a change on money.");
            result = false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {

            Date date = formatter.parse(recordDate.getText().toString());
            formatter = new SimpleDateFormat("MMMM-yyyy");
            String inputDateMonth = formatter.format(date);
            String tableMonth = fromTableDetialsIntent.getExtras().getString("month","");
            int tableYear = fromTableDetialsIntent.getExtras().getInt("year",0);

            if(!inputDateMonth.equals(tableMonth+"-"+tableYear)){
                TextView errDateError = (TextView)findViewById(R.id.errDateError);
                errDateError.setText("This table is "+tableMonth+"_"+tableYear);
                result = false;
            }else{
                TextView errDateError = (TextView)findViewById(R.id.errDateError);
                errDateError.setText("");
            }
        }catch (ParseException e){
            Log.d("RecordDetailsActivity","ParseException: "+e.getMessage() );
        }
        return result;
    }
}
