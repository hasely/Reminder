package com.example.huiweidong.Reminder;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;



public class ChoosePersonActivity extends AppCompatActivity {
    ListView list;
    SearchView search;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_person);

        list = (ListView) findViewById(R.id.listView2);
        search = (SearchView) findViewById(R.id.searchView);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        //setListAdapter(adapter);
        list.setAdapter(adapter);


        // This routine handles the "which list Item is clicked" problem
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View myView, int myItemInt, long mylng) {
                String selectedFromList = (String) (list.getItemAtPosition(myItemInt));
                Log.i("DEBUG", selectedFromList);
                Intent intent = new Intent(ChoosePersonActivity.this, SetTime.class);
                startActivity(intent);
            }
        });
        getPersonsFromContacts();
    }

    /**
     * gets the contacts
     */
    public void getPersonsFromContacts() {
        // Do something in response to button
        // get the whole table
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);


        if (c != null) {
            while (c.moveToNext()) {
                //get name
                int nameFieldColumnIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = c.getString(nameFieldColumnIndex);
                //get nummber
                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                        "DISPLAY_NAME = '" + name + "'", null, null);

                if (cursor.moveToFirst()) {
                    String contactId =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    //
                    //  Get all phone numbers.
                    //
                    Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        switch (type) {
                            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                listItems.add(name + "\n" + "home" + number);

                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                listItems.add(name + "\n" + number);
                                break;
                            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                // do something with the Work number here...
                                break;
                        }
                    }
                    phones.close();

                    //Log.i("DEBUG", name);
                    //listItems.add(name + id + "\n" + getPhoneNumber(id));

                }
            }
            c.close();
        }


    }

}
