package com.rockyw.weather.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by weijianxing on 9/22/15.
 */
public class V1Migration implements Migration {

    @Override
    public void migrate(SQLiteDatabase db) {
        db.execSQL("ALTER TABLE NOTE ADD COLUMN test");
    }
}