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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.List;
import com.groep3.pathedeapp.domain.LoadedLists;
import com.groep3.pathedeapp.domain.LoadedMovies;
import com.groep3.pathedeapp.domain.Rating;
import com.groep3.pathedeapp.domain.Review;
import com.groep3.pathedeapp.domain.User;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListList extends RecyclerView.Adapter<ListList.ListViewHolder> {
    private LinkedList<List> mListList;
    private LayoutInflater mInflater;
    private Context context;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private Integer userId;
    private String sessionId;
    private boolean loggedIn;
    private final String TAG = MainActivity.class.getSimpleName();

    public ListList(Context context, LinkedList<List> listList, Integer userId, String sessionId, Boolean loggedIn) {
        mInflater = LayoutInflater.from(context);
        this.mListList = listList;
        this.context = context;
        this.userId = userId;
        this.sessionId = sessionId;
        this.loggedIn = loggedIn;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        public final TextView listTitle;
        public final TextView listDescription;
        public final ImageView listImage;

        final ListList mAdapter;

        public ListViewHolder(View itemView, ListList adapter) {
            super(itemView);
            listTitle = itemView.findViewById(R.id.list);
            listImage = itemView.findViewById(R.id.list_item_image);
            listDescription = itemView.findViewById(R.id.listDescription);

            this.mAdapter = adapter;
        }
    }

    public void setListList(LinkedList<List> ListList) {
        this.mListList = ListList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListList.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.list_list_item, parent, false);
        return new ListViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListList.ListViewHolder holder, int position) {
        List mCurrent = mListList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent personalList = new Intent(context, PersonalListActivity.class);
                personalList.putExtra("list_id", mCurrent.getId());
                personalList.putExtra("session_id", sessionId);
                personalList.putExtra("logged_in", loggedIn);
                personalList.putExtra("userId", userId);
                context.startActivity(personalList);
            }
        });

        holder.listTitle.setText(mCurrent.getName());
        holder.listDescription.setText(mCurrent.getDescription());
        if (mCurrent.getPosterPath() != null) {
            Picasso.with(context)
                    .load(Uri.parse("https://image.tmdb.org/t/p/w500/" + mCurrent.getPosterPath()))
                    .into(holder.listImage);
        } else {
            Picasso.with(context).load(R.drawable.image_placeholder).into(holder.listImage);
        }


    }

    @Override
    public int getItemCount() {
        return mListList.size();
    }


}
