package com.vr.minitwitterapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.BaseTweetView;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

public class ListActivity extends AppCompatActivity{

    Context mContext;
    ListView mListView;
    EditText mSearchEditText;
    ImageButton mSearchButton;

    SearchTimeline mSearchTimeline;
    CustomTweetTimelineListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mContext = this;

        mSearchEditText = (EditText) findViewById(R.id.search_edittext);
        mListView = (ListView) findViewById(R.id.listview);
        mSearchButton = (ImageButton) findViewById(R.id.search_button);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(mSearchEditText.getText().toString())){
                    String searchtext = "#" + mSearchEditText.getText().toString();
                    mSearchTimeline = new SearchTimeline.Builder()
                            .query(searchtext)
                            .maxItemsPerRequest(15)
                            .build();
                    mAdapter = new CustomTweetTimelineListAdapter(mContext, mSearchTimeline);
                    mListView.setAdapter(mAdapter);

                }

            }
        });
    }

    class CustomTweetTimelineListAdapter extends TweetTimelineListAdapter {

        public CustomTweetTimelineListAdapter(Context context, Timeline<Tweet> timeline) {
            super(context, timeline);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            //disable subviews to avoid links are clickable
            if(view instanceof ViewGroup){
                disableViewAndSubViews((ViewGroup) view);
            }

            //enable root view and attach custom listener
            view.setEnabled(true);

            return view;
        }

        //helper method to disable subviews
        private void disableViewAndSubViews(ViewGroup layout) {
            layout.setEnabled(false);
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                if (child instanceof ViewGroup) {
                    disableViewAndSubViews((ViewGroup) child);
                } else {
                    child.setEnabled(false);
                    child.setClickable(false);
                    child.setLongClickable(false);
                }
            }
        }

    }

}

