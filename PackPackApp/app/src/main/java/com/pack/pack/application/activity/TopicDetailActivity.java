package com.pack.pack.application.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.pack.pack.application.AppController;
import com.pack.pack.application.R;
import com.pack.pack.application.adapters.TopicDetailAdapter;
import com.pack.pack.application.topic.activity.model.ParcelableTopic;
import com.pack.pack.client.api.API;
import com.pack.pack.client.api.APIBuilder;
import com.pack.pack.client.api.APIConstants;
import com.pack.pack.client.api.COMMAND;
import com.pack.pack.model.web.JPack;
import com.pack.pack.model.web.JTopic;
import com.pack.pack.model.web.Pagination;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TopicDetailActivity extends AppCompatActivity {

    private Pagination<JPack> page;

    private ProgressDialog progressDialog;

    private TopicDetailAdapter adapter;

    public static final String PARCELABLE_KEY = "topic_parcel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.topic_detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.upload_pack);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        adapter = new TopicDetailAdapter(this, new ArrayList<JPack>());
        final ListView listView = (ListView) findViewById(R.id.topic_detail_list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                int count = listView.getCount();
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (listView.getLastVisiblePosition() > count - 1) {
                        new LoadPackTask().execute();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        ParcelableTopic topic = (ParcelableTopic) getIntent().getParcelableExtra(PARCELABLE_KEY);
        new LoadPackTask().execute(topic);
    }

    private class LoadPackTask extends AsyncTask<ParcelableTopic, Integer, Pagination<JPack>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Pagination<JPack> doInBackground(ParcelableTopic... jTopics) {
            Pagination<JPack> page = null;
            if(jTopics == null || jTopics.length == 0)
                return page;
            try {
                ParcelableTopic jTopic = jTopics[0];
                String oAuthToken = AppController.getInstance().getoAuthToken();
                String userId = AppController.getInstance().getUserId();
                API api = APIBuilder.create().setAction(COMMAND.GET_ALL_PACKS_IN_TOPIC)
                        .setOauthToken(oAuthToken)
                        .addApiParam(APIConstants.User.ID, userId)
                        .addApiParam(APIConstants.Topic.ID, jTopic.getTopicId())
                        .addApiParam(APIConstants.Topic.CATEGORY, jTopic.getTopicCategory())
                        .build();
                page = (Pagination<JPack>) api.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return page;
        }

        @Override
        protected void onPostExecute(Pagination<JPack> jPackPagination) {
            super.onPostExecute(jPackPagination);
            if(jPackPagination != null) {
                List<JPack> packs = jPackPagination.getResult();
                if(packs != null && !packs.isEmpty()) {
                    adapter.setPacks(packs);
                    adapter.notifyDataSetChanged();
                }
            }
            hideProgressDialog();
        }

        private void showProgressDialog() {
            TopicDetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog = new ProgressDialog(TopicDetailActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            });
        }

        private void hideProgressDialog() {
            TopicDetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            });
        }
    }
}
