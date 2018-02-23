package com.sd.nytarticles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.UUID;

/**
 * Created by AzAlex2 on 22.02.2018.
 */

public class DetailedViewActivity extends FragmentActivity {

    public static final String EXTRA_ITEM_ID = "com.sd.nytarticles.item_id";
    public static final String EXTRA_LIST_IDX = "com.sd.nytarticles.list_idx";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = new DetailedViewFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }

    public static Intent newIntent(Context packageContext, UUID itemId, int idx){
        Intent intent = new Intent(packageContext, DetailedViewActivity.class);
        intent.putExtra(EXTRA_ITEM_ID, itemId);
        intent.putExtra(EXTRA_LIST_IDX, idx);
        return intent;
    }
}
