package com.sd.nytarticles;

import android.content.Context;

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

    public static ArticleLab get(Context context){
        if (sArticleLab == null){
            sArticleLab = new ArticleLab(context);
        }
        return sArticleLab;
    }

    private ArticleLab(Context context){
        mMostViewedList = new ArrayList<>();
        mMostEmailedList = new ArrayList<>();
        mMostSharedList = new ArrayList<>();
    }

    public List<ListItem> getList(int i){
        switch (i){
            case 0:
                return mMostViewedList;
            case 1:
                return mMostEmailedList;
            case 2:
                return mMostSharedList;
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
}
