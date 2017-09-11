package com.example.huiweidong.Reminder.com.example.huiweidong.Reminder.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.huiweidong.Reminder.Database;
import com.example.huiweidong.Reminder.DateOfDay;
import com.example.huiweidong.Reminder.MyAlert;
import com.example.huiweidong.Reminder.R;
import com.example.huiweidong.Reminder.RandomDate;

import java.text.ParseException;
import java.util.Calendar;

public class SetTimeActivity extends AppCompatActivity {

    private static final int DIALOG_ID = 0;
    public static int nr1;
    public static int nr2;
    public static String inteval_value;
    public static int repeatNr1;
    private static int year_x, month_x, day_x;
    AlertDialog.Builder builder = null;
    private LinearLayout ll1 = null;
    private LinearLayout ll2 = null;
    private LinearLayout ll3 = null;
    private EditText et_nr1;
    private EditText et_nr2;
    //TODO: this edittext is only for date
    private EditText et_date;
    private String startDate;
    private Spinner spinner;
    private CheckBox checkBox;
    private Button b_confirm;
    private Button buttonCancel;
    private String randomDate = null;
    //datapicker get the current date
    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            et_date.setText(day_x + "." + month_x + "." + year_x);
        }
    };

    private static int getNr(EditText et) {
        String s = et.getText().toString();
        return Integer.parseInt(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        setDefaultDate();
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);

        et_nr1 = (EditText) findViewById(R.id.et_nr1);
        et_nr2 = (EditText) findViewById(R.id.et_nr2);

        setStartDate();
        setSpinner();
        setCheckBox();
        showDialogOnImageButton();
        setConfirmButton();
    }

    /**
     * set actuell date als default date in edittext
     */
    private void setStartDate() {
        et_date = (EditText) findViewById(R.id.et_date);
        et_date.setHint(DateOfDay.getDateOfDay());
    }

    //set the current date for the calender, otherweise calender starts with year 1900
    private void setDefaultDate() {
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
    }

    /*
       click on calander Bild, wird der aktuelle Datum angezeigt.
        */
    private void showDialogOnImageButton() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_calander);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    @Override
    //创建Dialog（对话框）类
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListner, year_x, month_x, day_x);

        return null;
    }

    /*private void setAdapter(Spinner s){
        adapter = ArrayAdapter.createFromResource(this, R.array.intervall, android.R.layout.simple_spinner_item);
        adapter_repeatnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter_repeatnr);
    }*/

    private void setSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);
        String[] inteval = getResources().getStringArray(R.array.intervall);
        //intSpinner1.setPrompt("eine Zahl auswählen");
        ArrayAdapter<String> adapter_repeatnr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, inteval);
        adapter_repeatnr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inteval_value = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MyAlert.myAlert(SetTimeActivity.this, "info not complett");
            }
        });

    }

    private void setCheckBox() {
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    ll3.setVisibility(View.VISIBLE);
                } else if (isChecked == false) {
                    ll3.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    //click confirm button, return result back to Mainactivity

    private void setConfirmButton() {
        b_confirm = (Button) findViewById(R.id.b_confirm);

        // Toast.makeText(this, "jinru Button", Toast.LENGTH_LONG).show();
        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addDataInDB();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        // AddData(view);
    }

    private void addDataInDB() throws ParseException {

        startDate = String.valueOf(et_date.getText());
//
//        //Toast.makeText(this, startDate, Toast.LENGTH_LONG).show();
//        //get nr1
        if (!et_nr1.getText().toString().isEmpty() && !et_nr2.getText().toString().isEmpty()) {

        nr1 = getNr(et_nr1);
        nr2 = getNr(et_nr2);
            RandomDate randomDate = new RandomDate();
            randomDate.setRandomDate(startDate, nr1, nr2);
            String rd = randomDate.getRandomDate();
            Database.getInstance(SetTimeActivity.this).insertData(
                    ChoosePersonActivity.name, startDate,
                    nr1,
                    inteval_value, "ja",
                    nr2, rd);
            confirmDone();
        } else {
            MyAlert.myAlert(SetTimeActivity.this, "Please fill all infos!");
        }

    }


    private void confirmDone() {
        builder = new AlertDialog.Builder(SetTimeActivity.this);
        builder.setTitle("");
        builder.setIcon(R.drawable.done);
        builder.setMessage("Done!");
        builder.setNeutralButton("Go back to homesite", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(SetTimeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        builder.create().show();
    }


//        DatabaseClass.getInstance(SetTimeActivity.this).insertData(
//                ChoosePersonActivity.name, String.valueOf(et_startdate.getText()),
//                //String.valueOf(et_repeatnr.getText()),
//                //"spinner内容", "1",
//                //String.valueOf(et_intervalnr.getText()), setRadomDate);
//    }

//    public void alertInfoMissed(){
//        builder = new AlertDialog.Builder(SetTimeActivity.this);
//
//        builder.setMessage("Some information are not filled yet!");
//        builder.setPositiveButton("ok, go back to setting", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.setNegativeButton("no, i give ab", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Intent intent = new Intent(SetTimeActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        builder.create().show();
//    }

   /* public void alertNotSetSharp() {
        builder = new AlertDialog.Builder(SetTimeActivity.this);
        builder.setMessage("Are you sure you want to set the reminder unsharp?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                DatabaseClass.getInstance(SetTimeActivity.this).insertData(ChoosePersonActivity.name, String.valueOf(et_intervalnr.getText()),
                        String.valueOf(et_startdate.getText()), chosedButton1.getText().toString(), "0",
                        "", "");
                confirmDone();
            }
        });

        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }*/


    /**
     * calculate a radom Date
     */


}






