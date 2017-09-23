package com.example.huiweidong.Reminder.com.example.huiweidong.Reminder.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

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
    public static int temp = 15;
    public static String inteval_value = "Week(s)";
    private static int year_x, month_x, day_x;
    AlertDialog.Builder builder = null;
    private LinearLayout ll1 = null;
    private LinearLayout ll2 = null;
    private LinearLayout ll3 = null;
    private LinearLayout ll4 = null;
    private EditText et_date;
    private String startDate;

    private MyNumberPicker numberPicker1;
    private MyNumberPicker numberPicker2;
    private MyNumberPicker intervalPicker;
    private String[] forIntervalPicker = {"Month(s)", "Week(s)", "Day(s)"};

    private CheckBox checkBox;
    private Button b_confirm;
    private Button b_Cancel;

    private String oldDate;
    private String randomDate = null;
    /**
     * user chooses a date from DataPicker, et_date shows this date
     */
    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            et_date.setText(day_x + "." + month_x + "." + year_x);
            startDate = et_date.getText().toString();
        }
    };
    private View view;

    private static int getNr(EditText et) {
        String s = et.getText().toString();
        return Integer.parseInt(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        getAllElement();
        setTextEdit();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle.containsKey("ACTION") && bundle.getString("ACTION").equals("Edit")) {

//            Log.d("TAG","--------------------------------------------------------------");
//            Log.d("TAG", String.valueOf(info.length));
//            Log.d("TAG", info[0] );
//            Log.d("TAG",info[1]);
//            Log.d("TAG",info[2]);     Log.d("TAG",info[3]);
//            Log.d("TAG","--------------------------------------------------------------");

            loadOldInfo(bundle);
        } else {
            initiateInfo();
        }

        oldDate = String.valueOf(et_date.getHint());
    }

    private void getAllElement() {
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        et_date = (EditText) findViewById(R.id.et_date);
        numberPicker1 = (MyNumberPicker) findViewById(R.id.numberPicker1);
        intervalPicker = (MyNumberPicker) findViewById(R.id.intervalPicker);
        numberPicker2 = (MyNumberPicker) findViewById(R.id.numberPicker2);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        b_confirm = (Button) findViewById(R.id.b_confirm);
        b_Cancel = (Button) findViewById(R.id.b_cancel);
    }

    private void initiateInfo() {
        setNumberPicker(numberPicker1, 15);
        inteval_value = setIntervalPicker(intervalPicker, null);
        setNumberPicker(numberPicker2, 15);
        setCheckBox();
        setConfirmButton();
        setCancelButton();
    }

    /**
     * this method is used with Edit together.
     * if info for a person already setted, then load setted info on this page
     *
     * @param b bundel with setted infomation
     */
    private void loadOldInfo(Bundle b) {
        String[] info = b.getStringArray("Info");
        et_date.setHint(info[0]);
        setNumberPicker(numberPicker1, Integer.valueOf(info[1]));
        Log.d("TAG", "--------------------------------------------------------------");
        Log.d("TAG", "nr1 = " + String.valueOf(nr1));
        Log.d("TAG", "--------------------------------------------------------------");

        setIntervalPicker(intervalPicker, info[2]);

        if (Integer.valueOf(info[3]) != 0) {
            ll3.setVisibility(View.VISIBLE);
            checkBox.setChecked(true);
            setNumberPicker(numberPicker2, Integer.valueOf(info[3]));
        }

        //oldDate = String.valueOf(et_date.getText());

    }

    private void setTextEdit() {
        et_date.requestFocus();
        et_date.setHint(DateOfDay.getDateOfDay());
        setDefaultDate();
        //setInfo();
        et_date.setInputType(InputType.TYPE_NULL); //hide soft keyboard
        et_date.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        }));
        startDate = String.valueOf(et_date.getText());
    }

    /**
     * set 2 nummerPickers
     */
    private void setNumberPicker(MyNumberPicker numberPicker, int i) {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(30);
        numberPicker.setValue(i);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                temp = newVal;
                Log.d("TAG", "--------------------------------------------------------------");
                Log.d("TAG", "temp = " + String.valueOf(temp));
                Log.d("TAG", "--------------------------------------------------------------");
                if (picker.equals(numberPicker1)) {
                    nr1 = temp;
                }
                nr2 = temp;
            }
        });

    }

    /**
     * set interval Picker
     */

    private String setIntervalPicker(MyNumberPicker intervalPicker, String value) {
        intervalPicker.setDisplayedValues(forIntervalPicker);
        intervalPicker.setMinValue(0);
        intervalPicker.setMaxValue(forIntervalPicker.length - 1);
        if (value != null) {
            switch (value) {
                case "Month(s)":
                    intervalPicker.setValue(2);
                case "Week(s)":
                    intervalPicker.setValue(1);
                case "Day(s)":
                    intervalPicker.setValue(0);
            }
        } else {
            intervalPicker.setValue(1);
        }
        intervalPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                temp = newVal;
            }
        });

        if (temp == 0) {
            inteval_value = "Day(s)";
        } else {
            inteval_value = "Month(s)";
        }

        return inteval_value;
    }

    /**
     * if first time add info for a person, then load no info on this page
     */
    private void setInfo() {
        et_date.setHint(DateOfDay.getDateOfDay());
        setCheckBox();
    }

    //set the current date for the calender, otherweise calender starts with year 1900
    private void setDefaultDate() {
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
    }

    //click confirm button, return result back to Mainactivity

    /**
     * click on clock pic, datapicker is showed. and the actual date will be marked on data picker
     */
