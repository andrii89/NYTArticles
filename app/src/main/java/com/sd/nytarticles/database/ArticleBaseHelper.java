package com.sd.nytarticles.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sd.nytarticles.database.ArticleDbSchema.ArticleTable;

/**
 * Created by AzAlex2 on 23.02.2018.
 */

public class ArticleBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "articleBase.db";

    public ArticleBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + ArticleTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ArticleTable.Cols.UUID + ", " +
                ArticleTable.Cols.TITLE + ", " +
                ArticleTable.Cols.COLUMN + ", " +
                ArticleTable.Cols.SECTION + ", " +
                ArticleTable.Cols.ABSTRACT + ", " +
                ArticleTable.Cols.BYLINE + ", " +
                ArticleTable.Cols.URL + ", " +
                ArticleTable.Cols.IMAGE_URL +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
