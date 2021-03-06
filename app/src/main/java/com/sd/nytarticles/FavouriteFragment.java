package com.sd.nytarticles;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.appcompat.R.styleable.CompoundButton;

/**
 * Created by AzAlex2 on 20.02.2018.
 */

public class FavouriteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<ListItem> mItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.favourite_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        setupAdapter();


        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        updateUI();
    }

    private class ArticleHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        ListItem listItem;

        public TextView mTitleTextView;
        public CheckBox mFavouriteCheckBox;

        public ArticleHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.title_textview);

            mFavouriteCheckBox = (CheckBox) itemView.findViewById(R.id.favourite_checkbox);
            mFavouriteCheckBox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecked){
                        listItem.setChecked(false);
                        ArticleLab.get(getActivity()).deleteFavourite(listItem);
                        updateUI();
                    }
                }
            });
        }

        @Override
        public void onClick(View view){
            Intent intent = DetailedViewActivity.newIntent(getActivity(), listItem.getId(), 3);
            startActivity(intent);
        }

        public void bindArticle(ListItem item){
            listItem = item;
            mTitleTextView.setText(listItem.getCaption());
            mFavouriteCheckBox.setChecked(listItem.getChecked());
        }
    }

    private class ArticleAdapter extends RecyclerView.Adapter<ArticleHolder>{
        private List<ListItem> mArticles;

        public ArticleAdapter(List<ListItem> articles){
            mArticles = articles;
        }

        @Override
        public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_view, parent, false);
            return new ArticleHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleHolder holder, int position){
            ListItem listItem = mArticles.get(position);
            holder.bindArticle(listItem);
        }

        @Override
        public int getItemCount(){
            return mArticles.size();
        }
    }

    private void setupAdapter(){
        if(isAdded()){
            mRecyclerView.setAdapter(new ArticleAdapter(mItems));
        }
    }

    private void updateUI(){
        ArticleLab articleLab = ArticleLab.get(getActivity());

        mItems = ArticleLab.get(getActivity()).getList(3);

        if (mRecyclerView.getAdapter() == null){
            setupAdapter();
        } else{
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}

