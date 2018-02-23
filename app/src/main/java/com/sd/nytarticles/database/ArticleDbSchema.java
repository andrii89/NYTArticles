package com.sd.nytarticles.database;

/**
 * Created by AzAlex2 on 23.02.2018.
 */

public class ArticleDbSchema {
    public static final class ArticleTable{
        public static final String NAME = "favourite_articles";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String COLUMN = "column";
            public static final String SECTION = "section";
            public static final String ABSTRACT = "abstract";
            public static final String BYLINE = "byline";
            public static final String URL = "url";
            public static final String IMAGE_URL = "image_url";
        }
    }
}