//    private void showDialogOnImageButton() {
//        ImageButton imageButton = (ImageButton) findViewById(R.id.ib_calander);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(DIALOG_ID);
//            }
//        });
//    }

    @Override
    //创建Dialog（对话框）类
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dpickerListner, year_x, month_x, day_x);
        return null;
    }

    private void setCheckBox() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    ll3.setVisibility(View.VISIBLE);
                    checkBox.setTextColor(getResources().getColor(R.color.black));
                } else if (isChecked == false) {
                    ll3.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void setConfirmButton() {
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

    private void setCancelButton() {
        // Toast.makeText(this, "jinru Button", Toast.LENGTH_LONG).show();
        b_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetTimeActivity.this, MainActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });
    }

    public void openMainActivity(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        // AddData(view);
    }

    private void addDataInDB() throws ParseException {
        //check if all info entered, if not, they should be filled
        boolean startDateEntered = hasContent(et_date);
        //boolean intervalDaysNrEntered = hasContent(et_nr2);
        boolean checkboxChecked = isChecked(checkBox);
// if all info entered, then save info in DB
        if (startDateEntered && checkboxChecked) {
            RandomDate randomDate = new RandomDate();
            randomDate.setRandomDate(startDate, nr1, nr2);
            String rd = randomDate.getRandomDate();

            Log.d("TAG", "--------------------------------------------------------------");
            Log.d("TAG", "save date in Db: nr1 = " + String.valueOf(nr1));
            Log.d("TAG", "--------------------------------------------------------------");


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


    /**
     * check if start date entered
     */
    private boolean hasContent(EditText et) {
        if (et.getText().length() == 0) {
            et.setHint("?");
            et.setHintTextColor(getResources().getColor(R.color.red));
            return false;
        } else {
            et.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
    }

    /**
     * chech if checkbox is checked
     */
    private boolean isChecked(CheckBox checkBox) {
        if (checkBox.isChecked() == false) {
            checkBox.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        return true;
    }

    private boolean startDateChanged(String oldDate, String newDate) {
        if (oldDate.equals(newDate)) {
            return true;
        } else {
            MyAlert.myAlert(SetTimeActivity.this, "Startdate is not set");
            return false;
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






