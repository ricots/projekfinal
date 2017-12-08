package com.projectzero.renatto.aplikasifinalfix;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ALAMAT = "alamat";
    public static final String COLUMN_NOTELP = "notelp";
    public static final String COLUMN_NOFASKES = "nofaskes";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_POLY = "poly";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_NAME = "nama";
    public static final String TABLE_NAME = "rumahsakit";
    private static final String db_create = "create table rumahsakit(_id integer primary key autoincrement, nama varchar(50) not null, alamat varchar(50) not null, notelp varchar(50) not null,image varchar(250) not null,nofaskes varchar(50) not null,poly varchar(50) not null,lat varchar (50) not null,lng varchar (50) not null);";
    private static final String db_name = "rumahsakit.db";
    private static final int db_version = 1;

    public DBHelper(Context context) {
        super(context, db_name, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_create);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS rumahsakit");
        onCreate(db);
    }
}

