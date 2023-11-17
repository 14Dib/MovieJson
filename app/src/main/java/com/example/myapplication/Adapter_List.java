package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adapter_List extends ArrayAdapter<Movie> {
    private Activity context;
    private int idlayout;
    private ArrayList<Movie> movieList;

    public Adapter_List(@NonNull Activity context, int idlayout, ArrayList<Movie> movieList ) {
        super(context, idlayout,movieList);
        this.context = context;
        this.idlayout = idlayout;
        this.movieList = movieList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Tạo để chứa Layout
        LayoutInflater layoutInflater = context.getLayoutInflater();
        //Đặt layout lên đế để tạo thành view và trả về view
        convertView = layoutInflater.inflate(idlayout,null);
        //Lấy 1 phần tử trong mảng
        Movie movie = movieList.get(position);
        //ánh xạ
        ImageView imageView = convertView.findViewById(R.id.imageview);
        TextView title = convertView.findViewById(R.id.title_tv);
        TextView overview = convertView.findViewById(R.id.overview_tv);
        TextView rating = convertView.findViewById(R.id.rating_tv);
        RelativeLayout relativeLayout;

        rating.setText(movie.getRating().toString());
        title.setText(movie.getTitle());
        overview.setText(movie.getOverview());
        Glide.with(context).load(movie.getPoster()).into(imageView);
        return convertView;
    }


}
