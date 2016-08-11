package com.example.huiweidong.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openChoosePersonActivity(View view){
        Intent intent = new Intent(this, ChoosePersonActivity.class);


        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }


}
