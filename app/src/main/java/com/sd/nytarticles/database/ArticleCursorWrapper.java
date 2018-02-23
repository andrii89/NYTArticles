package com.sd.nytarticles.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.sd.nytarticles.ListItem;
import com.sd.nytarticles.database.ArticleDbSchema.ArticleTable;

import java.util.UUID;

/**
 * Created by AzAlex2 on 23.02.2018.
 */

public class ArticleCursorWrapper extends CursorWrapper {
    public ArticleCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public ListItem getArticle(){
        String uuidString = getString(getColumnIndex(ArticleTable.Cols.UUID));
        String title = getString(getColumnIndex(ArticleTable.Cols.TITLE));
        String column = getString(getColumnIndex(ArticleTable.Cols.COLUMN));
        String section = getString(getColumnIndex(ArticleTable.Cols.SECTION));
        String _abstract = getString(getColumnIndex(ArticleTable.Cols.ABSTRACT));
        String byline = getString(getColumnIndex(ArticleTable.Cols.BYLINE));
        String url = getString(getColumnIndex(ArticleTable.Cols.URL));
        String imageUrl = getString(getColumnIndex(ArticleTable.Cols.IMAGE_URL));

        ListItem item = new ListItem(UUID.fromString(uuidString));
        item.setCaption(title);
        item.setColumn(column);
        item.setSection(section);
        item.setAbstract(_abstract);
        item.setByline(byline);
        item.setUrl(url);
        item.setImageUrl(imageUrl);

        return item;
    }
}
