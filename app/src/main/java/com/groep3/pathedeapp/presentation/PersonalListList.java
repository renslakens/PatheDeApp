package com.groep3.pathedeapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.List;

import java.util.LinkedList;

public class PersonalListList extends RecyclerView.Adapter<PersonalListList.ListViewHolder>{
    private LinkedList<List> mPersonalListList;
    private LayoutInflater mInflater;
    private Context context;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public PersonalListList(Context context, LinkedList<List> listList) {
        mInflater = LayoutInflater.from(context);
        this.mPersonalListList = listList;
        this.context = context;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        public final TextView listTitle;

        final PersonalListList mAdapter;

        public ListViewHolder(View itemView, PersonalListList adapter) {
            super(itemView);
            listTitle = itemView.findViewById(R.id.personal_item);

            this.mAdapter = adapter;
        }
    }

    public void setPersonalListList(LinkedList<List> ListList) {
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
        List mCurrent = mPersonalListList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent moviePage = new Intent(context, MovieDetail.class);
                moviePage.putExtra("movieId", mCurrent.getId().toString());
                context.startActivity(moviePage);
            }
        });

        holder.listTitle.setText(mCurrent.getName());
    }

    @Override
    public int getItemCount() {
        return mPersonalListList.size();
    }


}