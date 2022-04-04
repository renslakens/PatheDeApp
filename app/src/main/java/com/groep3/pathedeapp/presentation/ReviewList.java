package com.groep3.pathedeapp.presentation;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.domain.Review;

import java.util.LinkedList;

public class ReviewList extends RecyclerView.Adapter<ReviewList.ReviewViewHolder> {
    private LinkedList<Review> mReviewList;
    private LayoutInflater mInflater;
    private Context context;

    public ReviewList(Context context, LinkedList<Review> reviewList) {
        mInflater = LayoutInflater.from(context);
        this.mReviewList = reviewList;
        this.context = context;
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        public final TextView reviewer;
        public final TextView review;
        final ReviewList mAdapter;

        public ReviewViewHolder(View itemView, ReviewList adapter) {
            super(itemView);
            reviewer = itemView.findViewById(R.id.reviewer);
            review = itemView.findViewById(R.id.content);

            this.mAdapter = adapter;
        }

    }

    public void setReviewList(LinkedList<Review> reviewList) {
        this.mReviewList = reviewList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewList.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.movie_review, parent, false);
        return new ReviewViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewList.ReviewViewHolder holder, int position) {
        Review mCurrent = mReviewList.get(position);
        holder.review.setText(mCurrent.getReview());
        holder.reviewer.setText(mCurrent.getAuthor());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}


