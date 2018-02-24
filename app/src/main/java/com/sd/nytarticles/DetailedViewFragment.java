package com.sd.nytarticles;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;


/**
 * Created by AzAlex2 on 22.02.2018.
 */

public class DetailedViewFragment extends Fragment {

    private static final String TAG = "DetailedViewFragment";

    ListItem mItem;

    private ImageView mItemImageView;
    private TextView mTitleTextView;
    private TextView mColumnTextView;
    private TextView mSectionTextView;
    private TextView mAbstractTextView;
    private TextView mPublishedDateTextView;
    private TextView mBylineTextView;
    private CheckBox mCheckBox;
    private Button mButton;

    private ImageDownloader<ImageView> mImageDownloader;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        UUID itemId = (UUID) getActivity().getIntent().getSerializableExtra(DetailedViewActivity.EXTRA_ITEM_ID);
        int listIdx = (int) getActivity().getIntent().getSerializableExtra(DetailedViewActivity.EXTRA_LIST_IDX);
        mItem = ArticleLab.get(getActivity()).getArticle(itemId, listIdx);

        Handler responseHandler = new Handler();
        mImageDownloader = new ImageDownloader<>(responseHandler);
        mImageDownloader.setImageDownloadListener(
                new ImageDownloader.ImageDownloadListener<ImageView>(){
                    @Override
                    public void onImageDownloaded(ImageView imageView, Bitmap bitmap){
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        setDrawable(drawable);
                    }
                }
        );
        mImageDownloader.start();
        mImageDownloader.getLooper();
        Log.i(TAG, "Background thread started");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_detailed_view,container, false);

        mItemImageView = (ImageView)view.findViewById(R.id.imageview);
        setDrawable(null);
        mImageDownloader.queueImage(mItemImageView, mItem.getImageUrl());

        mTitleTextView = (TextView)view.findViewById(R.id.title_textview);
        mTitleTextView.setText(mItem.getCaption());

        mColumnTextView = (TextView)view.findViewById(R.id.column_textview);
        mColumnTextView.setText(mItem.getColumn());

        mSectionTextView = (TextView)view.findViewById(R.id.section_textview);
        mSectionTextView.setText(mItem.getSection());

        mAbstractTextView = (TextView)view.findViewById(R.id.abstract_textview);
        mAbstractTextView.setText(mItem.getAbstract());

        mPublishedDateTextView = (TextView)view.findViewById(R.id.published_date_textview);
        mPublishedDateTextView.setText(mItem.getPublishedDate());

        mBylineTextView = (TextView)view.findViewById(R.id.byline_textview);
        mBylineTextView.setText(mItem.getByline());

        mCheckBox = (CheckBox)view.findViewById(R.id.favourite_checkbox);
        mCheckBox.setChecked(mItem.getChecked());
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ArticleLab.get(getActivity()).addToFavourite(mItem);
                } else {
                    mItem.setChecked(false);
                    ArticleLab.get(getActivity()).deleteFavourite(mItem);
                }
            }
        });

        mButton = (Button)view.findViewById(R.id.read_button);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mItem.getUrl()));
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mImageDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mImageDownloader.clearQueue();
    }

    private void setDrawable(Drawable drawable){
        if(drawable == null){
            mItemImageView.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
        } else {
            mItemImageView.setImageDrawable(drawable);
        }
    }
}
