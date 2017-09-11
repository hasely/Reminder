package com.example.huiweidong.Reminder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


public class MyAlert {

    public static AlertDialog.Builder builder = null;

    public static void myAlert(Context context, String alertText) {
        builder = new AlertDialog.Builder(context);
        builder.setMessage(alertText);

        builder.setPositiveButton("zurück", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();
    }

}
