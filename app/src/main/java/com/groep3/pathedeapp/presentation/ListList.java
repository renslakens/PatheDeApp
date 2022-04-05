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

public class ListList  extends RecyclerView.Adapter<ListList.ListViewHolder> {
    private LinkedList<Movie> mListList;
    private LayoutInflater mInflater;
    private Context context;

    public ListList(Context context, LinkedList<Movie> listList) {
        mInflater = LayoutInflater.from(context);
        this.mListList = listList;
        this.context = context;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
//        public final TextView movieTitle;
//        public final ImageView imageView;
//        public final TextView movieDate;
//        public final TextView movieScore;
        final ListList mAdapter;

        public ListViewHolder(View itemView, ListList adapter) {
            super(itemView);
//            movieTitle = itemView.findViewById(R.id.name);
//            imageView = itemView.findViewById(R.id.image);
//            movieDate = itemView.findViewById(R.id.date);
//            movieScore = itemView.findViewById(R.id.rating);

            this.mAdapter = adapter;
        }
    }

    public void setListList(LinkedList<Movie> ListList) {
        this.mListList = ListList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListList.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View mItemView = mInflater.inflate(R.layout.movie_list_item, parent, false);
//        return new ListViewHolder(mItemView, this);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListList.ListViewHolder holder, int position) {
        Movie mCurrent = mListList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Intent moviePage = new Intent(context, MovieDetail.class);
//                moviePage.putExtra("movieId", mCurrent.getId().toString());
//                context.startActivity(moviePage);
            }
        });
//
//        holder.movieTitle.setText(mCurrent.getTitle());
//        holder.movieScore.setText(mCurrent.getVoteAverage().toString());
//        holder.movieDate.setText(mCurrent.getReleaseDate());
//        if (mCurrent.getPosterPath() != null) {
//            Picasso.with(context)
//                    .load(Uri.parse("https://image.tmdb.org/t/p/w500/" + mCurrent.getPosterPath()))
//                    .into(holder.imageView);
//        } else {
//            Picasso.with(context).load(R.drawable.image_placeholder).into(holder.imageView);
//        }
    }

    @Override
    public int getItemCount() {
        return mListList.size();
    }
}
