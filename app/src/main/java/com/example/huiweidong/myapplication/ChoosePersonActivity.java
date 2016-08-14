package com.example.huiweidong.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;



public class ChoosePersonActivity extends AppCompatActivity {
    public Intent i = new Intent(this, MainActivity.class);
    ListView list;
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    Bundle bundle = new Bundle();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_person);

        list = (ListView) findViewById(R.id.listView2);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        //setListAdapter(adapter);
        list.setAdapter(adapter);
        getPersonsFromContacts();
    }

    public void getPersonsFromContacts() {
        // Do something in response to button
        // get the whole table
        Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                //get name
                int nameFieldColumnIndex = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = c.getString(nameFieldColumnIndex);
                //get nummber
                int contactId = c.getColumnIndex(ContactsContract.Contacts._ID);
                String id = c.getString(contactId);


                Log.i("DEBUG", name);
                Log.i("DEBUG", id);
                Log.i("DEBUG", getPhoneNumber(id));
                listItems.add(name + id + "\n" + getPhoneNumber(id));

            }
        }
        c.close();
    }

   public String getPhoneNumber(String id)
    {
        String number = "";
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone._ID + " = " + id, null, null);

        if(phones.getCount() > 0)
        {
           while(phones.moveToNext())
            {
                number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //System.out.println(number);
            }

        }

        phones.close();

        return number;
    }

}
