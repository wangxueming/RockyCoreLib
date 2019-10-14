package com.rockyw.weather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by weijianxing on 9/22/15.
 */
public class MyDataBaseHelper extends DaoMaster.OpenHelper {


    private static final String DB_NAME = "test.db";

    private static final SortedMap<Integer, Migration> ALL_MIGRATIONS = new TreeMap<>();

    static {
        ALL_MIGRATIONS.put(1, new V1Migration());
//        ALL_MIGRATIONS.put(2, new V2Migration());
//        ALL_MIGRATIONS.put(3, new V3Migration());
    }

    public MyDataBaseHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        executeMigrations(db, ALL_MIGRATIONS.keySet());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        SortedMap<Integer, Migration> migrations = ALL_MIGRATIONS.subMap(oldVersion, newVersion);
        executeMigrations(sqLiteDatabase, migrations.keySet());
    }

    private void executeMigrations(final SQLiteDatabase paramSQLiteDatabase, final Set<Integer>
            migrationVersions) {
        for (final Integer version : migrationVersions) {
            ALL_MIGRATIONS.get(version).migrate(paramSQLiteDatabase);
        }
    }
}



