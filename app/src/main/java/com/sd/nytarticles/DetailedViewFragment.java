package com.sd.nytarticles;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.UUID;


/**
 * Created by AzAlex2 on 22.02.2018.
 */

public class DetailedViewFragment extends Fragment {

    ListItem mItem;

    private TextView mTitleTextView;
    private TextView mColumnTextView;
    private TextView mSectionTextView;
    private TextView mAbstractTextView;
    private TextView mPublishedDateTextView;
    private TextView mBylineTextView;
    private CheckBox mCheckBox;
    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        UUID itemId = (UUID) getActivity().getIntent().getSerializableExtra(DetailedViewActivity.EXTRA_ITEM_ID);
        int listIdx = (int) getActivity().getIntent().getSerializableExtra(DetailedViewActivity.EXTRA_LIST_IDX);
        mItem = ArticleLab.get(getActivity()).getArticle(itemId, listIdx);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_detailed_view,container, false);

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
}
