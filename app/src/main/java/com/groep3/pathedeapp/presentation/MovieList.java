package com.groep3.pathedeapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.domain.Movie;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class MovieList extends RecyclerView.Adapter<MovieList.MovieViewHolder>{
    private LinkedList<Movie> nMovieList;
    private LayoutInflater mInflater;
    private Context context;


    public MovieList(Context context, LinkedList<Movie> movieList){
        mInflater = LayoutInflater.from(context);
        this.nMovieList = movieList;
        this.context = context;
    }


    static class MovieViewHolder extends RecyclerView.ViewHolder{
        public final TextView movieTitle;
        public final ImageView imageView;
        final MovieList mAdapter;

        public MovieViewHolder(View itemView, MovieList adapter) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);

            this.mAdapter = adapter;
        }

    }



    public void setMovieList(LinkedList<Movie> movieList) {
        this.nMovieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieList.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        View mItemView = mInflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieList.MovieViewHolder holder, int position) {
        Movie mCurrent = nMovieList.get(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Intent moviePage = new Intent(context, MovieDetail.class);
                moviePage.putExtra("movieName", mCurrent.getTitle());
                moviePage.putExtra("movie_cover", mCurrent.getPosterPath());;

                context.startActivity(moviePage);

            }
        });


        holder.movieTitle.setText(mCurrent.getTitle());
        Picasso.with(context)
                .load(Uri.parse("https://image.tmdb.org/t/p/w500/" + mCurrent.getPosterPath()))
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return nMovieList.size();
    }
}
