package com.example.huiweidong.Reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
