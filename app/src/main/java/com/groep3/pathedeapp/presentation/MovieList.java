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
        Log.d(TAG, "Made new meal list");
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
//        private TextView mTime;
//        private TextView mPrice;
//        private TextView mCity;
//        private ImageView mImage;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views
            Log.i(TAG, "Initializing itemViews");
            mName = itemView.findViewById(R.id.name);
//            mTime = itemView.findViewById(R.id.time);
//            mCity = itemView.findViewById(R.id.city);
//            mImage = itemView.findViewById(R.id.image);
//            mPrice = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(this);
        }

        void bindTo(Movie currentMovie) {
            Log.d(TAG, "Putting data in itemViews");
            mName.setText(currentMovie.getName());
//            mTime.setText(currentMeal.getDate());
//            mCity.setText(currentCook.getCity());
//            mPrice.setText(currentMeal.getPrice());
//            Log.d(TAG, "Attempting to load image " + currentMovie.getImageResource());
//            try {
//                Picasso.with(mContext)
//                        .load(Uri.parse(currentMovie.getImageResource()))
//                        .into(mImage);
//            }catch (Exception exception){
//                Log.e(TAG, "Could not load image");
//            }
        }

        @Override
        public void onClick(View view) {
//            Meal currentMeal = mMovieData.get(getAdapterPosition());
//            Log.i(TAG, "User clicked on " + currentMeal.getName());
//            Cook currenCook = mMovieData.get(getAdapterPosition()).getCook();
//            Intent foodPage = new Intent(mContext, MealDetail.class);
//
//            foodPage.putExtra("foodName", currentMeal.getName());
//            foodPage.putExtra("foodPrice", currentMeal.getPrice());
//            foodPage.putExtra("foodDescription", currentMeal.getDescription());
//            foodPage.putExtra("foodDate", currentMeal.getDate());
//            foodPage.putExtra("foodAllergies", currentMeal.getAllergies());
//            foodPage.putExtra("foodCook", currenCook.getName());
//            foodPage.putExtra("foodCity", currenCook.getCity());
//            foodPage.putExtra("foodStreet", currenCook.getStreet());
//            foodPage.putExtra("foodEmail", currenCook.getEmail());
//            foodPage.putExtra("foodPhone", currenCook.getPhoneNumber());
//            foodPage.putExtra("image_resource", currentMeal.getImageResource());;
//            foodPage.putExtra("foodVega", currentMeal.getVega());
//            foodPage.putExtra("foodVegan", currentMeal.getVegan());
//            Log.v(TAG, "Made new activity");
//            mContext.startActivity(foodPage);
//            Log.v(TAG, "Started new activity");
//
        }
    }
}
