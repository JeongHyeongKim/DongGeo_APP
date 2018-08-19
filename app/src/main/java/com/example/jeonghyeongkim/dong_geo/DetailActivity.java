package com.example.jeonghyeongkim.dong_geo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //comment 리스트
    private RecyclerView commentRecyclerView;
    private CommentAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private CommentModel comment;
    private ArrayList<CommentModel> mComments = new ArrayList<>();
    private ArrayList<String> commentUsers = new ArrayList<>();

    private int MAX_ITEM_COUNT = 10;

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
        navigationView.setNavigationItemSelectedListener(this);

        //Intent intent = new Intent();
        //intent.getStringExtra("currency");

        commentRecyclerView = findViewById(R.id.commentRecyclerview);
        mLayoutManager = new LinearLayoutManager(DetailActivity.this);
        commentRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CommentAdapter();
        mAdapter.setData(mComments);
        commentRecyclerView.setAdapter(mAdapter);


        mComments.add(new CommentModel("고희경", 2 ," 거래할래요"));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            // Handle the camera action
        } else if (id == R.id.nav_exchange) {
            Intent intent = new Intent(DetailActivity.this, KakaoInputActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_write_content) {
            Intent intent = new Intent(DetailActivity.this, WriteContentActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mypage) {
            Intent intent = new Intent(DetailActivity.this, MypageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
