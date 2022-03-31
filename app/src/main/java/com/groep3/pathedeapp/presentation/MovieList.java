package com.groep3.pathedeapp.presentation;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.domain.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieList extends RecyclerView.Adapter<MovieList.ViewHolder> {
    private ArrayList<Movie> mMovieData;
    private Context mContext;
    private ImageView mImage;
    private String TAG = "MovieList";


    public MovieList(Context context, ArrayList<Movie> mealData) {
        this.mContext = context;
        this.mMovieData = mealData;
        Log.d(TAG, "Made new movie list");
    }


    //makes viewholder objects
    @Override
    public MovieList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "Creating mealList viewholder");
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false));
    }

    //binds data to viewholder
    @Override
    public void onBindViewHolder(MovieList.ViewHolder holder, int position) {
        Movie currentMovie = mMovieData.get(position);
        Log.d(TAG, "Showing meal: "+ currentMovie.getName());
        holder.bindTo(currentMovie);

    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "MealData size: " + mMovieData.size());
        return mMovieData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mName;
        private ImageView mImage;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views
            Log.i(TAG, "Initializing itemViews");
            mName = itemView.findViewById(R.id.name);
            mImage = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        void bindTo(Movie currentMovie) {
            Log.d(TAG, "Putting data in itemViews");
            mName.setText(currentMovie.getName());
            try {
                Picasso.with(mContext)
                        .load(Uri.parse("https://image.tmdb.org/t/p/w500/" + currentMovie.getPosterPath()))
                        .into(mImage);
            }catch (Exception exception){
                Log.e(TAG, "Could not load image");
            }
        }

        @Override
        public void onClick(View view) {
            Movie currentMovie = mMovieData.get(getAdapterPosition());

            Intent moviePage = new Intent(mContext, MovieDetail.class);
            moviePage.putExtra("movieName", currentMovie.getName());
            moviePage.putExtra("movie_cover", currentMovie.getPosterPath());;

            Log.v(TAG, "Made new activity");
            mContext.startActivity(moviePage);
            Log.v(TAG, "Started new activity");
        }
    }
}
