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

public class MovieList extends RecyclerView.Adapter<MovieList.MovieViewHolder> {
    private LinkedList<Movie> mMovieList;
    private LayoutInflater mInflater;
    private Context context;
    private String session_id;
    private Boolean loggedIn;
    private final String TAG = MainActivity.class.getSimpleName();

    public MovieList(Context context, LinkedList<Movie> movieList, String session_id, Boolean loggedIn) {
        mInflater = LayoutInflater.from(context);
        this.mMovieList = movieList;
        this.context = context;
        this.session_id = session_id;
        this.loggedIn = loggedIn;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        public final TextView movieTitle;
        public final ImageView imageView;
        public final TextView movieDate;
        public final TextView movieScore;
        final MovieList mAdapter;

        public MovieViewHolder(View itemView, MovieList adapter) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
            movieDate = itemView.findViewById(R.id.date);
            movieScore = itemView.findViewById(R.id.rating);

            this.mAdapter = adapter;
        }
    }

    public void setMovieList(LinkedList<Movie> movieList) {
        this.mMovieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieList.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieList.MovieViewHolder holder, int position) {
        Movie mCurrent = mMovieList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent moviePage = new Intent(context, MovieDetail.class);
                moviePage.putExtra("movieId", mCurrent.getId().toString());
                moviePage.putExtra("session_id", session_id);
                moviePage.putExtra("logged_in", loggedIn);
                context.startActivity(moviePage);
            }
        });

        holder.movieTitle.setText(mCurrent.getTitle());
        holder.movieScore.setText(mCurrent.getVoteAverage().toString());
        holder.movieDate.setText(mCurrent.getReleaseDate());
        if (mCurrent.getPosterPath() != null) {
            Picasso.with(context)
                    .load(Uri.parse("https://image.tmdb.org/t/p/w500/" + mCurrent.getPosterPath()))
                    .into(holder.imageView);
        } else {
            Picasso.with(context).load(R.drawable.image_placeholder).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
}
