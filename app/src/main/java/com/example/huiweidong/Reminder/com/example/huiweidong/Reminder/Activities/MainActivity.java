package com.example.huiweidong.Reminder.com.example.huiweidong.Reminder.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.huiweidong.Reminder.Database;
import com.example.huiweidong.Reminder.LongtermAlarm;
import com.example.huiweidong.Reminder.R;


public class MainActivity extends AppCompatActivity {

    ListView list;
    SimpleCursorAdapter adapter;
    long _id;

    Cursor c;
    Database db;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //open the datenbank
        db = Database.getInstance(this);
        db.onOpen(db.getReadableDatabase());

        list = (ListView) findViewById(R.id.listView);

        fillAdapter();

        ItemOnClick();

        //wenn das programm an ist, dann soll alarm immer im Hitergrund laufen
        Intent i = new Intent(this, LongtermAlarm.class);
        this.startService(i);

    }

    private void fillAdapter() {

        c = db.getAllData();

        if (c.getCount() == 0) {
            Toast.makeText(this, "You have not set any contact information", Toast.LENGTH_LONG).show();
        } else c.moveToFirst();

        adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.listview_row, c,
                new String[]{c.getColumnName(1), c.getColumnName(2), c.getColumnName(3), c.getColumnName(4), c.getColumnName(6), c.getColumnName(7)},
                new int[]{R.id.name, R.id.date, R.id.repeatsNr, R.id.repeatsInt, R.id.unsharpenNr, R.id.radomDate},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        list.setAdapter(adapter);

//// TODO: 19.10.16 list in alphabetical order
    }

    private void ItemOnClick() {

//create a menu for "delete, cancel"
        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                //// TODO: 21.12.16 : edit implementieren
                menu.add(0, 0, 0, "edit");
                menu.add(0, 1, 0, "delete");
                menu.add(0, 2, 0, "cancel");
            }
        });
    }


    // long click on one item
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        _id = info.id;


        switch (item.getItemId()) {
            case 0: //edit
                clickOnEdit(_id);
                break;

            case 1: //delete
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you really want to delete it？");
                builder.setIcon(R.drawable.notification_warning);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Database.getInstance(MainActivity.this).deleteData(Long.toString(_id));

                        dialog.dismiss();

                        fillAdapter();

                        adapter.notifyDataSetChanged();

                    }
                });


                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                builder.create().show();
                break;


            case 2:
                // cancel

                break;

            default:
                break;
        }

        return super.onContextItemSelected(item);

    }


    // click on button add new person
    public void openChoosePersonActivity(View view) {
        Intent intent = new Intent(this, ChoosePersonActivity.class);

        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    //TODO: implementieren!
    public void clickOnEdit(long _id) {

        String[] infos = Database.getInstance(MainActivity.this).getDataFromARow(Long.toString(_id));


        b = new Bundle(); //专门用于不同activity之间传递数据
        b.putString("ACTION", "Edit");//键值方式
        // b.putLong("ID", _id);


        b.putStringArray("Info", infos);


        Intent intent = new Intent(this, SetTimeActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

}




