package com.sd.nytarticles;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by AzAlex2 on 21.02.2018.
 */

public class NYTConnect {

    private static final String TAG = "NYTConnect";

    private static final String API_KEY = "223e4440fca44fdc91e2c049394d83ed";

    private static final String MOST_VIEWED_URL = "https://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/30.json";
    private static final String MOST_EMAILED_URL = "https://api.nytimes.com/svc/mostpopular/v2/mostemailed/all-sections/30.json";
    private static final String MOST_SHARED_URL = "https://api.nytimes.com/svc/mostpopular/v2/mostshared/all-sections/30.json";


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpsURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<ListItem> fetchItems(int i){
        String mUrl;
        switch (i){
            case 0:
                mUrl = MOST_VIEWED_URL;
                break;
            case 1:
                mUrl = MOST_EMAILED_URL;
                break;
            case 2:
                mUrl = MOST_SHARED_URL;
                break;
            default:
                mUrl = MOST_VIEWED_URL;
        }

        List<ListItem> items = new ArrayList<>();

        try {
            String url = Uri.parse(mUrl)
                    .buildUpon()
                    .appendQueryParameter("api-key", API_KEY)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe){
            Log.e(TAG, "failed to fetch items", ioe);
        }

        return items;
    }

    private void parseItems(List<ListItem> items, JSONObject jsonObject)
        throws IOException, JSONException {

        JSONArray articleJsonArray = jsonObject.getJSONArray("results");
        for (int i = 0; i< articleJsonArray.length(); i++){
            JSONObject articleJsonObject = articleJsonArray.getJSONObject(i);

            ListItem item = new ListItem();
            item.setAbstract(articleJsonObject.getString("abstract"));
            item.setByline(articleJsonObject.getString("byline"));
            item.setCaption(articleJsonObject.getString("title"), i);
            item.setColumn(articleJsonObject.getString("column"));
            item.setSection(articleJsonObject.getString("section"));
            item.setPublishedDate(articleJsonObject.getString("published_date"));
            item.setUrl(articleJsonObject.getString("url"));

            try {
                JSONArray mediaJsonArray = articleJsonObject.getJSONArray("media");
                JSONObject mediaJsonObject = mediaJsonArray.getJSONObject(0);
                JSONArray metadataJsonArray = mediaJsonObject.getJSONArray("media-metadata");
                for (int j = 0; j< metadataJsonArray.length(); j++){
                    JSONObject imageJsonObject = metadataJsonArray.getJSONObject(j);
                    if(imageJsonObject.getString("format").equals("mediumThreeByTwo210")) {
                        item.setImageUrl(imageJsonObject.getString("url"));
                    }
                }
            } catch (Exception e){
                item.setImageUrl("");
            }

            items.add(item);
        }
    }
}
