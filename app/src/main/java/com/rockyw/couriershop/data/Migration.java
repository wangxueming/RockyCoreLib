package com.rockyw.couriershop.data;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by weijianxing on 9/22/15.
 */
public interface Migration {
    void migrate(SQLiteDatabase db);
}