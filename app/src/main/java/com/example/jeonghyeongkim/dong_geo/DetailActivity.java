package com.example.jeonghyeongkim.dong_geo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.Integer.parseInt;

public class DetailActivity extends AppCompatActivity{

    //comment 리스트
    private RecyclerView commentRecyclerView;
    private CommentAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private CommentModel comment;
    private ArrayList<CommentModel> mComments = new ArrayList<>();
    private ArrayList<String> commentUsers = new ArrayList<>();

    private int MAX_ITEM_COUNT = 10;

    MenuIntent menuIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuIntent);
        menuIntent.setKakaoNickView(navigationView);

        Bundle data = getIntent().getExtras();
        String raw_data = data.getString("raw_data");
        Log.d("raw_data", raw_data);
        try {
            set_view(raw_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {
        private ArrayList<CommentModel> commentData;

        public void setData(ArrayList<CommentModel> list){
            commentData = list;
        }

        @Override
        public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

// 사용할 아이템의 뷰를 생성해준다.
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment, parent, false);

            CommentHolder holder = new CommentHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final CommentHolder holder, int position) {

            CommentModel data = commentData.get(position);
            holder.commentOwnerDisplay.setImageResource(R.drawable.ic_launcher_background);
            holder.usernameTextView.setText(data.getUsername());
            holder.timeTextView.setText(String.valueOf(data.getTimeCreated()));
            holder.commentTextView.setText(data.getComment());
        }

        @Override
        public int getItemCount() {
            return commentData.size();
        }

    }

    public static class CommentHolder extends RecyclerView.ViewHolder {
        ImageView commentOwnerDisplay;
        TextView usernameTextView;
        TextView timeTextView;
        TextView commentTextView;


        public CommentHolder(View itemView) {
            super(itemView);
            commentOwnerDisplay = (CircleImageView) itemView.findViewById(R.id.comment_profile_image);
            usernameTextView = (TextView) itemView.findViewById(R.id.comment_username);
            timeTextView = (TextView) itemView.findViewById(R.id.comment_time_posted);
            commentTextView = (TextView) itemView.findViewById(R.id.comment);
        }

        public void setUsername(String username) {
            usernameTextView.setText(username);
        }

        public void setTime(CharSequence time) {
            timeTextView.setText(time);
        }

        public void setComment(String comment) {
            commentTextView.setText(comment);
        }
    }

    private void set_view(String raw_data) throws JSONException {

        commentRecyclerView = findViewById(R.id.commentRecyclerview);
        mLayoutManager = new LinearLayoutManager(DetailActivity.this);
        commentRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CommentAdapter();
        mAdapter.setData(mComments);
        commentRecyclerView.setAdapter(mAdapter);

        JSONObject jsonObject = new JSONObject(raw_data);
        JSONArray object_to_array = jsonObject.getJSONArray("result");

        for(int i=0;i<object_to_array.length();i++){
            JSONObject tmp= (JSONObject)object_to_array.get(i);

            String comment_id = (String) tmp.get("comment_id");
            Log.d("parsing_comment_id", String.valueOf(comment_id));

            String user_id = (String) tmp.get("user_id");
            Log.d("parsing_comment_user_id",String.valueOf(user_id));

            String state = (String) tmp.get("state");
            Log.d("parsing_comment_state", String.valueOf(state));

            String comment = (String) tmp.get("text");
            Log.d("parsing_comment_text", comment);

            String date = (String) tmp.get("date");
            Log.d("parsing_comment_date",date);

            String user_nickname = (String) tmp.get("user_nickname");
            Log.d("parsing_comment_nick",user_nickname);

            mComments.add(new CommentModel(user_nickname,date,comment,user_id,comment_id,state));

        }
    }
}
