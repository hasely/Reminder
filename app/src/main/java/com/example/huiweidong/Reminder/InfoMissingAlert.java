package com.example.huiweidong.Reminder;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by HuiweiDong on 21.12.16.
 * alert if info not komplet
 */

public class InfoMissingAlert {

    public static AlertDialog.Builder builder = null;


    public static void infoMissing(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setMessage("Info nicht vollständig");
        builder.setPositiveButton("zurück", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

    }

}
