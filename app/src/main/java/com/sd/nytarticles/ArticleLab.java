package com.sd.nytarticles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sd.nytarticles.database.ArticleBaseHelper;
import com.sd.nytarticles.database.ArticleCursorWrapper;
import com.sd.nytarticles.database.ArticleDbSchema;
import com.sd.nytarticles.database.ArticleDbSchema.ArticleTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by AzAlex2 on 22.02.2018.
 */

public class ArticleLab {
    private static ArticleLab sArticleLab;

    private List<ListItem> mMostViewedList;
    private List<ListItem> mMostSharedList;
    private List<ListItem> mMostEmailedList;
    private List<ListItem> mFavouriteList;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ArticleLab get(Context context){
        if (sArticleLab == null){
            sArticleLab = new ArticleLab(context);
        }
        return sArticleLab;
    }

    private ArticleLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new ArticleBaseHelper(mContext).getWritableDatabase();

        mMostViewedList = new ArrayList<>();
        mMostEmailedList = new ArrayList<>();
        mMostSharedList = new ArrayList<>();
        mFavouriteList = new ArrayList<>();

    }

    public List<ListItem> getList(int i){
        switch (i){
            case 0:
                return mMostViewedList;
            case 1:
                return mMostEmailedList;
            case 2:
                return mMostSharedList;
            case 3:
                mFavouriteList.clear();
                ArticleCursorWrapper cursor = queryArticles(null, null);

                try{
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()){
                        mFavouriteList.add(cursor.getArticle());
                        cursor.moveToNext();
                    }
                }finally {
                    cursor.close();
                }
                return mFavouriteList;
            default:
                return null;
        }
    }

    public void setList(int i, List<ListItem> list){
        switch (i){
            case 0:
                mMostViewedList = list;
                break;
            case 1:
                mMostEmailedList = list;
                break;
            case 2:
                mMostSharedList = list;
                break;
            default:
                break;
        }
    }

    public ListItem getArticle(UUID id, int i){
        List<ListItem> list = new ArrayList<>();
        switch (i){
            case 0:
                list = mMostViewedList;
                break;
            case 1:
                list = mMostEmailedList;
                break;
            case 2:
                list = mMostSharedList;
                break;
            case 3:
                list = mFavouriteList;
                break;
            default:
                break;
        }
        for (ListItem item : list){
            if (item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    private static ContentValues getContentValues(ListItem item){
        ContentValues values = new ContentValues();
        values.put(ArticleTable.Cols.UUID, item.getId().toString());
        values.put(ArticleTable.Cols.TITLE, item.getCaption());
        values.put(ArticleTable.Cols.COLUMN, item.getColumn());
        values.put(ArticleTable.Cols.SECTION, item.getSection());
        values.put(ArticleTable.Cols.ABSTRACT, item.getAbstract());
        values.put(ArticleTable.Cols.BYLINE, item.getByline());
        values.put(ArticleTable.Cols.URL, item.getUrl());
        values.put(ArticleTable.Cols.IMAGE_URL, item.getImageUrl());

        return values;
    }

    public void addToFavourite(ListItem item){
        boolean isChecked = false;
        for(ListItem listItem : mFavouriteList){
            if (item.getUrl().equals(listItem.getUrl())){
                isChecked = true;
            }
        }

        if(!isChecked) {
            ContentValues values = getContentValues(item);

            mDatabase.insert(ArticleTable.NAME, null, values);
        }

        for(int i = 0; i < 3; i++){
            List<ListItem> list = getList(i);
            for(ListItem listItem : list){
                if(listItem.getUrl().equals(item.getUrl())){
                    listItem.setChecked(true);
                }
            }
        }
    }

    public void deleteFavourite(ListItem item){
        mDatabase.delete(ArticleTable.NAME, ArticleTable.Cols.URL + " = ?", new String[] {item.getUrl()});

        for(int i = 0; i < 3; i++){
            List<ListItem> list = getList(i);
            for(ListItem listItem : list){
                if(listItem.getUrl().equals(item.getUrl())){
                    listItem.setChecked(false);
                }
            }
        }
    }

    private ArticleCursorWrapper queryArticles(String whereClause, String [] whereArgs){
        Cursor cursor = mDatabase.query(
                ArticleTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ArticleCursorWrapper(cursor);
    }
}
