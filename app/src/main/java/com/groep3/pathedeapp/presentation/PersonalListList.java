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
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.List;
import com.groep3.pathedeapp.domain.Movie;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class PersonalListList extends RecyclerView.Adapter<PersonalListList.ListViewHolder> {
    private LinkedList<Movie> mPersonalListList;
    private LayoutInflater mInflater;
    private Context context;
    private String sessionId;
    private Boolean loggedIn;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public PersonalListList(Context context, LinkedList<Movie> listList, String sessionId, Boolean loggedIn) {
        mInflater = LayoutInflater.from(context);
        this.mPersonalListList = listList;
        this.sessionId = sessionId;
        this.loggedIn = loggedIn;
        this.context = context;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        public final TextView listTitle;
        public final TextView listDescription;
        public final ImageView listImage;

        final PersonalListList mAdapter;

        public ListViewHolder(View itemView, PersonalListList adapter) {
            super(itemView);
            listTitle = itemView.findViewById(R.id.personal_item);
            listDescription = itemView.findViewById(R.id.personal_item_description);
            listImage = itemView.findViewById(R.id.personal_list_image);


            this.mAdapter = adapter;
        }
    }

    public void setPersonalListList(LinkedList<Movie> ListList) {
        this.mPersonalListList = ListList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonalListList.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.personal_list_item, parent, false);
        return new PersonalListList.ListViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalListList.ListViewHolder holder, int position) {
        Movie mCurrent = mPersonalListList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            Integer movieId = mCurrent.getId();

            @Override
            public void onClick(View view) {
                Intent moviePage = new Intent(context, MovieDetail.class);
                moviePage.putExtra("movieId", movieId.toString());
                moviePage.putExtra("session_id", sessionId);
                moviePage.putExtra("logged_in", loggedIn);
                context.startActivity(moviePage);
            }
        });

        if (mCurrent.getPosterPath() != null) {
            Picasso.with(context)
                    .load(Uri.parse("https://image.tmdb.org/t/p/w500/" + mCurrent.getPosterPath()))
                    .into(holder.listImage);
        } else {
            Picasso.with(context).load(R.drawable.image_placeholder).into(holder.listImage);
        }

        holder.listTitle.setText(mCurrent.getTitle());
        holder.listDescription.setText(mCurrent.getOverview());
    }

    @Override
    public int getItemCount() {
        return mPersonalListList.size();
    }


}
