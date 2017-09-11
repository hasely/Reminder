package com.example.huiweidong.Reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Reminder.db";
    private static final String TABLE_NAME = "Reminder_tabledb";
    private static final int DATABASE_VERSION = 6;
    public static String[] infosInARow = null;
    public static String COL_1 = "ID";
    public static String COL_2 = "CONTACT_PERSON";
    public static String COL_3 = "STARTSAT";


    //define variablen --> columm names
    public static Integer COL_4 = Integer.valueOf("REPEATSNR");
    public static String COL_5 = "REPEATSINTERVAL";
    public static String COL_6 = "UNSHARPEN";
    public static Integer COL_7 = Integer.valueOf("UNSHARPENNR");
    public static String COL_8 = "RADOMDATE";
    private static Database myDb;
    SQLiteDatabase db = getReadableDatabase();
    Cursor c = null;


    //Konstructor
    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized Database getInstance(Context context) {
        if (myDb == null) {
            // 指定数据库名为student，需修改时在此修改；此处使用默认工厂；指定版本为1
            myDb = new Database(context.getApplicationContext());
        }
        return myDb;
    }

    @Override
    /**
     *  create a table with listed columm names
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT,CONTACT_PERSON TEXT," +
                "STARTSAT TEXT,REPEATSNR INTEGER,REPEATSINTERVAL TEXT,UNSHARPEN TEXT, " +
                "UNSHARPENNR INTEGER,RADOMDATE TEXT )");
    }

    @Override
    //当数据库版本号发生变化的时候的调用onUpdate
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    /**
     * get the whole table
     *
     * @return
     */
    public Cursor getAllData() {
        //SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("select * from " + TABLE_NAME, null);
        return c;
    }

    /**
     * get a certain line from the table
     *
     * @return
     */
    public Cursor getDataFromARow(String ID) {
        //SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery("SELECT * FROM Reminder_tabledb WHERE _id = ?", new String[]{ID});
        if (c.getCount() > 0) {
            c.moveToFirst();
        }
        //c.getString(2);
        //c.moveToFirst();
        //Log.d("Debug", "---------------------以下是cursor在数据库中读取到的行数------------------------");
        //Log.d("Debug", Integer.toString(c.getCount()));

        //c.getCount();
        //Log.d("Debug", "---------------------以下为判断editext是否为null------------------------");


        db.close();
        return c;
    }

    public String[] getRow(String name) {

        c = db.query(TABLE_NAME, new String[]{COL_2, COL_3, COL_4.toString(), COL_5, COL_7.toString(), COL_8}, "CONTACT_PERSON=?", new String[]{name}, null, null, null);
        while (c.moveToNext()) {
            String nameFound = c.getString(c.getColumnIndex(COL_2));
            if (nameFound.equals(name)) {
                infosInARow = new String[]{COL_2, COL_3, COL_4.toString(), COL_5, COL_7.toString(), COL_8};
            }
        }
        return infosInARow;
    }

//    public reminderClass getReminderObjectFromRow(long ID) {
//        reminderClass rc = new reminderClass();
//       // SQLiteDatabase db = getReadableDatabase();
//         c = db.rawQuery("SELECT * FROM Reminder_tabledb WHERE _id = ?", new String[]{Long.toString(ID)});
//        if (c.getCount() > 0) {
//            c.moveToFirst();
//
//            rc.setId(c.getLong(0));
//            rc.setContactPerson(c.getString(1));
//            rc.setStartsAt(c.getString(2));
//            rc.setRepeatsNr(c.getString(3));
//            rc.setRepeatsInterval(c.getString(4));
//            rc.setUnsharpen(c.getString(5));
//            rc.setUnsharpenNr(c.getString(6));
//            rc.setUnsharpenInterval(c.getString(7));

            //Log.d("Debug", "---------------------以下测试是否能够读取数据库中的内容------------------------");

        //c.getString(2);
        //c.moveToFirst();
        //Log.d("Debug", "---------------------以下是cursor在数据库中读取到的行数------------------------");
        //Log.d("Debug", Integer.toString(c.getCount()));

        //c.getCount();
        //Log.d("Debug", "---------------------以下为判断editext是否为null------------------------");

//        c.close();
//        db.close();
//        return rc;
//    }

    public void insertData(String vKONTACT_PERSON, String vSTARTSAT, int vREPEATSNR, String vREPEATSINTERVAL, String vUNSHARPEN,
                           int vUNSHARPENNR, String vRADOMDATE) {
        // SQLiteDatabase db = getReadableDatabase();

        db.execSQL("INSERT INTO Reminder_tabledb(CONTACT_PERSON,STARTSAT,REPEATSNR,REPEATSINTERVAL,UNSHARPEN,UNSHARPENNR,RADOMDATE)" +
                "values (?,?,?,?,?,?,?)", new Object[]{vKONTACT_PERSON, vSTARTSAT, vREPEATSNR, vREPEATSINTERVAL, vUNSHARPEN, vUNSHARPENNR, vRADOMDATE});
    }


//    public void UpdateData(String KONTACT_PERSON, String STARTSAT, String REPEATSNR, String REPEATS, String UNSHARPEN,
//                           String UNSHARPENNR, String UNSHARPENINTERVAL) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues newValues = new ContentValues();
//        newValues.put("STARTSAT", "newStartDate");
//        newValues.put("RADOMDATE", "newRadomDate");
//        db.update("Reminder_tabledb", newValues, "_id=" + Integer.toString(id), null);
//    }


    public void updateStartAndRadomDate(int id, String newStartDate, String newRadomDate) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("STARTSAT", newStartDate);
        newValues.put("RADOMDATE", newRadomDate);
        db.update("Reminder_tabledb", newValues, "_id=" + Integer.toString(id), null);
    }


    public void deleteData(String ID) {
        //SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM Reminder_tabledb WHERE _id =" + ID);
    }

    //// TODO: 21.12.16 wenn mehr als ein Termin zufällig an dem gleichen Tag existiert
    public Cursor radomDateQuery() {
        String dateOfDay = DateOfDay.getDateOfDay();

        c = db.rawQuery("SELECT _id, CONTACT_PERSON,REPEATSNR,REPEATSINTERVAL,UNSHARPENNR,RADOMDATE FROM Reminder_tabledb WHERE RADOMDATE = ?", new String[]{dateOfDay});

        return c;
    }

    /**
     * check if the contact person already exist
     *
     * @param name
     * @return
     */
    public boolean isExist(String name) {

        c = db.query(TABLE_NAME, new String[]{COL_2}, "CONTACT_PERSON=?", new String[]{name}, null, null, null);
        while (c.moveToNext()) {
            String nameFound = c.getString(c.getColumnIndex(COL_2));
            if (nameFound.equals(name)) {
                return true;
            }
        }
        return false;
    }


}

