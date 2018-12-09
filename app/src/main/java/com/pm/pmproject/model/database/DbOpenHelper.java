package com.pm.pmproject.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.pm.pmproject.model.entity.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class DbOpenHelper extends DaoMaster.OpenHelper {

    public DbOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(Database db) {
        Log.d("DbOpenHelper", "onCreate");
        super.onCreate(db);
        Log.d("DbOpenHelper", "inserting data");
        db.execSQL("INSERT INTO attribute VALUES(1, 'burned_calories')");
        db.execSQL("INSERT INTO attribute VALUES(2, 'chest_circumference')");
        db.execSQL("INSERT INTO attribute VALUES(3, 'biceps_circumference')");
        db.execSQL("INSERT INTO attribute VALUES(4, 'waist_circumference')");
        db.execSQL("INSERT INTO attribute VALUES(5, 'forearm_circumference')");
        db.execSQL("INSERT INTO attribute VALUES(6, 'thigh_circumference')");
        db.execSQL("INSERT INTO attribute VALUES(7, 'hip_circumference')");
        db.execSQL("INSERT INTO attribute VALUES(8, 'body_weight')");

        db.execSQL("INSERT INTO training_type VALUES(1, 'running')");
        db.execSQL("INSERT INTO training_type VALUES(2, 'cycling')");
        db.execSQL("INSERT INTO training_type VALUES(3, 'gym')");
        db.execSQL("INSERT INTO training_type VALUES(4, 'calisthenics')");
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        DaoMaster.dropAllTables(db, true);
        //super.onUpgrade(db, oldVersion, newVersion);
        onCreate(db);
        Log.d("DEBUG", "DB_OLD_VERSION : " + oldVersion + ", DB_NEW_VERSION : " + newVersion);
    }
}