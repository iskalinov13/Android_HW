package com.example.lasttwitter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter<Post> {

    ArrayList<Post> posts;

    public PostAdapter(@NonNull Context context, ArrayList<Post> posts) {
        super(context, R.layout.list_item);
        this.posts = posts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(getContext());
        View custom_view=inflater.inflate(R.layout.list_item,parent,false);

        TextView username = custom_view.findViewById(R.id.blog_post_userName);
        TextView content = custom_view.findViewById(R.id.blog_post_description);
        TextView date = custom_view.findViewById(R.id.blog_post_date);


        Post post = this.posts.get(position);

        username.setText(post.getUserName());
        content.setText(post.getPostContent());
        date.setText(post.getPostDate());
        System.out.println(post.getUserName()+post.getPostContent()+post.getPostDate()+"pokma yok");


        return custom_view;

    }

    @Override
    public int getCount() {
        return this.posts.size();
    }

}